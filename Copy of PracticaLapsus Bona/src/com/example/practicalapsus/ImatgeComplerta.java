package com.example.practicalapsus;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ImatgeComplerta extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imatge_complerta);

		
		Intent i = getIntent();

		// Seleccionar id de l'imatge seleccionada
		int position = i.getExtras().getInt("id");
		ImageAdapter imageAdapter = new ImageAdapter(this);

		ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
		imageView.setImageResource(imageAdapter.arrayimatges[position]);
		
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
