package com.iia.touchwin.views;

import com.iia.touchwin.R;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		final CheckBox checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);
		final Button btnHelp = (Button) findViewById(R.id.btnHelp);
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		final SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		checkBoxSound.setChecked(oSettings.getBoolean(Const.PREFERENCES_SOUND,
				true));

		checkBoxSound.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// On enregistre le nouveau parametre de son dans le
				// SharedPreferences
				SharedPreferences.Editor oEditor = oSettings.edit();

				if (checkBoxSound.isChecked()) {
					oEditor.putBoolean(Const.PREFERENCES_SOUND, true);
					Utils.playSound(SettingsActivity.this, 
							SettingsActivity.this, R.raw.switch_sound);
				} else {
					oEditor.putBoolean(Const.PREFERENCES_SOUND, false);
				}
				oEditor.commit();
			}
		});
		
		
		btnHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intentOpenHelp = new Intent(SettingsActivity.this,
						HelpActivity.class);
				
				startActivity(intentOpenHelp);
				
			}
		});

	}
}
