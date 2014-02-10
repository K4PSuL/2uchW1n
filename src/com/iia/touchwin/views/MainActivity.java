package com.iia.touchwin.views;

import java.util.Date;

import org.joda.time.DateTime;

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
		final String login;
		
		final SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);
		
		login = oSettings.getString(Const.PREFERENCES_LOGIN, "");
		
		if (login != "") {
			// On popule le formulaire si un Login est enregistré et on donne le focus au password
			editLogin.setText(login);
			editPassword.requestFocus();
		}

		btnConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
						MainActivity.this, Const.DATABASE, null, 1);

				// Récupération de la BDD
				SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

				// Argument pour la condition de la requête SQL
				String[] whereArg = { editLogin.getText().toString(),
						editPassword.getText().toString() };

				// Requête sur la BDD
				Cursor oCursor = dataBase.query(PlayerContract.TABLE,
						PlayerContract.COLS, PlayerContract.COL_LOGIN
								+ "=? and " + PlayerContract.COL_PASSWORD
								+ "=?", whereArg, null, null, null);

				// Si au moin un résultat...
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
					dataBundle.putSerializable(Const.BUNDLE_PLAYER,
							(Player) thePlayer);

					Intent intentOpenHome = new Intent(MainActivity.this,
							HomeActivity.class);

					// On envoie le Player à l'autre vue
					intentOpenHome.putExtras(dataBundle);

					startActivity(intentOpenHome);

				} else {
					Toast.makeText(MainActivity.this, Const.ERREUR_LOGIN,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btnAddData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
						MainActivity.this, Const.DATABASE, null, 1);

				SQLiteDatabase dataBase = oDbHelper.getWritableDatabase();

				/* PLAYER */
				
				ContentValues myValuesPlayer = new ContentValues();
				myValuesPlayer.put(PlayerContract.COL_LOGIN, "Lokoi");
				myValuesPlayer.put(PlayerContract.COL_PASSWORD, "azqqza");
				myValuesPlayer.put(PlayerContract.COL_DATECREATE,
						new Date().toString());
				myValuesPlayer.put(PlayerContract.COL_AVATAR, "/img/lokoi.png");
				myValuesPlayer.put(PlayerContract.COL_ENABLE, true);
				myValuesPlayer.put(PlayerContract.COL_BIRTHDATE, "17/07/1992");

				dataBase.insert(PlayerContract.TABLE, null, myValuesPlayer);

				ContentValues myValuesPlayer2 = new ContentValues();
				myValuesPlayer2.put(PlayerContract.COL_LOGIN, "Chouk");
				myValuesPlayer2.put(PlayerContract.COL_PASSWORD, "aze");
				myValuesPlayer2.put(PlayerContract.COL_DATECREATE,
						new Date().toString());
				myValuesPlayer2.put(PlayerContract.COL_AVATAR, "/img/chouk.png");
				myValuesPlayer2.put(PlayerContract.COL_ENABLE, true);
				myValuesPlayer2.put(PlayerContract.COL_BIRTHDATE, "23/05/1993");

				dataBase.insert(PlayerContract.TABLE, null, myValuesPlayer2);

				/* GAME */
				
				ContentValues myValuesGame = new ContentValues();
				myValuesGame.put(GameContract.COL_LIBELLE, "Reflexe");

				dataBase.insert(GameContract.TABLE, null, myValuesGame);
				
				/* RESULT */
				
				ContentValues myValuesResult1 = new ContentValues();
				myValuesResult1.put(ResultContract.COL_PLAYDATE, new DateTime().toString());
				myValuesResult1.put(ResultContract.COL_ID_GAME, 1);
				myValuesResult1.put(ResultContract.COL_PLAYER1,1);
				myValuesResult1.put(ResultContract.COL_PLAYER2, 2);
				myValuesResult1.put(ResultContract.COL_SCOREP1, 10);
				myValuesResult1.put(ResultContract.COL_SCOREP2, 5);

				dataBase.insert(ResultContract.TABLE, null, myValuesResult1);
				
				ContentValues myValuesResult2 = new ContentValues();
				myValuesResult2.put(ResultContract.COL_PLAYDATE, new DateTime().toString());
				myValuesResult2.put(ResultContract.COL_ID_GAME, 1);
				myValuesResult2.put(ResultContract.COL_PLAYER1, 1);
				myValuesResult2.put(ResultContract.COL_PLAYER2, 2);
				myValuesResult2.put(ResultContract.COL_SCOREP1, 7);
				myValuesResult2.put(ResultContract.COL_SCOREP2, 8);

				dataBase.insert(ResultContract.TABLE, null, myValuesResult2);
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
