package com.first.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.basket.R;
import com.first.basket.bean.ProductBean;
import com.first.basket.common.CommonMethod;
import com.first.basket.common.CommonMethod1;
import com.first.basket.utils.ImageUtils;

import java.util.List;

/**
 * Created by hanshaobo on 24/09/2017.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    private Context context;
    private List<ProductBean> mDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private OnAddItemClickListener onAddItemClickListener;
    private OnAmountChangeListener mOnAmountChangeListener;
    private MyViewHolder holder;
    private int layoutPosition;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ProductBean data, int position);
    }

    public interface OnAddItemClickListener {
        void onAddClick(View view, ProductBean data, int position);
    }

    public interface OnAmountChangeListener {
        void onAmountChange(View imageView, int position);
    }

    public ContentAdapter(Context context, List<ProductBean> data) {
        this.context = context;
        this.mDatas = data;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnAddItemClickListener(OnAddItemClickListener listener) {
        this.onAddItemClickListener = listener;
    }

    public void setOnAmountChangeListener(OnAmountChangeListener listener) {
        this.mOnAmountChangeListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recycler_content, null);
        holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProductBean product = mDatas.get(position);
        holder.tvName.setText(product.getProductname());
        holder.tvUnit.setText(product.getWeight() + "/" + product.getUnit());
        holder.tvPrice.setText(CommonMethod1.Companion.showPrice(product));

        ImageUtils.showImg(context, product.getImg(), holder.ivGoods);

        holder.itemView.setTag(mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取当前点击的位置
                layoutPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, (ProductBean) holder.itemView.getTag(), layoutPosition);
            }
        });

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddItemClickListener.onAddClick(holder.itemView, (ProductBean) holder.itemView.getTag(), layoutPosition);
            }
        });
        if (CommonMethod.isTrue(product.getPromboolean())) {
            holder.tvProm.setVisibility(View.VISIBLE);
        } else {
            holder.tvProm.setVisibility(View.GONE);
        }
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
        private final TextView tvProm;
        private final ImageView ivAdd;

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvProm = itemView.findViewById(R.id.tvProm);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            ivGoods = itemView.findViewById(R.id.ivGoods);
        }
    }
}
