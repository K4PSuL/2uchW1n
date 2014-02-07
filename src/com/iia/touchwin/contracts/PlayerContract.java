package com.iia.touchwin.contracts;

public class PlayerContract {

	public final static String TABLE = "player";
	
	public final static String COL_ID = "id";
	public final static String COL_LOGIN = "login";
	public final static String COL_PASSWORD = "password";
	public final static String COL_DATECREATE = "dateCreate";
	public final static String COL_AVATAR = "avatar";
	public final static String COL_ENABLE = "enable";
	public final static String COL_BIRTHDATE = "birthdate";

	// On crée la requete SQL de creation de la base
	public final static String SCHEMA = "CREATE TABLE " + TABLE + "("
			+  COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+  COL_LOGIN + " VARCHAR NOT NULL , "
			+  COL_PASSWORD + " VARCHAR NOT NULL, "
			+  COL_DATECREATE + " DATETIME NOT NULL, "
			+  COL_AVATAR + " VARCHAR NOT NULL, "
			+  COL_ENABLE + " BOOLEAN NOT NULL, "
			+  COL_BIRTHDATE + " DATETIME NOT NULL"
			+ ");";

	// Colonne a récupérer lors d'un GET en base
	public final static String[] COLS = {
			PlayerContract.COL_ID,
			PlayerContract.COL_LOGIN, 
			PlayerContract.COL_PASSWORD,
			PlayerContract.COL_DATECREATE,
			PlayerContract.COL_AVATAR,
			PlayerContract.COL_ENABLE,
			PlayerContract.COL_BIRTHDATE 
			};
}
