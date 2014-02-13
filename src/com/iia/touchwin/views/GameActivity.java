package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

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
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {

	private Player thePlayer;
	private Player oPlayer2;
	private TextView lbScoreP1;
	private TextView lbScoreP2;
	private ImageView imgColor;
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		lbChrono = (TextView) findViewById(R.id.txtChrono);
		btnP1 = (Button) findViewById(R.id.btnP1);
		btnP2 = (Button) findViewById(R.id.btnP2);
		imgColor = (ImageView) findViewById(R.id.imgColor);
		lbScoreP1 = (TextView) findViewById(R.id.lbScoreP1);
		lbScoreP2 = (TextView) findViewById(R.id.lbScoreP2);
		
		animateChrono = AnimationUtils.loadAnimation(GameActivity.this,
				R.anim.chrono);
		
		oGame = (Game) getIntent().getExtras().getSerializable(
				Const.BUNDLE_GAME);
		
		nbRounds = getIntent().getExtras().getInt(Const.BUNDLE_TIME);
		
		thePlayer = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER);

		oPlayer2 = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER2);

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

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.win);
					} else {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));

						btnP1.setBackgroundColor(R.color.red);

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.loose);
					}

					roundPlay++;
					
					imgColor.setBackground(null);
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

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.win);
					} else {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));

						btnP2.setBackgroundColor(R.color.red);

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.loose);
					}
					
					roundPlay++;
					
					imgColor.setBackground(null);
				}
			}

		});
	}

	private class GameAsyncTask extends AsyncTask<Void, Integer, Void> {

		protected void onProgressUpdate(Integer... randomInteger) {
			super.onProgressUpdate(randomInteger);

			if (randomInteger[0] != 1) {
				isTrue = true;
				imgColor.setBackgroundResource(R.color.yellow);
			} else {
				isTrue = false;
				imgColor.setBackgroundResource(R.color.red);
			}

			play = true;

			if (nbRounds == roundPlay) {
				this.cancel(true);
			}
		}

		protected Void doInBackground(Void... arg0) {
			do {
				SystemClock.sleep(Utils.randomTime(2, 10) * 1000);

				publishProgress(Utils.randomTime(1, 3));

			} while (true != false);
		}

		@Override
		protected void onCancelled() {
			final Dialog oDialogEndGame = new Dialog(GameActivity.this);

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

			Utils.playSound(GameActivity.this, GameActivity.this, R.raw.end);

			Utils.saveScore(thePlayer, oPlayer2, scoreP1, scoreP2, oGame,
					GameActivity.this);

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
				Utils.playSound(GameActivity.this, GameActivity.this,
						R.raw.five);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 4:
				Utils.playSound(GameActivity.this, GameActivity.this,
						R.raw.four);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 3:
				Utils.playSound(GameActivity.this, GameActivity.this,
						R.raw.three);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 2:
				Utils.playSound(GameActivity.this, GameActivity.this, R.raw.two);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 1:
				Utils.playSound(GameActivity.this, GameActivity.this, R.raw.one);
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
			// On éxecute le jeux dans un autre thread
			GameAsyncTask inGame = new GameAsyncTask();
			inGame.execute();

		}

	}

}
