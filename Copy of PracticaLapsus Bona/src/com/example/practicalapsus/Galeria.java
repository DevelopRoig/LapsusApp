package com.example.practicalapsus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Galeria extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_galeria);

		GridView gridView = (GridView) findViewById(R.id.grid_view);
		
		
		gridView.setAdapter(new ImageAdapter(this));

		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// Enviar l'id de la imatge per saber quina ha de fer gran
				Intent i = new Intent(getApplicationContext(), ImatgeComplerta.class);
				// passing array index
				i.putExtra("id", position);
				startActivity(i);
			}
		});
		
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