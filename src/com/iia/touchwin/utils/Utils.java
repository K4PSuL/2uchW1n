package com.iia.touchwin.utils;

import java.util.Random;

import junit.framework.Test;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.contracts.ResultContract;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;

public abstract class Utils {

	/**
	 * Joue un son
	 * 
	 * @param oContext
	 * @param oActivity
	 * @param resource
	 */
	public static void playSound(Activity oActivity, int resource) {

		SharedPreferences oSettings = oActivity.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		if (oSettings.getBoolean(Const.PREFERENCES_SOUND, true)) {
			MediaPlayer oMediaPlayer = (MediaPlayer) MediaPlayer.create(
					oActivity.getApplicationContext(), resource);
			oMediaPlayer.start();
		}
	}

	/**
	 * Retourne un Player par son identifiant
	 * 
	 * @param oActivity
	 * @param idPlayer
	 * @return
	 */
	public static Player getPlayer(Context oContext, int idPlayer) {

		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				oContext, Const.DATABASE, null, 1);

		// R�cup�ration de la BDD
		SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

		// Argument pour la condition de la requ�te SQL
		String[] whereArg = { String.valueOf(idPlayer) };

		// Requ�te sur la BDD
		Cursor oCursor = dataBase.query(PlayerContract.TABLE,
				PlayerContract.COLS, PlayerContract.COL_ID + "=?", whereArg,
				null, null, null);

		// Si au moins un r�sultat...
		if (oCursor.moveToFirst()) {

			DateTime dateCreate = DateUtils.formatStringToDate(oCursor
					.getString(oCursor
							.getColumnIndex(PlayerContract.COL_DATECREATE)),
					oContext);

			DateTime dateBirth = DateUtils.formatStringToDate(oCursor
					.getString(oCursor
							.getColumnIndex(PlayerContract.COL_BIRTHDATE)),
					oContext);

			Player oPlayer = new Player();
			oPlayer.setId(oCursor.getInt((oCursor
					.getColumnIndex(PlayerContract.COL_ID))));
			oPlayer.setLogin(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_LOGIN))));
			oPlayer.setPassword(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_PASSWORD))));
			oPlayer.setDateCreate(dateCreate);
			oPlayer.setAvatar(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_AVATAR))));
			oPlayer.setBirthdate(dateBirth);

			return oPlayer;

		} else {
			return null;
		}
	}

	/**
	 * Authentifie un utilisateur d'apres son mdp + login
	 * 
	 * @param oActivity
	 * @param login
	 * @param password
	 * @return
	 */
	public static Player authentication(Activity oActivity, EditText login,
			EditText password) {

		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				oActivity.getApplicationContext(), Const.DATABASE, null, 1);

		// R�cup�ration de la BDD
		SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

		// Argument pour la condition de la requ�te SQL
		String[] whereArg = { login.getText().toString(),
				password.getText().toString() };

		// Requ�te sur la BDD
		Cursor oCursor = dataBase.query(PlayerContract.TABLE,
				PlayerContract.COLS, PlayerContract.COL_LOGIN + "=? and "
						+ PlayerContract.COL_PASSWORD + "=?", whereArg, null,
				null, null);

		// Si au moins un r�sultat...
		if (oCursor.moveToFirst()) {

			DateTime dateCreate = DateUtils.formatStringToDate(oCursor
					.getString(oCursor
							.getColumnIndex(PlayerContract.COL_DATECREATE)),
					oActivity.getApplicationContext());

			DateTime dateBirth = DateUtils.formatStringToDate(oCursor
					.getString(oCursor
							.getColumnIndex(PlayerContract.COL_BIRTHDATE)),
					oActivity.getApplicationContext());

			Player oPlayer = new Player();
			oPlayer.setId(oCursor.getInt((oCursor
					.getColumnIndex(PlayerContract.COL_ID))));
			oPlayer.setLogin(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_LOGIN))));
			oPlayer.setPassword(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_PASSWORD))));
			oPlayer.setDateCreate(dateCreate);
			oPlayer.setAvatar(oCursor.getString((oCursor
					.getColumnIndex(PlayerContract.COL_AVATAR))));
			oPlayer.setBirthdate(dateBirth);

			return oPlayer;

		} else {

			password.setError("Erreur d'identifiant !");

			return null;
		}
	}

	/**
	 * Retourne un nombre al�atoire entre les deux valeurs pass� en param�tres
	 * (max exclu)
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		Random oRandom = new Random();

		int timeRound = oRandom.nextInt(max - min) + min;

		return timeRound;
	}

	/**
	 * Sauvegarde le score final de la partie en BDD
	 * 
	 * @param oPlayer1
	 * @param oPlayer2
	 * @param scoreP1
	 * @param scoreP2
	 * @param oGame
	 * @param oActivity
	 */
	public static void saveScore(Player oPlayer1, Player oPlayer2, int scoreP1,
			int scoreP2, Game oGame, Activity oActivity) {

		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				oActivity.getApplicationContext(), Const.DATABASE, null, 1);

		SQLiteDatabase dataBase = oDbHelper.getWritableDatabase();

		ContentValues myValuesResult = new ContentValues();

		myValuesResult.put(ResultContract.COL_PLAYDATE, DateTime.now()
				.toString("dd/MM/YYYY"));
		myValuesResult.put(ResultContract.COL_ID_GAME, oGame.getId());
		myValuesResult.put(ResultContract.COL_PLAYER1, oPlayer1.getId());
		myValuesResult.put(ResultContract.COL_PLAYER2, oPlayer2.getId());
		myValuesResult.put(ResultContract.COL_SCOREP1, scoreP1);
		myValuesResult.put(ResultContract.COL_SCOREP2, scoreP2);

		dataBase.insert(ResultContract.TABLE, null, myValuesResult);
	}

	/**
	 * Affiche une dialogue en fin de partit avec le r�sultat final + savegarde
	 * en BDD le score
	 * 
	 * @param oActivity
	 * @param oPlayer1
	 * @param oPlayer2
	 * @param scoreP1
	 * @param scoreP2
	 * @param oGame
	 */
	public static void dialogEndGame(final Activity oActivity, Player oPlayer1,
			Player oPlayer2, int scoreP1, int scoreP2, Game oGame) {

		final Activity oActivity2 = oActivity;

		final Dialog oDialogEndGame = new Dialog(oActivity2);

		final Player oPlayerWinner;

		if (scoreP1 > scoreP2) {
			oPlayerWinner = oPlayer1;
		} else {
			oPlayerWinner = oPlayer2;
		}

		oDialogEndGame.setContentView(R.layout.dialog_game_end);
		oDialogEndGame.setTitle(oPlayerWinner.getLogin() + Const.WINNER);

		final TextView lbPseudo = (TextView) oDialogEndGame
				.findViewById(R.id.txtPseudo);
		final TextView lbScore = (TextView) oDialogEndGame
				.findViewById(R.id.txtScore);
		final Button btnExitEndGame = (Button) oDialogEndGame
				.findViewById(R.id.btnExitEndGame);

		lbPseudo.setText(oPlayerWinner.getLogin());
		lbScore.setText(String.valueOf(scoreP1) + " - "
				+ String.valueOf(scoreP2));

		oDialogEndGame.show();

		playSound(oActivity2, R.raw.end);

		saveScore(oPlayer1, oPlayer2, scoreP1, scoreP2, oGame, oActivity);

		btnExitEndGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				oDialogEndGame.dismiss();
				oActivity2.finish();
			}
		});
	}
}
