package com.kathline.danmaku.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.kathline.danmaku.R;

/**
 * 单行文本超过一屏不会截取和换行，替代TextView会直接截取不显示超过一屏
 */
public class SingleTextView extends View {

    TextPaint mTextPaint;
    StaticLayout mStaticLayout;
    CharSequence mText;
    float mTextSize;
    int mColor;

    // use this constructor if creating MyView programmatically
    public SingleTextView(Context context) {
        this(context, null);
    }

    // this constructor is used when created from xml
    public SingleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLabelView(context, attrs);
    }

    private void initLabelView(Context context, AttributeSet attrs) {

        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.SingleTextView);
            mTextSize = typedArray.getDimension(R.styleable.SingleTextView_android_textSize, DeviceUtils.sp2px(context, 16));
            mColor = typedArray.getColor(R.styleable.SingleTextView_android_color, 0xFF000000);
            mText = typedArray.getText(R.styleable.SingleTextView_android_text);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
//        mTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColor);

//        mText = "在Android开发中，Canvas.drawText不会换行，即使一个很长的字符串也只会显示一行，超出部分会隐藏在屏幕之外.StaticLayout是android中处理文字的一个工具类，StaticLayout 处理了文字换行的问题";
        // default to a single line of text
        int width = (int) mTextPaint.measureText(mText.toString());
        mStaticLayout = new StaticLayout(mText, mTextPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);

        // New API alternate
        //
        // StaticLayout.Builder builder = StaticLayout.Builder.obtain(mText, 0, mText.length(), mTextPaint, width)
        //        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
        //        .setLineSpacing(1, 0) // multiplier, add
        //        .setIncludePad(false);
        // mStaticLayout = builder.build();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Tell the parent layout how big this view would like to be
        // but still respect any requirements (measure specs) that are passed down.

        // determine the width
        int width;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthRequirement = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthRequirement;
        } else {
            int textWidth = (int) mTextPaint.measureText(mText.toString());
            mStaticLayout = new StaticLayout(mText, mTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
            width = mStaticLayout.getWidth() + getPaddingLeft() + getPaddingRight();
//            if (widthMode == MeasureSpec.AT_MOST) {
//                if (width > widthRequirement) {
////                    width = widthRequirement;
//                    // too long for a single line so relayout as multiline
//                    mStaticLayout = new StaticLayout(mText, mTextPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
//                }
//            }
        }

        // determine the height
        int height;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightRequirement = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightRequirement;
        } else {
            height = mStaticLayout.getHeight() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightRequirement);
            }
        }

        // Required call: set width and height
        setMeasuredDimension(width, height);
    }

    public void setText(final  CharSequence text) {
        mText = text;
        post(new Runnable() {
            @Override
            public void run() {
                int width = (int) mTextPaint.measureText(mText.toString());
                mStaticLayout = new StaticLayout(mText, mTextPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                requestLayout();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // do as little as possible inside onDraw to improve performance

        // draw the text on the canvas after adjusting for padding
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        mStaticLayout.draw(canvas);
        canvas.restore();
    }
}
