package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity implements View.OnClickListener {

	private ImageView imgColor;
	private ImageView imgClickP1;
	private ImageView imgClickP2;
	private TextView lbScoreP1;
	private TextView lbScoreP2;
	private TextView lbChrono;
	private TextView lbMoreScoreP1;
	private TextView lbMoreScoreP2;
	private Button btnP1;
	private Button btnP2;
	private Animation animateMoreScoreP1;
	private Animation animateMoreScoreP2;
	private Player oPlayer1;
	private Player oPlayer2;
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

		lbMoreScoreP1 = (TextView) findViewById(R.id.lbMoreP1);
		lbMoreScoreP2 = (TextView) findViewById(R.id.lbMoreP2);

		animateMoreScoreP1 = AnimationUtils.loadAnimation(GameActivity.this,
				R.anim.more_p1);

		animateMoreScoreP2 = AnimationUtils.loadAnimation(GameActivity.this,
				R.anim.more_p2);

		oGame = (Game) getIntent().getExtras().getSerializable(
				Const.BUNDLE_GAME);

		nbRounds = getIntent().getExtras().getInt(Const.BUNDLE_TIME);

		oPlayer1 = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER);

		oPlayer2 = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER2);

		btnP1.setText(oPlayer1.getLogin());
		btnP2.setText(oPlayer2.getLogin());

		// On démarre le chronomètre
		ChronoAsyncTask chrono = new ChronoAsyncTask(nbRounds);
		chrono.execute();

		btnP1.setOnClickListener((OnClickListener) this);
		btnP2.setOnClickListener((OnClickListener) this);
	}

	@Override
	public void onClick(View v) {
		if (play) {
			play = false;

			// Le joueur 1 joue
			if (v.getId() == R.id.btnP1) {
				// Et a bon
				if (isTrue) {
					clickBtn(animateMoreScoreP1, lbMoreScoreP1, lbScoreP1, imgClickP1, imgClickP2,
							R.raw.win, R.drawable.ok);
				} else {
					clickBtn(animateMoreScoreP2, lbMoreScoreP2, lbScoreP2, imgClickP1, imgClickP2,
							R.raw.loose, R.drawable.cancel);
				}
			} else {
				if (isTrue) {
					clickBtn(animateMoreScoreP2,lbMoreScoreP2, lbScoreP2, imgClickP2, imgClickP1,
							R.raw.win, R.drawable.ok);
				} else {
					clickBtn(animateMoreScoreP1,lbMoreScoreP1, lbScoreP1, imgClickP2, imgClickP1,
							R.raw.loose, R.drawable.cancel);
				}
			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * Incrémente le score du joueur ayant remporté le round
	 * 
	 * @param lbMoreWinner
	 * @param lbScoreWinner
	 * @param imgClickSender
	 * @param imgClickOther
	 * @param resSound
	 * @param resImg
	 */
	private void clickBtn(final Animation oAnimation, final TextView lbMoreWinner, TextView lbScoreWinner,
			ImageView imgClickSender, ImageView imgClickOther, int resSound,
			int resImg) {

		oAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				lbMoreWinner.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// On joue un son d'entrée
				lbMoreWinner.setVisibility(View.INVISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lbMoreWinner.startAnimation(animateMoreScoreP1);

		lbScoreWinner.setText(String.valueOf(Integer
				.valueOf((String) lbScoreWinner.getText()) + 1));

		Utils.playSound(GameActivity.this, resSound);

		imgClickSender.setVisibility(View.VISIBLE);
		imgClickSender.setImageResource(resImg);
		imgClickOther.setVisibility(View.INVISIBLE);

		imgColor.setVisibility(View.INVISIBLE);
	}

	/**
	 * AsyncTask pour la mécanique du jeu
	 * 
	 * @author Tommy
	 * 
	 */
	private class GameAsyncTask extends AsyncTask<Void, Integer, Void> {

		private int nbRounds;
		private int scoreP1;
		private int scoreP2;

		protected GameAsyncTask(int nbRounds) {
			this.nbRounds = nbRounds;
			this.scoreP1 = 0;
			this.scoreP2 = 0;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		protected void onProgressUpdate(Integer... randomInteger) {
			super.onProgressUpdate(randomInteger);

			// On récupère le score de chaque joueur
			this.scoreP1 = Integer.valueOf((String) lbScoreP1.getText());
			this.scoreP2 = Integer.valueOf((String) lbScoreP2.getText());

			switch (randomInteger[0]) {

			// Si = 1, la réponse est vrai
			case 1:
				isTrue = true;
				imgColor.setBackgroundResource(R.color.yellow);
				imgColor.setVisibility(View.VISIBLE);
				play = true;
				break;

			// Si = 2, la réponse est false
			case 2:
				isTrue = false;
				imgColor.setBackgroundResource(R.color.red);
				imgColor.setVisibility(View.VISIBLE);
				play = true;
				break;

			// Si = 3, On retire la couleur pour le round suivant
			case 3:
				imgColor.setVisibility(View.INVISIBLE);
				play = false;
				break;

			default:
				break;
			}
		}

		protected Void doInBackground(Void... arg0) {

			// Si le nombre de round passé est atteint, on quitte le thread
			while (scoreP1 + scoreP2 != nbRounds) {

				// On génére un temp d'attente aléatoire
				SystemClock.sleep(Utils.randomNumber(2, 10) * 1000);

				// On génére un nombre aléatoire pour s'avoir si la réponse est
				// vrai ou fausse
				publishProgress(Utils.randomNumber(1, 3));

				// On laisse 2sec pour répondre
				SystemClock.sleep(3000);

				// On retire la couleur
				publishProgress(3);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Utils.dialogEndGame(GameActivity.this, oPlayer1, oPlayer2,
					this.scoreP1, this.scoreP2, oGame);

		}
	}

	/**
	 * AsyncTask pour le compte à rebour de départ
	 * 
	 * @author Tommy
	 * 
	 */
	private class ChronoAsyncTask extends AsyncTask<Void, Integer, Void> {

		private int nbRounds;

		protected ChronoAsyncTask(int nbRounds) {
			this.nbRounds = nbRounds;
		}

		@Override
		protected void onProgressUpdate(Integer... number) {
			super.onProgressUpdate(number);

			Animation animateChrono = AnimationUtils.loadAnimation(
					GameActivity.this, R.anim.chrono);

			switch (number[0]) {
			case 5:
				Utils.playSound(GameActivity.this, R.raw.five);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 4:
				Utils.playSound(GameActivity.this, R.raw.four);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 3:
				Utils.playSound(GameActivity.this, R.raw.three);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 2:
				Utils.playSound(GameActivity.this, R.raw.two);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 1:
				Utils.playSound(GameActivity.this, R.raw.one);
				lbChrono.setText(String.valueOf(number[0]));
				break;
			case 0:
				lbChrono.setText(Const.CHRONO_GO);
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
