package com.iia.touchwin.views;

import java.util.Date;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		final EditText editPlayer1 = (EditText) findViewById(R.id.editPlayer1);
		final EditText editPlayer2 = (EditText) findViewById(R.id.editPlayer2);
		final EditText editGame = (EditText) findViewById(R.id.editGame);
		final Button btnGo = (Button) findViewById(R.id.btnGo);

		// On récupère le Player
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		// On popule le formulaire avec le login du Player 1
		editPlayer1.setText(thePlayer.getLogin());

		
		/* CHOIX DU JOUEUR 2 */
		editPlayer2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create custom dialog object
				final Dialog dialog = new Dialog(PlayActivity.this);
				// Include dialog.xml file
				dialog.setContentView(R.layout.dialog_player);
				// Set dialog title
				dialog.setTitle(R.string.title_dialog_player);
				// Show the dialog
				dialog.show();

				Button btnValidPlayer = (Button) dialog.findViewById(R.id.btnValid);
				
				// if button is clicked, close the custom dialog
				btnValidPlayer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {	

       				 	EditText editLoginDialog = (EditText) dialog.findViewById(R.id.editLogin);
       				 	EditText editPwdDialog = (EditText) dialog.findViewById(R.id.editPassword);
       		
       				 	
	       				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
	     						PlayActivity.this, Const.DATABASE, null, 1);
	
	     				// Récupération de la BDD
	     				SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();
	
	     				// Argument pour la condition de la requête SQL
	     				String[] whereArg = { editLoginDialog.getText().toString(),
	     						editPwdDialog.getText().toString() };
	
	     				// Requête sur la BDD
	     				Cursor oCursor = dataBase.query(PlayerContract.TABLE,
	     						PlayerContract.COLS, PlayerContract.COL_LOGIN
	     								+ "=? and " + PlayerContract.COL_PASSWORD
	     								+ "=?", whereArg, null, null, null);
	
	     				// Si au moins un résultat...
	     				if (oCursor.moveToFirst()) {
	
	     					@SuppressWarnings("deprecation")
	     					Date dateCreate = new Date(oCursor.getString((oCursor
	     							.getColumnIndex(PlayerContract.COL_DATECREATE))));
	
	     					@SuppressWarnings("deprecation")
	     					Date dateBirth = new Date(oCursor.getString((oCursor
	     							.getColumnIndex(PlayerContract.COL_BIRTHDATE))));
	
	     					Player Player2 = new Player();
	     					Player2.setId(oCursor.getInt((oCursor
	     							.getColumnIndex(PlayerContract.COL_ID))));
	     					Player2.setLogin(oCursor.getString((oCursor
	     							.getColumnIndex(PlayerContract.COL_LOGIN))));
	     					Player2.setPassword(oCursor.getString((oCursor
	     							.getColumnIndex(PlayerContract.COL_PASSWORD))));
	     					Player2.setDateCreate(dateCreate);
	     					Player2.setAvatar(oCursor.getString((oCursor
	     							.getColumnIndex(PlayerContract.COL_AVATAR))));
	     					Player2.setBirthdate(dateBirth);
	
	     					Bundle dataBundle = new Bundle();
	     					dataBundle.putSerializable(Const.BUNDLE_PLAYER2,
	     							(Player) Player2);
	
	     					// Close dialog
							dialog.dismiss();
							
							editPlayer2.setText(Player2.getLogin().toString());
	
	     				} else {
	     					Toast.makeText(PlayActivity.this, Const.ERREUR_LOGIN,
	     							Toast.LENGTH_LONG).show();
	     				}
     				
					}
				});
			}
		});
		
		
		/* CHOIX DU JEU */
		editGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create custom dialog object
				final Dialog dialog = new Dialog(PlayActivity.this);
				// Include dialog.xml file
				dialog.setContentView(R.layout.dialog_game);
				// Set dialog title
				dialog.setTitle(R.string.title_dialog_game);
				// Show dialog
				dialog.show();

				Button btnValidGame = (Button) dialog
						.findViewById(R.id.btnValid);
				// if button is clicked, close the custom dialog
				btnValidGame.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Close dialog
						dialog.dismiss();
					}
				});

			}
		});

		
		/* CHOIX DU TEMPS / NOMBRE DE ROUNDS */
		final RadioGroup radioGroupTime = (RadioGroup) findViewById(R.id.radioGroup);
		
		// get selected radio button from radioGroup
		int selectedId = radioGroupTime.getCheckedRadioButtonId();
		 
		// find the radiobutton by returned id
		final RadioButton radioBtn = (RadioButton) findViewById(selectedId);
		
		final Bundle dataBundle = new Bundle();
		dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);
		dataBundle.putSerializable(Const.BUNDLE_PLAYER2, (Player) thePlayer);
//		dataBundle.putString(Const.BUNDLE_GAME, (Game) oGame);
//		dataBundle.putString(Const.BUNDLE_TIME, editTime.getText().toString());
		
		/* DEMARRAGE DU JEU */
		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Bundle dataBundle = new Bundle();
				dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);
				dataBundle.putSerializable(Const.BUNDLE_PLAYER2, (Player) thePlayer);
				dataBundle.putSerializable(Const.BUNDLE_GAME, editGame.getText().toString());
				dataBundle.putSerializable(Const.BUNDLE_TIME, radioBtn.getText().toString());
				
				Intent intentOpenGame = new Intent(PlayActivity.this,
						GameActivity.class);

				intentOpenGame.putExtras(dataBundle);

				startActivity(intentOpenGame);
			}
		});

	}
	
	private static String padding_str(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
		}
}
