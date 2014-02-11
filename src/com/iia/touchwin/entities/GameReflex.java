package com.iia.touchwin.entities;

import java.util.Random;

import com.iia.touchwin.R;

import android.widget.ImageView;

public class GameReflex extends Game{
	
	private int nbRounds;
	
	/**
	 * Retourne un nombre aléatoire entre les deux valeurs passé en paramètres
	 * @param min
	 * @param max
	 * @return
	 */
	public int timeRandom(int min, int max) {
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
	public boolean isFalse(int min, int max) {
		int trueOrFalse = timeRandom(min, max);
		
		boolean isFalse = false;
		
		if (trueOrFalse == 1) {
			isFalse = true;
		}
		
		return isFalse;
	}
	
	public void playGame(ImageView imgColor) throws InterruptedException {
		int roundPlay = 0;
		boolean isFalse;
		
		do {
			
			isFalse = isFalse(1, 5);
			
			roundPlay++;
			Thread.sleep(timeRandom(2,7));
			
			if (isFalse) {
				imgColor.setBackgroundResource(R.color.yellow);
			} else {
				imgColor.setBackgroundResource(R.color.red);
			}
			
			
		} while(nbRounds == roundPlay);
	}

}
