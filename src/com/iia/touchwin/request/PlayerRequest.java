package com.iia.touchwin.request;

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.DateUtils;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

public abstract class PlayerRequest {

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
}
