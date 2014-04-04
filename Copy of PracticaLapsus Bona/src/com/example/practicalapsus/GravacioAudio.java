package com.example.practicalapsus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GravacioAudio extends Activity implements OnClickListener {

	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;

	private Llista l;
	private Button btnLlista;

	private ImageView imgRec;
	private ImageView rec;
	private ImageView imgPlay;
	private ImageView imgList;
	private ImageView imgMicro;
	private MediaRecorder mRecorder = null;
	private ArrayList<String> llista;

	public ArrayList<String> getLlista() {
		return llista;
	}

	public void setLlista(ArrayList<String> llista) {
		this.llista = llista;
	}

	private boolean mStartPlaying = true;
	private boolean mStartRecording = true;

	private MediaPlayer mPlayer = null;

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	public void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			String fileName = llista.get(0).toString();
			Toast.makeText(getApplicationContext(), fileName,
					Toast.LENGTH_SHORT).show();
			mPlayer.setDataSource(fileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	private ArrayList<String> obtenirRutaFitxers(String directori) {
		ArrayList<String> fitxers = new ArrayList<String>();
		File f = new File(directori);
		f.mkdirs();
		File[] taulaFitxers = f.listFiles();
		if (taulaFitxers.length == 0)
			return null;
		else {
			for (int i = 0; i < taulaFitxers.length; i++)
				fitxers.add(directori + "/" + taulaFitxers[i].getName());
		}
		return fitxers;
	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	public GravacioAudio() {
		l = new Llista();
		if (obtenirRutaFitxers(Environment.getExternalStorageDirectory()
				+ "/record") == null) {
			mFileName = Environment.getExternalStorageDirectory() + "/record";
			mFileName += "/audio_" + 1 + ".3gp";
		} else {
			int i = obtenirRutaFitxers(
					Environment.getExternalStorageDirectory() + "/record")
					.size() + 1;
			i = i + 1;
			mFileName = Environment.getExternalStorageDirectory() + "/record";
			mFileName += "/audio_" + i + ".3gp";
			actualitzarLlista();
		}
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.audio);
		imgRec = (ImageView) findViewById(R.id.imgRec);
		imgPlay = (ImageView) findViewById(R.id.imgPlay);
		imgList = (ImageView) findViewById(R.id.imgLlista);
		imgMicro = (ImageView) findViewById(R.id.imgMicro);
		rec = (ImageView) findViewById(R.id.rec);
		
		// Color de l'action bar
				ActionBar ab = getActionBar();
				ColorDrawable colorDrawable = new ColorDrawable(
						Color.parseColor("#136493"));
				ab.setBackgroundDrawable(colorDrawable);

		imgList.setOnClickListener(this);
		imgRec.setOnClickListener(this);
		imgPlay.setOnClickListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.imgPlay:
			if (obtenirRutaFitxers(Environment.getExternalStorageDirectory()
					+ "/record") != null) {
				actualitzarLlista();
				onPlay(mStartPlaying);
			} else {
				Toast.makeText(getApplicationContext(),
						"No hi ha cap audio!!!", Toast.LENGTH_SHORT).show();
			}

			if (mStartPlaying) {
				imgPlay.setImageResource(R.drawable.stop);

			} else {
				imgPlay.setImageResource(R.drawable.play);

			}
			mStartPlaying = !mStartPlaying;
			break;
		case R.id.imgRec:
			if (obtenirRutaFitxers(Environment.getExternalStorageDirectory()
					+ "/record") != null) {
				actualitzarLlista();
			}
			onRecord(mStartRecording);
			if (mStartRecording) {
				imgRec.setImageResource(R.drawable.stop);
				rec.setVisibility(View.VISIBLE);
			} else {
				imgRec.setImageResource(R.drawable.record);
				rec.setVisibility(View.INVISIBLE);
			}
			mStartRecording = !mStartRecording;
			break;
		case R.id.imgLlista:
			if (obtenirRutaFitxers(Environment.getExternalStorageDirectory()
					+ "/record") == null) {
				Toast.makeText(GravacioAudio.this,
						"No hi ha cap nota d'audio guardada!!",
						Toast.LENGTH_SHORT).show();

			} else {
				Intent i = new Intent(this, Llista.class);
				startActivity(i);
			}
		default:
			break;
		}
	}

	public void actualitzarLlista() {
		llista = obtenirRutaFitxers(Environment.getExternalStorageDirectory()
				+ "/record");
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

