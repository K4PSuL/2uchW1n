package com.iia.touchwin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;

import com.iia.touchwin.R;

public abstract class Utils {

	/**
	 * Permet de jouer un son
	 * 
	 * @param oContext
	 * @param oActivity
	 * @param resource
	 */
	public static void playSound(Context oContext, Activity oActivity,
			int resource) {

		SharedPreferences oSettings = oActivity.getSharedPreferences(
				Const.PREFERENCES_PLAYER, Context.MODE_PRIVATE);

		if (oSettings.getBoolean(Const.PREFERENCES_SOUND, true)) {
			MediaPlayer oMediaPlayer = (MediaPlayer) MediaPlayer.create(
					oContext, resource);
			oMediaPlayer.start();
		}
	}
}
