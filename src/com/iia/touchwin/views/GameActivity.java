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

	Player thePlayer;
	Player Player2;
	TextView lbScoreP1;
	TextView lbScoreP2;
	ImageView imgColor;
	int nbRounds = 7;
	boolean play;
	boolean isTrue;

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

		lbScoreP1 = (TextView) findViewById(R.id.lbScoreP1);
		lbScoreP2 = (TextView) findViewById(R.id.lbScoreP2);

		btnP1.setText(thePlayer.getLogin());
		btnP2.setText(Player2.getLogin());

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

		// On éxecute le jeux dans un autre thread
		GameAsyncTask inGame = new GameAsyncTask();
		inGame.execute();

		
	}

	private class GameAsyncTask extends AsyncTask<Void, Boolean, Void> {

		protected void onProgressUpdate(Boolean isTrue) {
			super.onProgressUpdate(isTrue);

			play = true;
			
			if (isTrue.booleanValue()) {
				imgColor.setBackgroundResource(R.color.yellow);
			} else {
				imgColor.setBackgroundResource(R.color.red);
			}
		}

		protected Void doInBackground(Void... arg0) {
			int roundPlay = 0;

			do {
				roundPlay++;
				
				isTrue = GameReflex.randomFalse(1, 5);

				int timeRound = GameReflex.randomTime(3, 7);

				SystemClock.sleep(timeRound * 1000);
				
				publishProgress(isTrue);

			} while (nbRounds != roundPlay);

			return null;
		}

	}
}
