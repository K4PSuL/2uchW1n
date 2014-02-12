package com.iia.touchwin.utils;

import com.iia.touchwin.R;
import com.iia.touchwin.views.MainActivity;

public class Const {
	public final static String PREFERENCES_PLAYER = "player";
	public final static String PREFERENCES_PLAYER2 = "player2";
	public final static String PREFERENCES_LOGIN = "login";
	public final static String PREFERENCES_SOUND = "sound";

	public final static String ERREUR_LOGIN = "Erreur, login incorrect";
	public final static String ERREUR_PLAYER2 = "Erreur, adversaire impossible";
	public final static String ERREUR_FORMVIDE = "Veuillez remplir tous les champs";
	
	public final static String DATABASE = "TouchWin";

	public final static String BUNDLE_PLAYER = "player";
	public final static String BUNDLE_PLAYER2 = "player2";
	public final static String BUNDLE_GAME = "game";
	public final static String BUNDLE_TIME = "time";
	
	public final static String WINNER = " à gagné !";
	
	public static Class<?> NOTIFICATION_ACTIVITY = MainActivity.class;
	public static Integer NOTIFICATION_ICON = R.drawable.ic_launcher;
}
