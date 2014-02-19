package com.iia.touchwin.request;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.touchwin.contracts.GameContract;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

public abstract class GameRequest {

	/**
	 * Retourne la liste de tous les jeux
	 * 
	 * @return
	 */
	public static ArrayList<Game> getAllGame(Activity oActivity) {

		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				oActivity.getApplicationContext(), Const.DATABASE, null, 1);

		SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

		Cursor oCursor = dataBase.query(GameContract.TABLE, GameContract.COLS,
				null, null, null, null, null);

		ArrayList<Game> aGames = new ArrayList<Game>();

		if (oCursor.moveToFirst()) {
			do {
				Game oGame = new Game();

				oGame.setId(oCursor.getInt(oCursor
						.getColumnIndex(GameContract.COL_ID)));
				oGame.setLibelle(oCursor.getString(oCursor
						.getColumnIndex(GameContract.COL_LIBELLE)));
				aGames.add(oGame);

			} while (oCursor.moveToNext());
		}

		return aGames;

	}
}
