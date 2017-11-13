package com.first.basket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.first.basket.R;
import com.first.basket.bean.ProductBean;
import com.first.basket.common.CommonMethod;
import com.first.basket.common.CommonMethod1;
import com.first.basket.utils.ImageUtils;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by hanshaobo on 24/09/2017.
 */

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.MyViewHolder> {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private Context context;
    private List<ProductBean> mDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private MyViewHolder holder;
    private int layoutPosition;
    private View mHeaderView;
    private View mFooterView;

    public void addHeaderView(@Nullable View header) {
        mHeaderView = header;
        notifyItemInserted(0);
    }

    public void addFooterView(@Nullable View footer) {
        mFooterView = footer;
        notifyItemInserted(getItemCount() - 1);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ProductBean data, int position);
    }

    public PlaceOrderAdapter(Context context, List<ProductBean> datas) {
        this.context = context;
        this.mDatas = datas;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MyViewHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new MyViewHolder(mFooterView);
        }

        View itemView = View.inflate(context, R.layout.item_recycler_shop, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(CommonMethod.dip2px(context,10), 0, CommonMethod.dip2px(context,10), 0);
        itemView.setLayoutParams(params);
        holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof MyViewHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                ProductBean bean = mDatas.get(position - 1);
                holder.tvName.setText(bean.getProductname());
                holder.tvUnit.setText(bean.getWeight() + "/" + bean.getUnit());
                holder.tvPrice.setText(CommonMethod1.Companion.showPrice(bean));
                holder.tvOrderCount.setVisibility(View.VISIBLE);
                holder.tvOrderCount.setText("x " + bean.getAmount());

                ImageUtils.showImg(context, bean.getImg(), holder.ivGoods);

                holder.itemView.setTag(bean);
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatas.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return mDatas.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size() + 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivGoods;
        private TextView tvName;
        private TextView tvUnit;
        private TextView tvPrice;
        private TextView tvOrderCount;

        MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }

            tvName = itemView.findViewById(R.id.tvName1);
            tvUnit = itemView.findViewById(R.id.tvUnit1);
            tvPrice = itemView.findViewById(R.id.tvPrice1);
            ivGoods = itemView.findViewById(R.id.ivGoods);
            itemView.findViewById(R.id.cbSelect).setVisibility(View.GONE);
            itemView.findViewById(R.id.amoutView).setVisibility(View.GONE);
            tvOrderCount = itemView.findViewById(R.id.tvOrderCount);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }
}

