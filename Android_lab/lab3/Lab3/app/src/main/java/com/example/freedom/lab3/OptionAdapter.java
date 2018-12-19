package com.example.freedom.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by freedom on 2017/10/22.
 */

public class OptionAdapter extends BaseAdapter{
    private Context context;
    List<String> options;
    public OptionAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
    }
    @Override
    public int getCount() {
        if (options != null) {
            return options.size();
        } else return 0;
    }
    @Override
    public Object getItem(int i) {
        if (options == null) {
            return null;
        } else {
            return options.get(i);
        }
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView;
        ViewHolder holder;
        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.option, null);
            holder = new ViewHolder();
            holder.option = (TextView) convertView.findViewById(R.id.option);
            convertView.setTag(holder);
        } else {
            convertView = view;
            holder = (ViewHolder) convertView.getTag();
        }
        holder.option.setText(options.get(position));
        return convertView;
    }
    private class ViewHolder {
        public TextView option;
    }
}
