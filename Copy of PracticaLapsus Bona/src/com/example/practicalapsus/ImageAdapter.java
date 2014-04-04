package com.example.practicalapsus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public Integer[] arrayimatges = { R.drawable.imatge1, R.drawable.imatge2,
			R.drawable.imatge3, R.drawable.imatge4, R.drawable.imatge5,
			R.drawable.imatge6, R.drawable.imatge7, R.drawable.imatge8,
			R.drawable.imatge9, R.drawable.imatge10, R.drawable.imatge11,
			R.drawable.imatge12, R.drawable.imatge13, R.drawable.imatge14,
			R.drawable.imatge15 };

	// Constructor
	public ImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return arrayimatges.length;
	}

	@Override
	public Object getItem(int position) {
		return arrayimatges[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(arrayimatges[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
		return imageView;
	}

}
