package com.example.practicalapsus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.Window;

public class SplashScreen extends Activity {
	private static final int SPLASH_TIME = 2 * 1000;// 2 seconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splashscreen);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(SplashScreen.this, Registre.class);
				startActivity(intent);

				SplashScreen.this.finish();

				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

			}
		}, SPLASH_TIME);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, SPLASH_TIME);

	}

	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
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