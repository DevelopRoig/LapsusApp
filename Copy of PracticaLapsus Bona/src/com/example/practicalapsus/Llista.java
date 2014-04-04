package com.example.practicalapsus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Llista extends Activity {
	private GravacioAudio ga;
	private MediaPlayer mPlayer;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.llista_fitxers);
		// Color de l'action bar
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#136493"));
		ab.setBackgroundDrawable(colorDrawable);
		ListView lv;
		ArrayList<String> fitxersEnCarpeta = obtenirFitxers(Environment
				.getExternalStorageDirectory() + "/record");
		lv = (ListView) findViewById(R.id.llista);

		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fitxersEnCarpeta));

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					final int position, long id) {

				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							ga = new GravacioAudio();
							mPlayer = new MediaPlayer();
							try {
								String fileName = ga.getLlista().get(position)
										.toString();
								Toast.makeText(getApplicationContext(),
										fileName, Toast.LENGTH_SHORT).show();
								mPlayer.setDataSource(fileName);
								mPlayer.prepare();
								mPlayer.start();
							} catch (IOException e) {
								e.printStackTrace();
							}
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Llista.this);
				builder.setMessage("\t" + "Vols reproduir aquest gravació")
						.setPositiveButton("Si", dialogClickListener)
						.setNegativeButton("No", dialogClickListener).show();
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}

	}

	public ArrayList<String> obtenirFitxers(String directori) {
		ArrayList<String> fitxers = new ArrayList<String>();
		File f = new File(directori);
		f.mkdirs();
		File[] taulaFitxers = f.listFiles();
		if (taulaFitxers.length == 0)
			return null;
		else {
			for (int i = 0; i < taulaFitxers.length; i++)
				fitxers.add(taulaFitxers[i].getName());
		}
		return fitxers;
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
