package com.iia.touchwin.utils;

import com.iia.touchwin.R;
import com.iia.touchwin.views.MainActivity;

public class Const {
	public final static String PREFERENCES_PLAYER = "player";
	public final static String PREFERENCES_PLAYER2 = "player2";
	public final static String PREFERENCES_LOGIN = "login";
	public final static String PREFERENCES_SOUND = "sound";

	public final static String ERREUR_LOGIN = "Erreur, login incorrect";
	public final static String ERREUR_PLAYER2 = "Toi VS Toi ?!...";
	public final static String ERREUR_FORMVIDE = "Veuillez remplir tous les champs";
	
	public final static String DATABASE = "TouchWin";

	public final static String BUNDLE_PLAYER = "player";
	public final static String BUNDLE_PLAYER2 = "player2";
	public final static String BUNDLE_GAME = "game";
	public final static String BUNDLE_TIME = "time";
	
	public final static String WINNER = " a gagn� !";
	
	public final static String COLOR_RED = "#c80000";
	
	public final static String CHRONO_GO = "Go !";
	
	public final static String WEBSERVICE_GETRESULTS = "http://www.tommy-calais.fr/touchwin/public/player/?method=getResults&id_player=1";
	
	public final static String TOAST_STATS = "Clic sur ton score pour le publier !";
	
	public final static String SHARE_TITLE = "Partager mon score via...";
	public final static String SHARE_SUBJECT = "Mon score TouchWin !";
	public final static String SHARE_WINNER = "Wouah ! J'ai gagn� � TouchWin contre ";
	public final static String SHARE_LOOSER = "Pouah ! J'ai perdu � TouchWin contre ";
	public final static String SHARE_RESULT = "\nR�sultat : ";
	
	public static Class<?> NOTIFICATION_ACTIVITY = MainActivity.class;
	public static Integer NOTIFICATION_ICON = R.drawable.ic_launcher;
}
