package com.iia.touchwin.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

public class CalculActivity extends Activity {
	
	private Player thePlayer;
	private Player oPlayer2;
	private TextView lbScoreP1;
	private TextView lbScoreP2;
	private Player oPlayerWinner;
	private Button btnP1;
	private Button btnP2;
	private TextView lbChrono;
	private Animation animateChrono;
	private int nbRounds;
	private boolean play;
	private boolean isTrue;
	private Game oGame;
	private int roundPlay = 0;
	private int calcul;
	private TextView lbCalculP1;
	private TextView lbCalculP2;
	private int result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		lbChrono = (TextView) findViewById(R.id.txtChrono);
		btnP1 = (Button) findViewById(R.id.btnP1);
		btnP2 = (Button) findViewById(R.id.btnP2);
		lbScoreP1 = (TextView) findViewById(R.id.lbScoreP1);
		lbScoreP2 = (TextView) findViewById(R.id.lbScoreP2);
		lbCalculP1 = (TextView) findViewById(R.id.lbCalculP1);
		lbCalculP2 = (TextView) findViewById(R.id.lbCalculP2);
		
		animateChrono = AnimationUtils.loadAnimation(CalculActivity.this,
				R.anim.chrono);
		
		oGame = (Game) getIntent().getExtras().getSerializable(
				Const.BUNDLE_GAME);
		
		nbRounds = getIntent().getExtras().getInt(Const.BUNDLE_TIME);
		
		thePlayer = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER);

		oPlayer2 = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER2);

		calcul = 0;
		result = 0;
		isTrue = false;
		
		btnP1.setText(thePlayer.getLogin());
		btnP2.setText(oPlayer2.getLogin());

		// On démarre le chronomètre
		ChronoAsyncTask chrono = new ChronoAsyncTask();
		chrono.execute();

		btnP1.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (play) {
					play = false;

					// Si il fallait cliquer
					if (isTrue) {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));

						btnP1.setBackgroundColor(R.color.green);

						Utils.playSound(CalculActivity.this,
								R.raw.win);
					} else {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));

						btnP1.setBackgroundColor(R.color.red);

						Utils.playSound(CalculActivity.this,
								R.raw.loose);
					}

					roundPlay++;
					
					//imgColor.setBackground(null);
				}
			}
		});

		btnP2.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (play) {
					play = false;

					// Si il fallait cliquer
					if (isTrue) {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));

						btnP2.setBackgroundColor(R.color.green);

						Utils.playSound(CalculActivity.this,
								R.raw.win);
					} else {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));

						btnP2.setBackgroundColor(R.color.red);

						Utils.playSound(CalculActivity.this,
								R.raw.loose);
					}
					
					roundPlay++;
					
					//imgColor.setBackground(null);
				}
			}

		});
	}
	
	private void calcul(int min, int max) {
		int cpt = Utils.randomNumber(1, 3); // bon ou mauvais calcul
		result = calcul;
		
		if (cpt == 1) {
			isTrue = true;
		} else {
			do {
				result = Utils.randomNumber(calcul-5, calcul+5);
			} while (result == calcul);
			isTrue = false;
		}
	}
	
	private void setNewCalcul() {
		int nb1 = Utils.randomNumber(0, 26); // premier nombre
		int nb2 = Utils.randomNumber(0, 26); // deuxième nombre
		int operator = Utils.randomNumber(1, 4); // opérateur
		String lbCalcul = ""; // libellé du calcul à afficher
		
		switch (operator) {
		case 1:
			calcul = nb1 + nb2;
			calcul(0,26);			
			lbCalcul = nb1 + " + " + nb2 + " = " + result;
			break;
		case 2:
			calcul = nb1 - nb2;
			calcul(0,26);
			lbCalcul = nb1 + " - " + nb2 + " = " + result;
			break;
		case 3:
			calcul = nb1 * nb2;
			calcul(0,11);
			lbCalcul = nb1 + " x " + nb2 + " = " + result;
			break;
		default:
			break;
		}
		
		lbCalculP1.setText(lbCalcul);
		lbCalculP2.setText(lbCalcul);
	}
	
	private class GameAsyncTask extends AsyncTask<Void, Integer, Void> {

		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			lbCalculP1.setText(null);
			lbCalculP2.setText(null);
			
			setNewCalcul();
			
			play = true;

			if (nbRounds == roundPlay - 1) {
				this.cancel(true);
			}
		}

		protected Void doInBackground(Void... arg0) {
			do {
				SystemClock.sleep(5000);

				publishProgress(Utils.randomNumber(1, 3));

			} while (true != false);
		}

		@Override
		protected void onCancelled() {
			final Dialog oDialogEndGame = new Dialog(CalculActivity.this);

			int scoreP1 = Integer.valueOf((String) lbScoreP1.getText());
			int scoreP2 = Integer.valueOf((String) lbScoreP2.getText());

			if (scoreP1 > scoreP2) {
				oPlayerWinner = thePlayer;
			} else {
				oPlayerWinner = oPlayer2;
			}

			oDialogEndGame.setContentView(R.layout.dialog_game_end);
			oDialogEndGame.setTitle(oPlayerWinner.getLogin() + Const.WINNER);

			TextView lbPseudo = (TextView) oDialogEndGame
					.findViewById(R.id.txtPseudo);
			TextView lbScore = (TextView) oDialogEndGame
					.findViewById(R.id.txtScore);
			Button btnExitEndGame = (Button) oDialogEndGame
					.findViewById(R.id.btnExitEndGame);

			lbPseudo.setText(oPlayerWinner.getLogin());
			lbScore.setText(lbScoreP1.getText() + " - " + lbScoreP2.getText());

			oDialogEndGame.show();

			Utils.playSound(CalculActivity.this, R.raw.end);

			Utils.saveScore(thePlayer, oPlayer2, scoreP1, scoreP2, oGame,
					CalculActivity.this);

			btnExitEndGame.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					oDialogEndGame.dismiss();
				}
			});
		}
	}

	private class ChronoAsyncTask extends AsyncTask<Void, Integer, Void> {
		
		protected void onProgressUpdate(Integer... number) {
			super.onProgressUpdate(number);

			switch (number[0]) {
			case 5:
				Utils.playSound(CalculActivity.this,
						R.raw.five);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 4:
				Utils.playSound(CalculActivity.this,
						R.raw.four);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 3:
				Utils.playSound(CalculActivity.this,
						R.raw.three);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 2:
				Utils.playSound(CalculActivity.this, 
						R.raw.two);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 1:
				Utils.playSound(CalculActivity.this, 
						R.raw.one);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 0:
				lbChrono.setText("Go !");
				break;
			default:
				lbChrono.setText(null);
				break;
			}

			lbChrono.startAnimation(animateChrono);

		}

		protected Void doInBackground(Void... arg0) {

			publishProgress(5);
			SystemClock.sleep(1000);
			publishProgress(4);
			SystemClock.sleep(1000);
			publishProgress(3);
			SystemClock.sleep(1000);
			publishProgress(2);
			SystemClock.sleep(1000);
			publishProgress(1);
			SystemClock.sleep(1000);
			publishProgress(0);
			SystemClock.sleep(500);
			publishProgress(-1);

			return null;
		}

		protected void onPostExecute(Void result) {
			// On éxecute le jeu dans un autre thread
			GameAsyncTask inGame = new GameAsyncTask();
			inGame.execute();

		}

	}
}
