package com.iia.touchwin.utils;

import java.util.Random;

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.contracts.ResultContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.views.StatsActivity;

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

			
			DateTime dateCreate = DateUtils.formatStringToDate(oCursor.getString(oCursor
					.getColumnIndex(PlayerContract.COL_DATECREATE)), oActivity.getApplicationContext());
			
			DateTime dateBirth = DateUtils.formatStringToDate(oCursor.getString(oCursor
					.getColumnIndex(PlayerContract.COL_BIRTHDATE)), oActivity.getApplicationContext());

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
	
	/**
	 * Retourne un nombre aléatoire entre les deux valeurs passé en paramètres
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomTime(int min, int max) {
		Random oRandom = new Random();
		
		int timeRound = oRandom.nextInt(max-min) + min;

		return timeRound;
	}
	
	/**
	 * Retourne un boolean permettant de savoir si la réponse du round est faux ou vrai
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean randomFalse(int min, int max) {
		int trueOrFalse = randomTime(min, max);
		
		boolean isFalse = false;
		
		if (trueOrFalse == 1) {
			isFalse = true;
		}
		
		return isFalse;
	}
}
