package com.iia.touchwin.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.touchwin.contracts.*;

public class TouchWinSqlLiteOpenHelper extends SQLiteOpenHelper {

	
	public TouchWinSqlLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		// on execute la requete de creation de base de donnée si elle n'existe pas !
		
		db.execSQL(GameContract.SCHEMA);
		db.execSQL(PlayerContract.SCHEMA);
		db.execSQL(ResultContract.SCHEMA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
//		if (oldVersion < newVersion) {
//			db.delete(UserContract.SCHEMA, null, null);
//			db.delete(PostContract.SCHEMA, null, null);
//			db.delete(CommentContract.SCHEMA, null, null);
//		}
	}

}
