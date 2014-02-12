package com.iia.touchwin.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.GameReflex;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

public class CalculActivity extends Activity {
	private Player thePlayer;
	private Player oPlayer2;
	private TextView lbScoreP1;
	private TextView lbScoreP2;
	private int calcul;
	private TextView lbCalculP1;
	private TextView lbCalculP2;
	private int result;
	private int nbRounds;
	private boolean play;
	private boolean isTrue;
	private Player oPlayerWinner;
	private Button btnP1;
	private Button btnP2;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		btnP1 = (Button) findViewById(R.id.btnP1);
		btnP2 = (Button) findViewById(R.id.btnP2);

		//imgColor = (ImageView) findViewById(R.id.imgColor);
		
		lbCalculP1 = (TextView) findViewById(R.id.lbCalculP1);
		lbCalculP2 = (TextView) findViewById(R.id.lbCalculP2);
		
		thePlayer = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER);

		oPlayer2 = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER2);
		
		nbRounds = getIntent().getExtras().getInt(Const.BUNDLE_TIME);
		
		calcul = 0;
		result = 0;
		isTrue = false;
		
		lbScoreP1 = (TextView) findViewById(R.id.lbScoreP1);
		lbScoreP2 = (TextView) findViewById(R.id.lbScoreP2);

		btnP1.setText(thePlayer.getLogin());
		btnP2.setText(oPlayer2.getLogin());
		
		Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.five);
		Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.four);
		Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.three);
		Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.two);
		Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.one);

		// On éxecute le jeux dans un autre thread
		GameAsyncTask inGame = new GameAsyncTask();
		inGame.execute();

		btnP1.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (play) {
					play = false;

					if (isTrue) {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));
						btnP1.setBackgroundColor(R.color.green);
						Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.win);
					} else {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));
						btnP1.setBackgroundColor(R.color.red);
						Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.loose);
					}

					//imgColor.setBackgroundResource(R.color.gray);
				}
			}
		});

		btnP2.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (play) {
					play = false;

					if (isTrue) {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));
						btnP2.setBackgroundColor(R.color.green);
						Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.win);
					} else {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));
						btnP2.setBackgroundColor(R.color.red);
						Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.loose);
					}

					//imgColor.setBackgroundResource(R.color.gray);
				}
			}
		});
	}

	private void setNewCalcul() {
		int nb1 = GameReflex.randomTime(0, 100); // premier nombre
		int nb2 = GameReflex.randomTime(0, 100); // deuxième nombre
		int operator = GameReflex.randomTime(1, 4); // opérateur
		int cpt; // bon ou mauvais calcul
		String lbCalcul = "";
		
		switch (operator) {
		case 1:
			calcul = nb1 + nb2;
			cpt = GameReflex.randomTime(1, 3);
			result = calcul;
			
			if (cpt == 1) {
				isTrue = true;
			} else {
				do {
					result = GameReflex.randomTime(calcul-5, calcul+5);
				} while (result == calcul);
				isTrue = false;
			}
			
			lbCalcul = nb1 + " + " + nb2 + " = " + result;
			break;
		case 2:
			calcul = nb1 - nb2;
			cpt = GameReflex.randomTime(1, 3);
			result = calcul;
			
			if (cpt == 1) {
				isTrue = true;
			} else {
				do {
					result = GameReflex.randomTime(calcul-5, calcul+5);
				} while (result == calcul);
				isTrue = false;
			}
			
			lbCalcul = nb1 + " - " + nb2 + " = " + result;
			break;
		case 3:
			calcul = nb1 * nb2;
			cpt = GameReflex.randomTime(1, 3);
			result = calcul;
			
			if (cpt == 1) {
				isTrue = true;
			} else {
				do {
					result = GameReflex.randomTime(calcul-5, calcul+5);
				} while (result == calcul);
				isTrue = false;
			}
			
			lbCalcul = nb1 + " x " + nb2 + " = " + result;
			break;
		default:
			break;
		}
		
		lbCalculP1.setVisibility(View.VISIBLE);
		lbCalculP2.setVisibility(View.VISIBLE);
		lbCalculP1.setText(lbCalcul);
		lbCalculP2.setText(lbCalcul);
	}
	
	private class GameAsyncTask extends AsyncTask<Void, Integer, Void> {
		protected void onProgressUpdate(Integer... randomInteger) {
			super.onProgressUpdate(randomInteger);

			btnP1.setBackgroundResource(R.color.gray);
			btnP2.setBackgroundResource(R.color.gray);

			lbCalculP1.setText(" ");
			lbCalculP2.setText(" ");
			
			lbCalculP1.setVisibility(View.INVISIBLE);
			lbCalculP2.setVisibility(View.INVISIBLE);
			
			setNewCalcul();
			
			play = true;
		}

		protected Void doInBackground(Void... arg0) {
			int roundPlay = 0;
			
			do {
				roundPlay++;

				SystemClock.sleep(GameReflex.randomTime(2, 10) * 1000);

				publishProgress(GameReflex.randomTime(1, 4));

			} while (nbRounds != roundPlay);

			return null;
		}
		
		protected void onPostExecute(Void result) {
			final Dialog oDialogEndGame = new Dialog(CalculActivity.this);
		
			int scoreP1 = Integer.valueOf((String) lbScoreP1.getText());
			int scoreP2 = Integer.valueOf((String) lbScoreP2.getText());
			
			if (scoreP1 > scoreP2) {
				oPlayerWinner = thePlayer;
			} else {
				oPlayerWinner = oPlayer2;
			}
			
			oDialogEndGame.setContentView(R.layout.dialog_game_end);
			oDialogEndGame.setTitle(oPlayerWinner.getLogin() + " à gagné !");
			
			TextView txtPseudo = (TextView) oDialogEndGame
					.findViewById(R.id.txtPseudo);
			TextView txtScore = (TextView) oDialogEndGame
					.findViewById(R.id.txtScore);
			Button btnExitEndGame = (Button) oDialogEndGame
					.findViewById(R.id.btnExitEndGame);
			
			txtPseudo.setText(oPlayerWinner.getLogin());
			txtScore.setText(lbScoreP1.getText() + " - " + lbScoreP2.getText());

			oDialogEndGame.show();

			Utils.playSound(CalculActivity.this, CalculActivity.this, R.raw.end);
			
			btnExitEndGame.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					oDialogEndGame.dismiss();
				}
			});
			
		}
	}
}
