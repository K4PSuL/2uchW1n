package com.iia.touchwin.utils;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.views.HomeActivity;
import com.iia.touchwin.views.MainActivity;

public abstract class Utils {

	/**
	 * Permet de jouer un son
	 * 
	 * @param oContext
	 * @param oActivity
	 * @param resource
	 */
	public static void playSound(Context oContext, Activity oActivity,
			int resource) {

		SharedPreferences oSettings = oActivity.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		if (oSettings.getBoolean(Const.PREFERENCES_SOUND, true)) {
			MediaPlayer oMediaPlayer = (MediaPlayer) MediaPlayer.create(
					oContext, resource);
			oMediaPlayer.start();
		}
	}
	
	/**
	 * Permet d'authentifier l'utilisateur
	 * @param oActivity
	 * @param login
	 * @param password
	 * @return
	 */
	public static Player authentication(Activity oActivity, String login, String password) {
		
		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				oActivity.getApplicationContext(), Const.DATABASE, null, 1);

		// Récupération de la BDD
		SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

		// Argument pour la condition de la requête SQL
		String[] whereArg = { login, password };

		// Requête sur la BDD
		Cursor oCursor = dataBase.query(PlayerContract.TABLE,
				PlayerContract.COLS, PlayerContract.COL_LOGIN
						+ "=? and " + PlayerContract.COL_PASSWORD
						+ "=?", whereArg, null, null, null);

		// Si au moins un résultat...
		if (oCursor.moveToFirst()) {

			@SuppressWarnings("deprecation")
			Date dateCreate = new Date(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_DATECREATE))));

			@SuppressWarnings("deprecation")
			Date dateBirth = new Date(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_BIRTHDATE))));

			Player thePlayer = new Player();
			thePlayer.setId(oCursor.getInt((oCursor
					.getColumnIndex(PlayerContract.COL_ID))));
			thePlayer.setLogin(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_LOGIN))));
			thePlayer.setPassword(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_PASSWORD))));
			thePlayer.setDateCreate(dateCreate);
			thePlayer.setAvatar(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_AVATAR))));
			thePlayer.setBirthdate(dateBirth);
			
			return thePlayer;
			
		} else {
			Toast.makeText(oActivity.getApplicationContext(), Const.ERREUR_LOGIN,
					Toast.LENGTH_LONG).show();
			
			return null;
		}
	}
}
