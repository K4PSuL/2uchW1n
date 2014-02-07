package com.iia.touchwin.contracts;

public class ResultContract {

	public final static String TABLE = "result";
	
	public final static String COL_ID = "id";
	public final static String COL_PLAYDATE = "playDate";
	public final static String COL_ID_GAME = "id_game";
	public final static String COL_PLAYER1 = "id_player1";
	public final static String COL_PLAYER2 = "id_player2";
	public final static String COL_SCOREP1 = "scoreP1";
	public final static String COL_SCOREP2 = "scoreP2";

	// On crée la requete SQL de creation de la base
	public final static String SCHEMA = "CREATE TABLE " + TABLE + "("
			+  COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+  COL_PLAYDATE + " DATETIME NOT NULL, "
			+  COL_ID_GAME + " INTEGER NOT NULL REFERENCES " + GameContract.TABLE + "(" + GameContract.COL_ID + "),"
			+  COL_PLAYER1 + " INTEGER NOT NULL REFERENCES " + PlayerContract.TABLE + "(" + PlayerContract.COL_ID + "),"
			+  COL_PLAYER2 + " INTEGER NOT NULL REFERENCES " + PlayerContract.TABLE + "(" + PlayerContract.COL_ID + "),"
			+  COL_SCOREP1 + " INTEGER NOT NULL, "
			+  COL_SCOREP2 + " INTEGER NOT NULL "
			+ ");";

	// Colonne a récupérer lors d'un GET en base
	public final static String[] COLS = {
			ResultContract.COL_ID,
			ResultContract.COL_PLAYDATE, 
			ResultContract.COL_ID_GAME,
			ResultContract.COL_PLAYER1,
			ResultContract.COL_PLAYER2,
			ResultContract.COL_SCOREP1,
			ResultContract.COL_SCOREP2
			};
}
