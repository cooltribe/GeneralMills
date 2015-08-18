package com.example1.anima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 陈玉柱 on 2015/7/24.
 */
public class MyListViewAdapter extends BaseAdapter {
    List<String> list;
    Context context;
    LayoutInflater inflater;

    public MyListViewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
        ViewHolder vh ;
        if (null == convertView){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, parent ,false);
            vh.text = (TextView) convertView.findViewById(R.id.text111);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(200,0,0,0);
        vh.text.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView text;
    }
}
