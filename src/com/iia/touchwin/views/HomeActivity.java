package com.iia.touchwin.views;

import java.util.Date;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.*;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		final Button btnJouer = (Button) findViewById(R.id.btnPlay);
		final Button btnStats = (Button) findViewById(R.id.btnStats);
		final Button btnSettings = (Button) findViewById(R.id.btnSettings);
		final Bundle dataBundle = new Bundle();

		// On récupére le Player
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		// On enregistre son Login pour populer le formulaire de connexion
		SharedPreferences.Editor oEditor = oSettings.edit();
		oEditor.putString(Const.PREFERENCES_LOGIN, thePlayer.getLogin());
		oEditor.commit();

		// On joue un son d'entré
		Utils.playSound(this.getApplicationContext(), this, R.raw.home);

		dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);

		btnJouer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentOpenPlay = new Intent(HomeActivity.this,
						PlayActivity.class);

				intentOpenPlay.putExtras(dataBundle);

				startActivity(intentOpenPlay);
			}
		});

		btnStats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentOpenStats = new Intent(HomeActivity.this,
						StatsActivity.class);

				intentOpenStats.putExtras(dataBundle);

				startActivity(intentOpenStats);
			}
		});

		btnSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentOpenSettings = new Intent(HomeActivity.this,
						SettingsActivity.class);

				intentOpenSettings.putExtras(dataBundle);

				startActivity(intentOpenSettings);
			}
		});
	}
}
