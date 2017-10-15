package com.first.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.basket.R;
import com.first.basket.bean.ClassifyBean;
import com.first.basket.bean.ProductsBean;
import com.first.basket.utils.ImageUtils;

import java.util.List;

/**
 * Created by hanshaobo on 24/09/2017.
 */

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.MyViewHolder> {
    private Context context;
    private List<ProductsBean> mDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private MyViewHolder holder;
    private int layoutPosition;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ProductsBean data, int position);
    }

    public PlaceOrderAdapter(Context context, List<ProductsBean> datas) {
        this.context = context;
        this.mDatas = datas;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recycler_place_order, null);
        holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvName.setText(mDatas.get(position).getProductname());
        holder.tvUnit.setText(mDatas.get(position).getUnit());
        holder.tvPrice.setText(mDatas.get(position).getPrice());

        ImageUtils.showImg(context, mDatas.get(position).getImg(), holder.ivGoods);

        holder.itemView.setTag(mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, (ProductsBean) holder.itemView.getTag(), layoutPosition);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivGoods;
        private final TextView tvName;
        private final TextView tvUnit;
        private final TextView tvPrice;

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivGoods = itemView.findViewById(R.id.ivGoods);
        }
    }
}
