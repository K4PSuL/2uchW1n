package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		final Button btnP1 = (Button) findViewById(R.id.btnP1);
		final Button btnP2 = (Button) findViewById(R.id.btnP2);
		final ImageView imgColor = (ImageView) findViewById(R.id.imgColor);

		// On récupère le Player
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		btnP1.setText(thePlayer.getLogin());
		btnP2.setText("Player 2");
		
		
		imgColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Create custom dialog object
                final Dialog dialog = new Dialog(GameActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_game);
                // Set dialog title
                dialog.setTitle(R.string.title_dialog_stop);
 /*
                // set values for custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.textDialog);
                text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.image0);
 */
                dialog.show();
                 
                Button btnValidGame = (Button) dialog.findViewById(R.id.btnValid);
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
		
	}
}
