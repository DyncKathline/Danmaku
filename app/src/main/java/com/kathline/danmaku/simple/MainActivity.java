package com.kathline.danmaku.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kathline.danmaku.simple.adapter.MainAdapter;
import com.kathline.danmaku.simple.arragephotoview.R;
import com.kathline.danmaku.simple.ui.MutiBarrageActivity;
import com.kathline.danmaku.simple.ui.SingleBarrageActivity;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnSelectListener{

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
    }

    private void initWidget() {
        mRecyclerView = findViewById(R.id.recycle);
        mAdapter = new MainAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSelectStr(String str) {
        switch (str){
            case "单视图弹幕":
                SingleBarrageActivity.show(this);
                break;
            case "多视图弹幕":
                MutiBarrageActivity.show(this);
                break;
            default:
                break;
        }
    }
}
