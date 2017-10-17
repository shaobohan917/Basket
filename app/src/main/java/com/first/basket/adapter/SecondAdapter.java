package com.first.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.first.basket.R;
import com.first.basket.bean.ClassifyBean;

import java.util.List;

/**
 * Created by hanshaobo on 24/09/2017.
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyViewHolder> {
    private Context context;
    private List<ClassifyBean.DataBean.LeveltwoBean> datas;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private MyViewHolder holder;
    private int layoutPosition;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ClassifyBean.DataBean.LeveltwoBean data, int position);
    }

    public SecondAdapter(Context context, List<ClassifyBean.DataBean.LeveltwoBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recycler_second, null);
        holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvSecondLevel.setText(datas.get(position).getLeveltwodesc());

        holder.itemView.setTag(datas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, (ClassifyBean.DataBean.LeveltwoBean) holder.itemView.getTag(), layoutPosition);
            }
        });

        //更改状态
        if(position == layoutPosition){
            holder.tvSecondLevel.setBackgroundColor(context.getResources().getColor(R.color.colorMain));
            holder.tvSecondLevel.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            holder.tvSecondLevel.setBackgroundColor(context.getResources().getColor(R.color.grayF8));
            holder.tvSecondLevel.setTextColor(context.getResources().getColor(R.color.gray66));
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSecondLevel;

        MyViewHolder(View itemView) {
            super(itemView);
            tvSecondLevel = itemView.findViewById(R.id.tvSecondLevel);
        }
    }
}
