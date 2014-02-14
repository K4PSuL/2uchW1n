package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {

	private Button btnP1;
	private Button btnP2;
	private TextView lbScoreP1;
	private TextView lbScoreP2;
	private TextView lbChrono;
	private ImageView imgColor;
	private ImageView imgClickP1;
	private ImageView imgClickP2;
	private Player thePlayer;
	private Player oPlayer2;
	private Player oPlayerWinner;
	private Animation animateChrono;
	private int nbRounds;
	private boolean play;
	private boolean isTrue;
	private Game oGame;

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
		imgClickP1 = (ImageView) findViewById(R.id.imgClickP1);
		imgClickP2 = (ImageView) findViewById(R.id.imgClickP2);

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
		ChronoAsyncTask chrono = new ChronoAsyncTask(nbRounds);
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

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.win);

						imgClickP1.setVisibility(View.VISIBLE);
						imgClickP1.setImageResource(R.drawable.ok);
						imgClickP2.setVisibility(View.INVISIBLE);

					} else {

						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.loose);

						imgClickP1.setVisibility(View.VISIBLE);
						imgClickP1.setImageResource(R.drawable.cancel);
						imgClickP2.setVisibility(View.INVISIBLE);
					}
					
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

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.win);

						imgClickP2.setVisibility(View.VISIBLE);
						imgClickP2.setImageResource(R.drawable.ok);
						imgClickP1.setVisibility(View.INVISIBLE);

					} else {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));

						Utils.playSound(GameActivity.this, GameActivity.this,
								R.raw.loose);

						imgClickP2.setVisibility(View.VISIBLE);
						imgClickP2.setImageResource(R.drawable.cancel);
						imgClickP1.setVisibility(View.INVISIBLE);
					}

					imgColor.setBackground(null);
				}
			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private class GameAsyncTask extends AsyncTask<Void, Integer, Void> {

		private int nbRounds;
		private int roundsPlay;
		private boolean goNext;

		protected GameAsyncTask(int nbRounds) {
			this.nbRounds = nbRounds;
			this.roundsPlay = 0;
			this.goNext = true;
		}

		@Override
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

			roundsPlay++;
		}

		protected Void doInBackground(Void... arg0) {
			do {
				
				SystemClock.sleep(Utils.randomTime(2, 10) * 1000);

				publishProgress(Utils.randomTime(1, 3));
				
				SystemClock.sleep(2000);

			} while (this.roundsPlay != nbRounds);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

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

			SystemClock.sleep(2000);
			
			btnExitEndGame.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					oDialogEndGame.dismiss();
					GameActivity.this.finish();
				}
			});
		}
	}

	private class ChronoAsyncTask extends AsyncTask<Void, Integer, Void> {

		private int nbRounds;

		protected ChronoAsyncTask(int nbRounds) {
			this.nbRounds = nbRounds;
		}

		@Override
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

		@Override
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
			SystemClock.sleep(1000);
			publishProgress(-1);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			// On éxecute le jeux dans un autre thread
			GameAsyncTask inGame = new GameAsyncTask(nbRounds);
			inGame.execute();
		}

	}
}
