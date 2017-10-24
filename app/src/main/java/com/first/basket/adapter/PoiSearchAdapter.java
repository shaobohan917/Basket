package com.first.basket.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.first.basket.R;
import com.first.basket.activity.AddressMapsActivity;

import java.util.List;

/**
 * Created by hanshaobo on 22/10/2017.
 */

public class PoiSearchAdapter extends BaseAdapter {
    private Context ctx;
    private List<PoiItem> list;

    public PoiSearchAdapter(AddressMapsActivity context, List<PoiItem> poiItems) {
        this.ctx = context;
        this.list = poiItems;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(ctx, R.layout.item_poisearch, null);
            holder.poititle = convertView.findViewById(R.id.poititle);
            holder.poititle2 = convertView.findViewById(R.id.poititle2);
            holder.cbSelect = convertView.findViewById(R.id.cbSelect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PoiItem item = (PoiItem) getItem(position);
        holder.poititle.setText(item.getTitle());
        holder.poititle2.setText(item.getSnippet()+"");
        return convertView;
    }

    private class ViewHolder {
        TextView poititle;
        TextView poititle2;
        CheckBox cbSelect;
    }
}