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
import android.view.animation.Animation.AnimationListener;
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
		final Player oPlayer1 = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		// On enregistre son Login pour populer le formulaire de connexion
		SharedPreferences.Editor oEditor = oSettings.edit();
		oEditor.putString(Const.PREFERENCES_LOGIN, oPlayer1.getLogin());
		oEditor.commit();

		// Animation du logo
		Animation animateLogo = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.logo);
		animateLogo.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// On joue un son d'entrée
				Utils.playSound(HomeActivity.this, R.raw.home);
			}
		});
		
		oLogo.startAnimation(animateLogo);
		
		
		dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) oPlayer1);
		//Animation animation = AnimationUtils.loadAnimation(HomeActivity.this, R.drawable.logo);
		//oLogo.startAnimation(animation);
		
		
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
