package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		final EditText editPlayer1 = (EditText) findViewById(R.id.editPlayer1);
		final EditText editPlayer2 = (EditText) findViewById(R.id.editPlayer2);
		final EditText editGame = (EditText) findViewById(R.id.editGame);
		final EditText editTime = (EditText) findViewById(R.id.editTime);
		final Button btnGo = (Button) findViewById(R.id.btnGo);

		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		editPlayer1.setText(thePlayer.getLogin());

		final Bundle dataBundle = new Bundle();
		dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);

		editGame.setClickable(true);
		editGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create custom dialog object
				final Dialog dialog = new Dialog(PlayActivity.this);
				// Include dialog.xml file
				dialog.setContentView(R.layout.dialog_game);
				// Set dialog title
				dialog.setTitle(R.string.title_dialog_game);
				/*
				 * // set values for custom dialog components - text, image and
				 * button TextView text = (TextView)
				 * dialog.findViewById(R.id.textDialog);
				 * text.setText("Custom dialog Android example."); ImageView
				 * image = (ImageView) dialog.findViewById(R.id.imageDialog);
				 * image.setImageResource(R.drawable.image0);
				 */
				dialog.show();

				Button btnValidGame = (Button) dialog
						.findViewById(R.id.btnValid);
				// if button is clicked, close the custom dialog
				btnValidGame.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Close dialog
						dialog.dismiss();
					}
				});

			}
		});

		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intentOpenGame = new Intent(PlayActivity.this,
						GameActivity.class);

				intentOpenGame.putExtras(dataBundle);

				startActivity(intentOpenGame);
			}
		});

	}
}
