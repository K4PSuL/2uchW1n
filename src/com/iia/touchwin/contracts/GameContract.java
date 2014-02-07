package com.iia.touchwin.contracts;

public class GameContract {

	public final static String TABLE = "game";

	public final static String COL_ID = "id";
	public final static String COL_LIBELLE = "libelle";

	// On crée la requete SQL de creation de la base
	public final static String SCHEMA = "CREATE TABLE " + TABLE + "(" + COL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_LIBELLE
			+ " VARCHAR NOT NULL " 
			+ ");";

	// Colonne a récupérer lors d'un GET en base
	public final static String[] COLS = { GameContract.COL_ID, GameContract.COL_LIBELLE };
}
