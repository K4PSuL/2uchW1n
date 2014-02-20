package com.iia.touchwin.request;

import org.joda.time.DateTime;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.iia.touchwin.contracts.ResultContract;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

public abstract class ResultRequest {

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

}
