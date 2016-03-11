package com.cxy.firststudio;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/2/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {

    public String[] datas = null;
    public int type = 0;
    public MyAdapter(String[] datas, int type) {
        this.datas = datas;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (type){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item,parent,false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item,parent,false);
                break;
        }
        ViewHolder vh = new ViewHolder(view);
        if(type == 0){
            vh.mTextView = (TextView) view.findViewById(R.id.textView);
        }else{
            vh.mTextView = (TextView) view.findViewById(R.id.textView2);
        }
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(datas[position]);
        holder.itemView.setTag(datas[position]);
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (String)v.getTag());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
//            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
}
