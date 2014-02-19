package com.iia.touchwin.ui;

import java.util.ArrayList;
import com.iia.touchwin.R;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.request.GameRequest;
import com.iia.touchwin.request.PlayerRequest;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;
import com.iia.touchwin.utils.QustomDialogBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class PlayActivity extends Activity implements SensorEventListener {

	private Player oPlayer2;
	private Game oGameSelect;
	private SensorManager oSensorManager;
	private Sensor oAccelerometer;
	private EditText editGame;
	private ArrayList<Game> aGames;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_play);

		final EditText editPlayer1 = (EditText) findViewById(R.id.editPlayer1);
		final EditText editPlayer2 = (EditText) findViewById(R.id.editPlayer2);
		editGame = (EditText) findViewById(R.id.editGame);
		final RadioGroup radioGroupTime = (RadioGroup) findViewById(R.id.radioGroup);
		final Button btnGo = (Button) findViewById(R.id.btnGo);

		final Bundle dataBundle = new Bundle();

		final Player oPlayer1 = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		final SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER2, Context.MODE_PRIVATE);

		editPlayer1.setText(oPlayer1.getLogin());
		
		aGames = GameRequest.getAllGame(PlayActivity.this);

		// Déclaration des sensors
		oSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		oAccelerometer = oSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		Toast.makeText(getApplicationContext(), Const.TOAST_PLAY, 
				   Toast.LENGTH_LONG).show();
		
		/* CHOIX DU JOUEUR 2 */
		editPlayer2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final QustomDialogBuilder oDialogChoicePlayer = new QustomDialogBuilder(
						v.getContext())
						.setTitle(R.string.title_dialog_player)
						.setTitleColor(Const.COLOR_RED)
						.setDividerColor(R.color.red)
						.setCustomView(R.layout.dialog_player, v.getContext())
						.setIcon(
								getResources().getDrawable(
										R.drawable.ic_launcher));

				final AlertDialog oDialogPlayer = oDialogChoicePlayer.show();

				View oDialogP = oDialogChoicePlayer
						.getViewById(R.layout.dialog_player);

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

						oPlayer2 = PlayerRequest.authentication(PlayActivity.this,
								editLoginDialog, editPwdDialog);

						if (oPlayer2 != null) {
							if (oPlayer1.getId() == oPlayer2.getId()) {

								editLoginDialog.setError(Const.ERREUR_PLAYER2);

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

				final QustomDialogBuilder oDialogChoiceGame = new QustomDialogBuilder(
						v.getContext())
						.setTitle(R.string.title_dialog_game)
						.setTitleColor(Const.COLOR_RED)
						.setDividerColor(R.color.red)
						.setCustomView(R.layout.dialog_game, v.getContext())
						.setIcon(
								getResources().getDrawable(
										R.drawable.ic_launcher));

				final AlertDialog oDialogGame = oDialogChoiceGame.show();

				View oDialogG = oDialogChoiceGame
						.getViewById(R.layout.dialog_game);

				ListView listGames = (ListView) oDialogG
						.findViewById(R.id.listGames);

				Button btnValidGame = (Button) oDialogG
						.findViewById(R.id.btnValid);

				MyGameAdapter oAdapter = new MyGameAdapter(PlayActivity.this,
						R.layout.row_game, aGames);

				listGames.setAdapter(oAdapter);

				listGames.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapter, View v,
							int position, long arg3) {

						oGameSelect = (Game) adapter
								.getItemAtPosition(position);

						editGame.setText(oGameSelect.getLibelle());

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

				/* CHOIX DU TEMPS / NOMBRE DE ROUNDS */
				int selectedId = radioGroupTime.getCheckedRadioButtonId();
				final RadioButton radioBtn = (RadioButton) findViewById(selectedId);

				dataBundle.putSerializable(Const.BUNDLE_PLAYER,
						(Player) oPlayer1);
				dataBundle.putSerializable(Const.BUNDLE_PLAYER2,
						(Player) oPlayer2);
				dataBundle.putSerializable(Const.BUNDLE_GAME,
						(Game) oGameSelect);
				dataBundle.putInt(Const.BUNDLE_TIME,
						Integer.valueOf(radioBtn.getText().toString()));

				// Controle des champs vides
				if (editPlayer1.getText().length() < 1
						|| editPlayer2.getText().length() < 1
						|| editGame.getText().length() < 1) {

					Toast.makeText(PlayActivity.this, Const.ERREUR_FORMVIDE,
							Toast.LENGTH_LONG).show();

				} else {

					Intent intentOpenGame = null;

					if (oGameSelect.getId() == Const.GAME_COLOR) {
						intentOpenGame = new Intent(PlayActivity.this,
								CouleurActivity.class);

					} else if (oGameSelect.getId() == Const.GAME_CALCUL) {
						intentOpenGame = new Intent(PlayActivity.this,
								CalculActivity.class);
					}

					intentOpenGame.putExtras(dataBundle);
					startActivity(intentOpenGame);
				}
			}
		});
	}

	@Override
	protected void onPause() {
		oSensorManager.unregisterListener(this, oAccelerometer);
		super.onPause();
	}

	@Override
	protected void onResume() {
		oSensorManager.registerListener(this, oAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
		super.onResume();
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

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		float x = 0, y = 0, z = 0;
		
		if (arg0.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			x = arg0.values[0];
			y = arg0.values[1];
			z = arg0.values[2];
		}
		
		// Si un mouvement a était détécté, on choisi un jeu aléatoirement
		if (x > 12 || y > 12 || z > 12) {
			
			if (aGames != null) {
				int random;
				random = Utils.randomNumber(0, aGames.size());

				oGameSelect = (Game) aGames.get(random);

				editGame.setText(oGameSelect.getLibelle());
			}
		}
	}
}
