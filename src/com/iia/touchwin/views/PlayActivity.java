package com.iia.touchwin.views;

import java.util.ArrayList;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.GameContract;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;
import com.iia.touchwin.utils.Utils;
import com.iia.touchwin.utils.QustomDialogBuilder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity {

	private Player oPlayer2;
	final String RED = "#c80000";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		final EditText editPlayer1 = (EditText) findViewById(R.id.editPlayer1);
		final EditText editPlayer2 = (EditText) findViewById(R.id.editPlayer2);
		final EditText editGame = (EditText) findViewById(R.id.editGame);
		final Button btnGo = (Button) findViewById(R.id.btnGo);
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		final Game oGame = new Game();
		final RadioGroup radioGroupTime = (RadioGroup) findViewById(R.id.radioGroup);
		final SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER2, Context.MODE_PRIVATE);

		/* CHOIX DU TEMPS / NOMBRE DE ROUNDS */
		int selectedId = radioGroupTime.getCheckedRadioButtonId();
		final RadioButton radioBtn = (RadioButton) findViewById(selectedId);

		editPlayer1.setText(thePlayer.getLogin());

		/* CHOIX DU JOUEUR 2 */
		editPlayer2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final QustomDialogBuilder oDialogChoicePlayer = new QustomDialogBuilder(v.getContext()).
						setTitle(R.string.title_dialog_player).
						setTitleColor(RED).
						setDividerColor(RED).
						setCustomView(R.layout.dialog_player, v.getContext()).
						setIcon(getResources().getDrawable(R.drawable.ic_launcher));
				
				final AlertDialog oDialogPlayer = oDialogChoicePlayer.show();
				
				View oDialogP = oDialogChoicePlayer.getViewById(R.layout.dialog_player);
				
				/*
				final Dialog oDialogChoicePlayer = new Dialog(PlayActivity.this);

				oDialogChoicePlayer.setContentView(R.layout.dialog_player);
				oDialogChoicePlayer.setTitle(R.string.title_dialog_player);
				oDialogChoicePlayer.show();
				*/

				final EditText editLoginDialog = (EditText) oDialogP
						.findViewById(R.id.editLogin);
				final EditText editPwdDialog = (EditText) oDialogP
						.findViewById(R.id.editPassword);

				Button btnValidPlayer = (Button) oDialogP
						.findViewById(R.id.btnValid);

				String login = oSettings.getString(Const.PREFERENCES_LOGIN, "");

				if (login != "") {
					editLoginDialog.setText(login);
					editPwdDialog.requestFocus();
				}

				btnValidPlayer.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						oPlayer2 = Utils.authentication(PlayActivity.this,
								editLoginDialog.getText().toString(),
								editPwdDialog.getText().toString());

						if (oPlayer2 != null) {
							if (thePlayer.getId() == oPlayer2.getId()) {
								Toast.makeText(PlayActivity.this,
										Const.ERREUR_PLAYER2, Toast.LENGTH_LONG)
										.show();
							} else {
								editPlayer2.setText(oPlayer2.getLogin()
										.toString());

								SharedPreferences.Editor oEditor = oSettings
										.edit();
								oEditor.putString(Const.PREFERENCES_LOGIN,
										oPlayer2.getLogin());
								oEditor.commit();

								oDialogPlayer.dismiss();
							}
						}
					}
				});
			}
		});

		/* CHOIX DU JEU */		
		editGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				final QustomDialogBuilder oDialogChoiceGame = new QustomDialogBuilder(v.getContext()).
						setTitle(R.string.title_dialog_game).
						setTitleColor(RED).
						setDividerColor(RED).
						setCustomView(R.layout.dialog_game, v.getContext()).
						setIcon(getResources().getDrawable(R.drawable.ic_launcher));
				
				final AlertDialog oDialogGame = oDialogChoiceGame.show();
				
				View oDialogG = oDialogChoiceGame.getViewById(R.layout.dialog_game);
				
				
				ListView listGames = (ListView) oDialogG
						.findViewById(R.id.listGames);

				Button btnValidGame = (Button) oDialogG
						.findViewById(R.id.btnValid);

				TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
						PlayActivity.this, Const.DATABASE, null, 1);

				SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

				Cursor oCursor = dataBase.query(GameContract.TABLE,
						GameContract.COLS, null, null, null, null, null);

				ArrayList<Game> aGames = new ArrayList<Game>();

				if (oCursor.moveToFirst()) {
					do {
						oGame.setId(oCursor.getInt(oCursor
								.getColumnIndex(GameContract.COL_ID)));
						oGame.setLibelle(oCursor.getString(oCursor
								.getColumnIndex(GameContract.COL_LIBELLE)));
						aGames.add(oGame);

					} while (oCursor.moveToNext());
				}

				MyGameAdapter oAdapter = new MyGameAdapter(PlayActivity.this,
						R.layout.row_game, aGames);

				listGames.setAdapter(oAdapter);

				listGames.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long arg3) {
						Game oGame = (Game) adapter.getItemAtPosition(position);

						editGame.setText(oGame.getLibelle());

						oDialogGame.dismiss();
					}
				});
				
				btnValidGame.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						oDialogGame.dismiss();
					}
				});
			}
		});
		

		/* DEMARRAGE DU JEU */
		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Bundle dataBundle = new Bundle();
				dataBundle.putSerializable(Const.BUNDLE_PLAYER,
						(Player) thePlayer);
				dataBundle.putSerializable(Const.BUNDLE_PLAYER2,
						(Player) oPlayer2);
				dataBundle.putSerializable(Const.BUNDLE_GAME, (Game) oGame);
				dataBundle.putInt(Const.BUNDLE_TIME,
						Integer.valueOf(radioBtn.getText().toString()));

				// Controle des champs vides
				if (editPlayer1.getText().length() < 1
						|| editPlayer2.getText().length() < 1
						|| editGame.getText().length() < 1) {
					Toast.makeText(PlayActivity.this, Const.ERREUR_FORMVIDE,
							Toast.LENGTH_LONG).show();
				} else {
					Intent intentOpenGame = new Intent(PlayActivity.this,
							GameActivity.class);

					intentOpenGame.putExtras(dataBundle);

					startActivity(intentOpenGame);
				}
			}
		});
	}

	private static class MyGameAdapter extends ArrayAdapter<Game> {

		private Context context;
		private int resource;
		private LayoutInflater inflater;

		public MyGameAdapter(Context context, int resource,
				java.util.List<Game> items) {
			super(context, resource, items);
			this.context = context;
			this.resource = resource;

			inflater = LayoutInflater.from(this.context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(this.resource, null);

			TextView lbGame = (TextView) view.findViewById(R.id.lbGame);

			Game oGame = this.getItem(position);

			lbGame.setText(oGame.getLibelle().toString());

			return view;
		}
	}
}
