package com.example.vutran.finderawsome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vutran.finderawsome.Model.ModelStore;
import com.example.vutran.finderawsome.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by VuTran on 6/2/2017.
 */

public class AdapterStore extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ModelStore> listStore;

    public AdapterStore(Context context, int layout, List<ModelStore> listStore) {
        this.context = context;
        this.layout = layout;
        this.listStore = listStore;
    }

    @Override
    public int getCount() {
        return listStore.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHoler holder;
        if (convertView == null) {
            holder = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textViewNameSave);
            holder.textViewAddress = (TextView) convertView.findViewById(R.id.textViewAddressSave);
            holder.imageViewLogo = (ImageView) convertView.findViewById(R.id.imageViewLogoSave);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }
        /**
         * Gán giá trị cho item trên listview
         * Name và Address
         */
        ModelStore store = listStore.get(position);
        holder.textViewName.setText(store.getName());
        holder.textViewAddress.setText(store.getAddress());
        Picasso.with(context).load(urlRequestPhoto(store.getIdImage()).toString()).into(holder.imageViewLogo);
        return convertView;

    }

    public StringBuilder urlRequestPhoto(String idImage) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400");
        url.append("&photoreference=" + idImage);
        url.append("&key=AIzaSyAKs8S1YN0HEbaY34at6sYh4nDDywkge2s");
        return url;
    }

    private class ViewHoler {
        TextView textViewName, textViewAddress;
        ImageView imageViewLogo;
    }
}
