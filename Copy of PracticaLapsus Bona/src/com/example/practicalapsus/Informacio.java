package com.example.practicalapsus;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class Informacio extends Activity {

	private ImageView logoImage = null;
	private ScrollView scrollView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacio);

		logoImage = (ImageView) findViewById(R.id.logoImage);
		scrollView = (ScrollView) findViewById(R.id.scrollViewText);

		//Color de l'action bar
		 ActionBar ab = getActionBar(); 
		 ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136493"));    
		           ab.setBackgroundDrawable(colorDrawable);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
