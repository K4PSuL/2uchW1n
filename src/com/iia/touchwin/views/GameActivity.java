package com.iia.touchwin.views;

import java.util.Random;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.GameReflex;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
//import com.iia.touchwin.utils.GameAsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources.Theme;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {

	private Player thePlayer;
	private Player Player2;
	private TextView lbScoreP1;
	private TextView lbScoreP2;
	private ImageView imgColor;
	private int nbRounds;
	private boolean play;
	private boolean isTrue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);

		final Button btnP1 = (Button) findViewById(R.id.btnP1);
		final Button btnP2 = (Button) findViewById(R.id.btnP2);

		imgColor = (ImageView) findViewById(R.id.imgColor);

		thePlayer = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER);

		Player2 = (Player) getIntent().getExtras().getSerializable(
				Const.BUNDLE_PLAYER2);
		
		nbRounds = getIntent().getExtras().getInt(Const.BUNDLE_TIME);

		lbScoreP1 = (TextView) findViewById(R.id.lbScoreP1);
		lbScoreP2 = (TextView) findViewById(R.id.lbScoreP2);

		btnP1.setText(thePlayer.getLogin());
		btnP2.setText(Player2.getLogin());

		// On éxecute le jeux dans un autre thread
		GameAsyncTask inGame = new GameAsyncTask();
		inGame.execute();

		btnP1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (play) {
					play = false;

					if (isTrue) {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));
					} else {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));
					}

					imgColor.setBackgroundResource(R.color.gray);
				}
			}
		});

		btnP2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (play) {

					play = false;

					if (isTrue) {
						lbScoreP2.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP2.getText()) + 1));
					} else {
						lbScoreP1.setText(String.valueOf(Integer
								.valueOf((String) lbScoreP1.getText()) + 1));
					}

					imgColor.setBackgroundResource(R.color.gray);
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
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			int roundPlay = 0;

			do {
				roundPlay++;

				SystemClock.sleep(GameReflex.randomTime(2, 10) * 1000);

				publishProgress(GameReflex.randomTime(1, 4));

			} while (nbRounds != roundPlay);

			return null;
		}

	}
}
