package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class StatsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

//		final Button btnJouer = (Button) findViewById(R.id.btnPlay);
//		final Button btnStats = (Button) findViewById(R.id.btnStats);
//		final Button btnSettings = (Button) findViewById(R.id.btnSettings);

		// On récupére le Player
		final Player thePlayer = (Player) getIntent().getExtras().getSerializable(Const.BUNDLE_PLAYER);

	}
}
