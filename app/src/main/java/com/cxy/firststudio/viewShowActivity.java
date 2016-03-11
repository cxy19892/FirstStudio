package com.cxy.firststudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

public class viewShowActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private int type = 0;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_show);
        type = getIntent().getIntExtra("type", 0);
        initviews();
    }

    private void initviews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        switch(type){
            case 0: //listView
                mLayoutManager = new LinearLayoutManager(this);
                break;
            case 1: // gridView
                mLayoutManager = new GridLayoutManager(this, 2);
                break;
            case 2: // 瀑布流
                mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyAdapter(getDummyDatas(), type);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(viewShowActivity.this,data,Toast.LENGTH_SHORT).show();
            }
        });

    }


    private String[] getDummyDatas(){
        String[] datas = {"ubue","ierbnon","解决不能被1","解决不能被2","解决不能被3","解决不能被4","解决不能被5",
                "解决不能被6","解决不能被7","解决不能被8","解决不能被9","解决不能被10","解决不能被11",};
        return datas;
    }
}
