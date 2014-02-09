package com.iia.touchwin.views;

import java.util.Date;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		final CheckBox checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);
		final Player thePlayer = (Player) getIntent().getExtras().getSerializable(Const.BUNDLE_PLAYER);

		final SharedPreferences oSettings = this.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);
		
		final Boolean trueBool = true;
		final Boolean falseBool = false;
		checkBoxSound.setChecked(oSettings.getBoolean(Const.PREFERENCES_SOUND, true));

		checkBoxSound.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// On enregistre le nouveau parametre de son dans le SharedPreferences
				SharedPreferences.Editor oEditor = oSettings.edit();

				if (checkBoxSound.isChecked()) {
					oEditor.putBoolean(Const.PREFERENCES_SOUND, true);
				} else {
					oEditor.putBoolean(Const.PREFERENCES_SOUND, false);
				}
				oEditor.commit();
			}
		});

	}
}
