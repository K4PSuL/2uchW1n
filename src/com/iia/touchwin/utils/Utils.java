package com.iia.touchwin.utils;

import java.util.Random;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Game;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.request.ResultRequest;

public abstract class Utils {

	/**
	 * Joue un son
	 * 
	 * @param oContext
	 * @param oActivity
	 * @param resource
	 */
	public static void playSound(Activity oActivity, int resource) {

		SharedPreferences oSettings = oActivity.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		if (oSettings.getBoolean(Const.PREFERENCES_SOUND, true)) {
			MediaPlayer oMediaPlayer = (MediaPlayer) MediaPlayer.create(
					oActivity.getApplicationContext(), resource);
			oMediaPlayer.start();
		}
	}

	/**
	 * Retourne un nombre aléatoire entre les deux valeurs passé en paramètres
	 * (max exclu)
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		Random oRandom = new Random();

		int timeRound = oRandom.nextInt(max - min) + min;

		return timeRound;
	}

	/**
	 * Affiche une dialogue en fin de partit avec le résultat final + savegarde
	 * en BDD le score
	 * 
	 * @param oActivity
	 * @param oPlayer1
	 * @param oPlayer2
	 * @param scoreP1
	 * @param scoreP2
	 * @param oGame
	 */
	public static void dialogEndGame(final Activity oActivity, Player oPlayer1,
			Player oPlayer2, int scoreP1, int scoreP2, Game oGame) {

		final Activity oActivity2 = oActivity;

		final Dialog oDialogEndGame = new Dialog(oActivity2);

		final Player oPlayerWinner;

		if (scoreP1 > scoreP2) {
			oPlayerWinner = oPlayer1;
		} else {
			oPlayerWinner = oPlayer2;
		}

		oDialogEndGame.setContentView(R.layout.dialog_game_end);
		oDialogEndGame.setTitle(oPlayerWinner.getLogin() + Const.WINNER);

		final TextView lbPseudo = (TextView) oDialogEndGame
				.findViewById(R.id.txtPseudo);
		final TextView lbScore = (TextView) oDialogEndGame
				.findViewById(R.id.txtScore);
		final Button btnExitEndGame = (Button) oDialogEndGame
				.findViewById(R.id.btnExitEndGame);

		lbPseudo.setText(oPlayerWinner.getLogin());
		lbScore.setText(String.valueOf(scoreP1) + " - "
				+ String.valueOf(scoreP2));

		oDialogEndGame.show();

		playSound(oActivity2, R.raw.end);

		ResultRequest.saveScore(oPlayer1, oPlayer2, scoreP1, scoreP2, oGame, oActivity);

		btnExitEndGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				oDialogEndGame.dismiss();
				oActivity2.finish();
			}
		});
	}
}
