package com.example.vutran.finderawsome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vutran.finderawsome.Model.ModelReview;
import com.example.vutran.finderawsome.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by VuTran on 6/16/2017.
 */

public class AdapterReview extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ModelReview> listReview;

    public AdapterReview(Context context, int layout, List<ModelReview> listReview) {
        this.context = context;
        this.layout = layout;
        this.listReview = listReview;
    }

    @Override
    public int getCount() {
        return listReview.size();
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imageViewAvatar = (ImageView) convertView.findViewById(R.id.imageViewAvatar);
            holder.textViewComment = (TextView) convertView.findViewById(R.id.textViewComment);
            holder.textViewName = (TextView) convertView.findViewById(R.id.textViewNameSave);
            holder.textViewStar = (TextView) convertView.findViewById(R.id.textViewStar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelReview review = listReview.get(position);
        holder.textViewComment.setText(review.getText());
        holder.textViewName.setText(review.getAuthorName());
        holder.textViewStar.setText(review.getRating());
        Picasso.with(context).load(review.getPhotoUrl()).into(holder.imageViewAvatar);

        return convertView;

    }

    private class ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewName, textViewComment,textViewStar;
    }
}
