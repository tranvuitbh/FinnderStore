package com.example.vutran.finderawsome.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vutran.finderawsome.R;

import java.util.List;

/**
 * Created by VuTran on 6/22/2017.
 */

public class DBAdapterStore extends BaseAdapter{

    private Context context;
    private int layout;
    private List<DBModelStore> storeList;

    public DBAdapterStore(Context context, int layout, List<DBModelStore> storeList) {
        this.context = context;
        this.layout = layout;
        this.storeList = storeList;
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView textViewNameSaved, textViewAddressSaved;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.textViewNameSaved = (TextView) convertView.findViewById(R.id.textViewNameSave);
            holder.textViewAddressSaved = (TextView) convertView.findViewById(R.id.textViewAddressSave);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        DBModelStore store = storeList.get(position);
        holder.textViewNameSaved.setText(store.getName());
        holder.textViewAddressSaved.setText(store.getAddress());
        return convertView;
    }
}
