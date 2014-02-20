package com.iia.touchwin.ui;

import org.joda.time.DateTime;

import com.iia.touchwin.R;
import com.iia.touchwin.request.PlayerRequest;
import com.iia.touchwin.utils.*;
import com.iia.touchwin.entities.*;
import com.iia.touchwin.contracts.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);

		final Button btnConnection = (Button) findViewById(R.id.btnConnection);
		final Button btnAddData = (Button) findViewById(R.id.btnAddData);
		final Button btnRegister = (Button) findViewById(R.id.btnRegister);
		final EditText editLogin = (EditText) findViewById(R.id.editLogin);
		final EditText editPassword = (EditText) findViewById(R.id.editPassword);
		final String login;

		final SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		login = oSettings.getString(Const.PREFERENCES_LOGIN, "");

		if (login != "") {
			// On popule le formulaire si un Login est enregistré et on donne le
			// focus au password
			editLogin.setText(login);
			editPassword.requestFocus();
		}

		btnConnection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// On vérifie les identifiants fournis par l'utilisateur
				Player oPlayer1 = PlayerRequest.authentication(
						MainActivity.this, editLogin, editPassword);

				if (oPlayer1 != null) {
					Bundle dataBundle = new Bundle();
					dataBundle.putSerializable(Const.BUNDLE_PLAYER,
							(Player) oPlayer1);

					Intent intentOpenHome = new Intent(MainActivity.this,
							HomeActivity.class);

					// On envoie le Player à l'autre vue
					intentOpenHome.putExtras(dataBundle);

					startActivity(intentOpenHome);
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
				myValuesPlayer.put(PlayerContract.COL_PASSWORD, "");
				new DateTime();
				myValuesPlayer.put(PlayerContract.COL_DATECREATE, DateTime
						.now().toString("dd/MM/YYYY"));
				myValuesPlayer.put(PlayerContract.COL_AVATAR, "/img/lokoi.png");
				myValuesPlayer.put(PlayerContract.COL_ENABLE, true);
				myValuesPlayer.put(PlayerContract.COL_BIRTHDATE, "17/07/1992");

				dataBase.insert(PlayerContract.TABLE, null, myValuesPlayer);

				ContentValues myValuesPlayer2 = new ContentValues();
				myValuesPlayer2.put(PlayerContract.COL_LOGIN, "Chouk");
				myValuesPlayer2.put(PlayerContract.COL_PASSWORD, "");
				new DateTime();
				myValuesPlayer2.put(PlayerContract.COL_DATECREATE, DateTime
						.now().toString("dd/MM/YYYY"));
				myValuesPlayer2
						.put(PlayerContract.COL_AVATAR, "/img/chouk.png");
				myValuesPlayer2.put(PlayerContract.COL_ENABLE, true);
				myValuesPlayer2.put(PlayerContract.COL_BIRTHDATE, "23/05/1993");

				dataBase.insert(PlayerContract.TABLE, null, myValuesPlayer2);

				/* GAME */

				ContentValues myValuesGame = new ContentValues();
				myValuesGame.put(GameContract.COL_LIBELLE, "Couleur");

				dataBase.insert(GameContract.TABLE, null, myValuesGame);

				ContentValues myValuesGame2 = new ContentValues();
				myValuesGame2.put(GameContract.COL_LIBELLE, "Calcul");

				dataBase.insert(GameContract.TABLE, null, myValuesGame2);

				/* RESULT */

				ContentValues myValuesResult1 = new ContentValues();
				new DateTime();
				myValuesResult1.put(ResultContract.COL_PLAYDATE, DateTime.now()
						.toString("dd/MM/YYYY"));
				myValuesResult1.put(ResultContract.COL_ID_GAME, 1);
				myValuesResult1.put(ResultContract.COL_PLAYER1, 1);
				myValuesResult1.put(ResultContract.COL_PLAYER2, 2);
				myValuesResult1.put(ResultContract.COL_SCOREP1, 10);
				myValuesResult1.put(ResultContract.COL_SCOREP2, 5);

				dataBase.insert(ResultContract.TABLE, null, myValuesResult1);

				ContentValues myValuesResult2 = new ContentValues();
				new DateTime();
				myValuesResult2.put(ResultContract.COL_PLAYDATE, DateTime.now()
						.toString("dd/MM/YYYY"));
				myValuesResult2.put(ResultContract.COL_ID_GAME, 1);
				myValuesResult2.put(ResultContract.COL_PLAYER1, 1);
				myValuesResult2.put(ResultContract.COL_PLAYER2, 2);
				myValuesResult2.put(ResultContract.COL_SCOREP1, 7);
				myValuesResult2.put(ResultContract.COL_SCOREP2, 8);

				dataBase.insert(ResultContract.TABLE, null, myValuesResult2);
			}
		});

		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentOpenHome = new Intent(MainActivity.this,
						RegisterActivity.class);

				startActivity(intentOpenHome);
			}
		});
	}
}
