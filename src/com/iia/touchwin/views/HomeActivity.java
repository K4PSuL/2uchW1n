package com.iia.touchwin.views;


import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		final Button btnJouer = (Button) findViewById(R.id.btnPlay);
		final Button btnStats = (Button) findViewById(R.id.btnStats);
		final Button btnSettings = (Button) findViewById(R.id.btnSettings);
		final Bundle dataBundle = new Bundle();
		
		final ImageView oLogo = (ImageView) findViewById(R.id.imgLogo);
		
		// On récupére le Player
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		// On enregistre son Login pour populer le formulaire de connexion
		SharedPreferences.Editor oEditor = oSettings.edit();
		oEditor.putString(Const.PREFERENCES_LOGIN, thePlayer.getLogin());
		oEditor.commit();

		// On joue un son d'entrée
		Utils.playSound(HomeActivity.this, this, R.raw.home);

		// Animation du logo
		Animation animateLogo = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.logo);

		oLogo.startAnimation(animateLogo);
		
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
