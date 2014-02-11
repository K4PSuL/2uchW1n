package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	long startTime = 0L;
	Handler customHandler = new Handler();
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		final Button btnP1 = (Button) findViewById(R.id.btnP1);
		final Button btnP2 = (Button) findViewById(R.id.btnP2);
		final ImageView imgColor = (ImageView) findViewById(R.id.imgColor);

		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);
		final Player Player2 = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER2);
		final String game = getIntent().getExtras().getString(Const.BUNDLE_GAME);
        final int nbRounds = getIntent().getExtras().getInt(Const.BUNDLE_TIME);
		
        // Create custom dialog object
        final Dialog dialog = new Dialog(GameActivity.this);
        
		btnP1.setText(thePlayer.getLogin());
		btnP2.setText(Player2.getLogin());
	
		startTime = SystemClock.uptimeMillis();
		customHandler.postDelayed(updateTimerThread, 0);
		
		
		dialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);
			}
		});
		
		dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				startTime = SystemClock.uptimeMillis();
        		customHandler.postDelayed(updateTimerThread, 0);
			}
		});
		
		imgColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Include dialog.xml file
                dialog.setContentView(R.layout.dialog_game);
                // Set dialog title
                dialog.setTitle(R.string.title_dialog_stop);

                dialog.show();
                 
                Button btnStop = (Button) dialog.findViewById(R.id.btnValid);
                // if button is clicked, close the custom dialog
                btnStop.setOnClickListener(new View.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// Close dialog
                        dialog.dismiss();
                    }
        			
                });
                
			}
		});
		
	}
	
	private Runnable updateTimerThread = new Runnable() {

		public void run() {

			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			int milliseconds = (int) (updatedTime % 1000);
			
			final TextView lbTimer = (TextView) findViewById(R.id.lbTimer);
			lbTimer.setText("" + mins + ":"
					+ String.format("%02d", secs) + ":"
					+ String.format("%03d", milliseconds));
			customHandler.postDelayed(this, 0);
		}

	};
	
}
