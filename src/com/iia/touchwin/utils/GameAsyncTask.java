//package com.iia.touchwin.utils;
//
//import java.lang.reflect.Array;
//
//import com.iia.touchwin.R;
//import com.iia.touchwin.entities.Game;
//import com.iia.touchwin.entities.GameReflex;
//import com.iia.touchwin.entities.Result;
//import com.iia.touchwin.views.GameActivity;
//
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//public class GameAsyncTask extends AsyncTask<Void, Integer, Void> {
//	
//	protected void onProgressUpdate(Integer... values) {
//		super.onProgressUpdate(values);
//	
//	}
//
//	protected Void doInBackground(Void... arg0) {
//		int roundPlay = 0;
//		
//		do {
//			final boolean isFalse = GameReflex.isFalse(1, 5);
//
//			roundPlay++;
//			
//			int timeRound = GameReflex.timeRandom(2, 7);
//			
//			try {
//				Thread.sleep(timeRound);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			if (isFalse) {
//				publishProgress(R.color.yellow);
//			} else {
//				publishProgress(R.color.red);
//			}
//
//			
//
//		} while (nbRounds != roundPlay);
//		
//		return null;
//	}
//
//}
