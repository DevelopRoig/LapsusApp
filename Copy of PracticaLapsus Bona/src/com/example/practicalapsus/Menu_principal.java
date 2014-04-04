package com.example.practicalapsus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Menu_principal extends Activity {
	private ImageView imatge;
	private ImageButton grabadora, galeria, informacio, reproductor;
	private TextView nom;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		imatge = (ImageView) findViewById(R.id.imatge);
		nom = (TextView) findViewById(R.id.lblNomCognom);
		grabadora = (ImageButton) findViewById(R.id.grabadora);
		galeria = (ImageButton) findViewById(R.id.galeria);
		informacio = (ImageButton) findViewById(R.id.informacio);
		reproductor = (ImageButton) findViewById(R.id.video);
		mp = MediaPlayer.create(Menu_principal.this, R.raw.lapsus);
		mp.start();

		// Obtenir dades del Registre i reduir la imatge que ens arriva
		imatge.setImageBitmap(obtenirImatgeFromResource(getResources(),
				Registre.tempImageFile.getAbsolutePath(), 200, 200));
		String nomuser = getIntent().getStringExtra(Registre.NOM);
		String cognomuser = getIntent().getStringExtra(Registre.COGNOM);
		nom.setText(nomuser + " " + cognomuser);

		// Color de l'action bar
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(
				Color.parseColor("#136493"));
		ab.setBackgroundDrawable(colorDrawable);

		// Accio al fer click a la grabadora
		grabadora.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Menu_principal.this,
						GravacioAudio.class);
				startActivity(intent);
			}
		});

		// Accio al fer click a la galeria
		galeria.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Menu_principal.this, Galeria.class);
				startActivity(intent);
			}
		});

		// Accio al fer click a informacio
		informacio.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Menu_principal.this,
						Informacio.class);
				startActivity(intent);
			}
		});

		// Accio al fer click al reproductor
		reproductor.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(Menu_principal.this,
						Reproductor.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_imatges, menu);
		return true;
	}

	public static int calcularInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// alçada i amplada de la imatge
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		int heightRatio, widthRatio;
		if (height > reqHeight || width > reqWidth) {
			heightRatio = Math.round((float) height / (float) reqHeight);
			widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap obtenirImatgeFromResource(Resources res,
			String pathName, int reqWidth, int reqHeight) {
		// Primer, descodificar el bitmap amb inJustDecodeBounds=true
		// per comprovar les dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName);
		// Calcular inSampleSize
		options.inSampleSize = calcularInSampleSize(options, reqWidth,
				reqHeight);
		// Descodificar el bitmap amb el valor indicat de inSampleSize
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName);
	}

	public void onPause() {
		super.onPause();
		if (mp.isPlaying()) {
			mp.pause();
		}

	}

	public void onResume() {
		super.onResume();
		mp.start();
	}

	public void onStop() {
		super.onStop();
	}

	public void onDestroy() {
		super.onDestroy();
		if (mp.isPlaying()) {
			mp.stop();
			mp.release();
			mp = null;
		}

	}

}
