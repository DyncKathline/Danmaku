package com.kathline.danmaku.simple.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kathline.danmaku.simple.arragephotoview.R;
import com.kathline.danmaku.simple.data.BarrageData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kathline.danmaku.adapter.BarrageAdapter;
import com.kathline.danmaku.ui.BarrageView;
import com.kathline.danmaku.ui.SingleTextView;

import java.util.LinkedList;
import java.util.List;

public class SingleBarrageActivity extends AppCompatActivity {

    private String SEED[] = {"景色还不错啊", "小姐姐真好看～，", "小姐姐真好看～，小姐姐真好看～，又去哪里玩了？我也要去！带我去～", "门票多少啊？", "厉害啦！"};
    private final int ICON_RESOURCES[] = {R.drawable.cat, R.drawable.corgi, R.drawable.lovelycat, R.drawable.boy, R.drawable.girl, R.drawable.samoyed};

    private BarrageView barrageView;
    private BarrageAdapter<BarrageData> mAdapter;

    //private Button btnStop;

    public static void show(Context context) {
        Intent intent = new Intent(context, SingleBarrageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_barrage);

        barrageView = findViewById(R.id.barrage);
        //btnStop = findViewById(R.id.btn_add);

        initBarrage();

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });

        /*btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                barrageView.destroy();
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initBarrage() {
        BarrageView.Options options = new BarrageView.Options()
                .setGravity(BarrageView.GRAVITY_TOP)                // 设置弹幕的位置
                .setInterval(50)                                     // 设置弹幕的发送间隔
                .setSpeed(200, 29)                   // 设置速度和波动值
                .setModel(BarrageView.MODEL_COLLISION_DETECTION)     // 设置弹幕生成模式
                .setRepeat(-1)                                       // 循环播放 默认为1次 -1 为无限循环
                .setClick(false);                                    // 设置弹幕是否可以点击
        barrageView.setOptions(options);
        // 设置适配器 第一个参数是点击事件的监听器
        barrageView.setAdapter(mAdapter = new BarrageAdapter<BarrageData>(null, this) {
            @Override
            public BarrageViewHolder<BarrageData> onCreateViewHolder(View root, int type) {
                return new SingleBarrageActivity.ViewHolder(root);
            }

            @Override
            public int getItemLayout(BarrageData barrageData) {
                return R.layout.barrage_item_normal;
            }
        });
    }

    private void initData() {
        int strLength = SEED.length;
        List<BarrageData> dataList = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            dataList.add(new BarrageData(SEED[i % strLength], 0, i));
        }
        mAdapter.addList(dataList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        barrageView.destroy();
    }

    class ViewHolder extends BarrageAdapter.BarrageViewHolder<BarrageData> {

        private ImageView mHeadView;
        private SingleTextView mContent;

        ViewHolder(View itemView) {
            super(itemView);

            mHeadView = itemView.findViewById(R.id.image);
            mContent = itemView.findViewById(R.id.content);
        }

        @Override
        protected void onBind(BarrageData data) {
            Glide.with(SingleBarrageActivity.this).load(ICON_RESOURCES[data.getPos() % ICON_RESOURCES.length])
                    .apply(RequestOptions.circleCropTransform())
                    .into(mHeadView);
            mContent.setText(data.getContent());
        }
    }
}
