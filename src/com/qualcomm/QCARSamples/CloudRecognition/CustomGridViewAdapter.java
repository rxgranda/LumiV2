package com.qualcomm.QCARSamples.CloudRecognition;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author manish.s
 *
 */
public class CustomGridViewAdapter extends ArrayAdapter<Item> {
 Context context;
 int layoutResourceId;
 ArrayList<Item> data = new ArrayList<Item>();
 int ancho;

 public CustomGridViewAdapter(Context context, int layoutResourceId,
   ArrayList<Item> data, int width) {
  super(context, layoutResourceId, data);
  this.layoutResourceId = layoutResourceId;
  this.context = context;
  this.data = data;
  ancho=width;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
  View row = convertView;
  RecordHolder holder = null;

  if (row == null) {
   LayoutInflater inflater = ((Activity) context).getLayoutInflater();
   row = inflater.inflate(layoutResourceId, parent, false);

   holder = new RecordHolder();
   holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
   holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
   row.setTag(holder);
  } else {
   holder = (RecordHolder) row.getTag();
  }

  Item item = data.get(position);
  holder.txtTitle.setText(item.getTitle());
  holder.txtTitle.setWidth(ancho/3);
  holder.imageItem.setBackground(item.getImage());
  holder.imageItem.setMaxWidth(ancho/3);
  holder.imageItem.setMaxHeight(ancho/3);

  return row;

 }

 static class RecordHolder {
  TextView txtTitle;
  ImageView imageItem;

 }
}