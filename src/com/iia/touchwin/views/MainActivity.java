package com.iia.touchwin.views;

import java.util.Date;

import com.iia.touchwin.R;
import com.iia.touchwin.utils.*;
import com.iia.touchwin.entities.*;
import com.iia.touchwin.contracts.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		
		final Button btnConnection = (Button) findViewById(R.id.btnConnection);
		final Button btnAddData = (Button) findViewById(R.id.btnAddData);
		final EditText editLogin = (EditText) findViewById(R.id.editLogin);
		final EditText editPassword = (EditText) findViewById(R.id.editPassword);

		SharedPreferences oSetting = this.getSharedPreferences(
				Const.PREFERENCES_CONNECTION, Context.MODE_PRIVATE);

		btnConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
						MainActivity.this, "touchwin", null, 1);

				SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

				String[] whereArg = { editLogin.getText().toString(),
						editPassword.getText().toString() };

				Cursor oCursor = dataBase.query(PlayerContract.TABLE,
						PlayerContract.COLS, "login=? and password=?", whereArg,
						null, null, null);

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
					
					Bundle dataBundle = new Bundle();
					dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);

					Intent intentOpenHome = new Intent(MainActivity.this,
							HomeActivity.class);
					
					intentOpenHome.putExtras(dataBundle);
					
					startActivity(intentOpenHome);
					
				} else {
					Toast.makeText(MainActivity.this,
							"Erreur, login incorrect", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		
		btnAddData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
						MainActivity.this, "touchwin", null, 1);
				
				SQLiteDatabase dataBase = oDbHelper.getWritableDatabase();

				ContentValues myValues = new ContentValues();
				myValues.put(PlayerContract.COL_LOGIN, "Lokoi");
				myValues.put(PlayerContract.COL_PASSWORD, "azqqza");
				myValues.put(PlayerContract.COL_DATECREATE, new Date().toString());
				myValues.put(PlayerContract.COL_AVATAR, "/img/lokoi.png");
				myValues.put(PlayerContract.COL_ENABLE, true);
				myValues.put(PlayerContract.COL_BIRTHDATE, "17/07/1992");
				
				dataBase.insert(PlayerContract.TABLE, null, myValues);
				
				ContentValues myValues2 = new ContentValues();
				myValues2.put(PlayerContract.COL_LOGIN, "Chouk");
				myValues2.put(PlayerContract.COL_PASSWORD, "aze");
				myValues2.put(PlayerContract.COL_DATECREATE, new Date().toString());
				myValues2.put(PlayerContract.COL_AVATAR, "/img/chouk.png");
				myValues2.put(PlayerContract.COL_ENABLE, true);
				myValues2.put(PlayerContract.COL_BIRTHDATE, "23/05/1993");
				
				dataBase.insert(PlayerContract.TABLE, null, myValues2);
			}	
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
