package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class PlayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		final Button editPlayer1 = (Button) findViewById(R.id.editPlayer1);
//		final Button btnStats = (Button) findViewById(R.id.btnStats);
//		final Button btnSettings = (Button) findViewById(R.id.btnSettings);
		
		final Player thePlayer = (Player) getIntent().getExtras().getSerializable(Const.BUNDLE_PLAYER);

		editPlayer1.setText(thePlayer.getLogin());
	}
}
