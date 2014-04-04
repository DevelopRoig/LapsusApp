package com.example.practicalapsus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Registre extends Activity {

	public final static String NOM = "NOM";
	public final static String COGNOM = "COGNOM";
	public final static File IMATGE = null;
	// un codi per l'aplicació a obrir
	static final int CAMERA_APP_CODE = 100;

	// on visualitzarem la imatge capturada
	private ImageView imatge;
	private TextView nom;
	private TextView cognom;
	private Button entra;
	// el fitxer on es guardarà la imatge
	public static File tempImageFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registre);
		imatge = (ImageView) findViewById(R.id.foto);
		nom = (TextView) findViewById(R.id.editNom);
		cognom = (TextView) findViewById(R.id.editCognom);
		entra = (Button) findViewById(R.id.Entrar);
		
		
		
		//Color de l'action bar
		 ActionBar ab = getActionBar(); 
		 ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#136493"));    
		           ab.setBackgroundDrawable(colorDrawable);
		           
		entra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tempImageFile == null
						|| nom.getText().toString().compareTo("") == 0
						|| cognom.getText().toString().compareTo("") == 0) {
					Toast.makeText(Registre.this,
							"No pots accedir sense emplenar tots els camps!!",
							Toast.LENGTH_SHORT).show();

				} else {
					Intent i = new Intent(Registre.this, Menu_principal.class);
					Bundle b = new Bundle();
					b.putSerializable(NOM, nom.getText().toString());
					b.putSerializable(COGNOM, cognom.getText().toString());
					b.putSerializable("imatge", tempImageFile);
					i.putExtras(b);
					startActivity(i);
				}
			}
		});

	}

	/**
	 * Mètode que comprova si hi ha una aplicició per a captura de fotos
	 * 
	 * @param context
	 * @param action
	 * @return true si existeix, false en cas contrari
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/**
	 * Mètode que respon a l'event clic del botó
	 * 
	 * @param view
	 * @throws IOException
	 */
	public void ferUnaFoto(View view) throws IOException {
		if (isIntentAvailable(this, MediaStore.ACTION_IMAGE_CAPTURE)) {
			// intenció de fer una foto
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			// crear la ruta del fitxer on desar la foto
			tempImageFile = crearFitxer();
			// li passem paràmetres a l'Inent per indicar que es vol guarda
			// la captura en un fitxer
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempImageFile));
			// inciar l'intent
			startActivityForResult(takePictureIntent, CAMERA_APP_CODE);
		} else {
			Toast.makeText(this, "No hi ha cap aplicació per capturar fotos",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_APP_CODE) {
			if (resultCode == RESULT_OK) {
				try {
					// mostrar el fitxer que ha desat l'app de captura
					imatge.setImageBitmap(Media.getBitmap(getContentResolver(),
							Uri.fromFile(tempImageFile)));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO he de gestionar els errors
					e.printStackTrace();
				}

				// mostrar els bytes rebuts de l'intent
				// imatge.setImageBitmap((Bitmap) data.getExtras().get("data"));

			}
		}
	}

	/**
	 * Crea la ruta absoluta per a un nou fitxer temporal
	 * 
	 * @return L'objecte File que representa el fitxer
	 */
	private File crearFitxer() {
		// Create an image file name

		String imageFileName = "foto.jpg";
		File path = new File(Environment.getExternalStorageDirectory(),
				this.getPackageName());
		if (!path.exists())
			path.mkdirs();

		return new File(path, imageFileName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registre, menu);
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
