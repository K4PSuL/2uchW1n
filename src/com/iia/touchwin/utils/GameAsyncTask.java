package com.iia.touchwin.utils;

import java.lang.reflect.Array;

import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Result;

import android.os.AsyncTask;
import android.widget.Toast;
/*
public class GameAsyncTask extends AsyncTask<Void, Integer, Void> {
	
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(getApplicationContext(),
				"Début du traitement asynchrone", Toast.LENGTH_LONG).show();
	}

	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		// Mise à jour de la ProgressBar
		mProgressBar.setProgress(values[0]);
	}

	protected Void doInBackground(Void... arg0) {

		int progress;
		for (progress = 0; progress <= 100; progress++) {
			for (int i = 0; i < 1000000; i++) {
			}
			// la méthode publishProgress met à jour l'interface en invoquant la
			// méthode onProgressUpdate
			publishProgress(progress);
			progress++;
		}
		return null;
	}

	protected void onPostExecute(Void result) {
		Toast.makeText(getApplicationContext(),
				"Le traitement asynchrone est terminé", Toast.LENGTH_LONG)
				.show();
	}

	protected Result doInBackground(Params... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
*/