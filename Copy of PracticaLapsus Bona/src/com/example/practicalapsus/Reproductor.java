package com.example.practicalapsus;

import java.io.File;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

public class Reproductor extends Activity {

	private VideoView videoView;
	private MediaController mediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reproductor);

		videoView = (VideoView) findViewById(R.id.videoView1);
		mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);

		videoView.setVideoURI(Uri.parse("android.resource://"
				+ getPackageName() + "/" + R.raw.videolapsus));
		videoView.start();
		mediaController.show();
		videoView.requestFocus();
		
		//Color de l'action bar
		 ActionBar ab = getActionBar(); 
		 ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136493"));    
		           ab.setBackgroundDrawable(colorDrawable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reproductor, menu);
		return true;
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
