package com.iia.touchwin.entities;

import java.util.Random;

import com.iia.touchwin.R;

import android.widget.ImageView;

public class GameReflex extends Game{
	
	private static int nbRounds;
	
	/**
	 * Retourne un nombre aléatoire entre les deux valeurs passées en paramètres
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomTime(int min, int max) {
		Random oRandom = new Random();
		
		int timeRound = oRandom.nextInt(max-min) + min;

		return timeRound;
	}
	
	/**
	 * Retourne un boolean permettant de savoir si la réponse du round est faux ou vrai
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean randomFalse(int min, int max) {
		int trueOrFalse = randomTime(min, max);
		
		boolean isFalse = false;
		
		if (trueOrFalse == 1) {
			isFalse = true;
		}
		
		return isFalse;
	}

}
