package com.iia.touchwin.views;

import java.util.Date;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
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

		SharedPreferences mySetting = this.getSharedPreferences(
				Const.PREFERENCES_CONNECTION, Context.MODE_PRIVATE);

		SharedPreferences settings = this.getSharedPreferences("connexion",
				Context.MODE_PRIVATE);

		SharedPreferences.Editor editor = settings.edit();

		final Player thePlayer = new Player();

		Bundle dataBundle = new Bundle();

		dataBundle.putSerializable(Const.BUNDLE_PLAYER, (Player) thePlayer);

		btnJouer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Bundle dataBundle = new Bundle();
				dataBundle.putSerializable(Const.BUNDLE_PLAYER,
						(Player) thePlayer);

				Intent intentOpenPlay = new Intent(HomeActivity.this,
						PlayActivity.class);

				intentOpenPlay.putExtras(dataBundle);

				startActivity(intentOpenPlay);

			}
		});

		btnStats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle dataBundle = new Bundle();
				dataBundle.putSerializable(Const.BUNDLE_PLAYER,
						(Player) thePlayer);

				Intent intentOpenStats = new Intent(HomeActivity.this,
						StatsActivity.class);

				intentOpenStats.putExtras(dataBundle);

				startActivity(intentOpenStats);

			}
		});

		btnSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle dataBundle = new Bundle();
				dataBundle.putSerializable(Const.BUNDLE_PLAYER,
						(Player) thePlayer);

				Intent intentOpenSettings = new Intent(HomeActivity.this,
						SettingsActivity.class);

				intentOpenSettings.putExtras(dataBundle);

				startActivity(intentOpenSettings);
			}
		});

	}
}
