package com.example.python_on_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Flipper extends ArrayAdapter {
    Integer[] images;
    Context context;
    public Flipper(Context context, Integer[] images) {
        super(context,R.layout.image,images);
        this.context = context;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Integer img = images[position];
        ViewHolder holder ;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(img);
        return convertView;
    }

    private class ViewHolder {
        public ImageView image;

        public void fill(Integer img) {
            image.setImageResource(img);

        }
    }
}
