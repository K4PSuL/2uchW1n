package com.iia.touchwin.utils;

import android.content.res.Resources;

import com.iia.touchwin.R;
import com.iia.touchwin.views.TouchWinMediaPlayer;

public class Utils {

	public playSound(Context oContext, Resources oRessource) {
		final MediaPlayer oMediaPlayer = (MediaPlayer) WinMediaPlayer.create(getApplicationContext(), R.raw.home);
		oMediaPlayer.start();
	}
}
