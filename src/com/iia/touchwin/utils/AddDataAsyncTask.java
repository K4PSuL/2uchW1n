package com.iia.touchwin.utils;

import java.lang.reflect.Array;

import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Result;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class AddDataAsyncTask extends AsyncTask<Activity, Integer, Void> {
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Toast.makeText(getApplicationContext(),
				"Chargement des données en base...", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPostExecute(Void result) {
		Toast.makeText(getApplicationContext(),
				"Données chargées avec succès !", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	protected Void doInBackground(Activity... params) {
		// TODO Auto-generated method stub
		return null;
	}
}
