package com.iia.touchwin.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iia.touchwin.R;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.contracts.ResultContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.entities.Result;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StatsActivity extends Activity {

	private static class myResultAdapter extends ArrayAdapter<Result> {

		private Context context;
		private int resource;
		private LayoutInflater monInflateur;

		// On créer un adapater pour la listView
		public myResultAdapter(Context context, int resource,
				List<Result> objects) {
			super(context, resource, objects);

			this.context = context;
			this.resource = resource;
			this.monInflateur = LayoutInflater.from(this.context);
		}
		
		

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View oView = monInflateur.inflate(this.resource, null);

			TextView lbPlayer = (TextView) oView.findViewById(R.id.lbPlayer);
			TextView lbDate = (TextView) oView.findViewById(R.id.lbDate);
			TextView lbResult = (TextView) oView.findViewById(R.id.lbResult);
			RelativeLayout bg = (RelativeLayout) oView.findViewById(R.id.relative);

			// On récupére le Result en fonction de l'index et on l'affiche
			Result oResult = this.getItem(position);

			lbPlayer.setText(String.valueOf(oResult.getId_player2()));
			lbDate.setText(String.valueOf(oResult.getPlayDate()));
			lbResult.setText(String.valueOf(oResult.getScoreP1()) + " - "
					+ String.valueOf(oResult.getScoreP2()));

			if (oResult.getScoreP1() > oResult.getScoreP2()) {
				//oView.setBackgroundColor(R.color.yellow);
				bg.setBackgroundColor(R.color.yellow);
			} else {
				//oView.setBackgroundColor(R.color.red);
				bg.setBackgroundColor(R.color.red);
			}

			return oView;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		double total = 0;
		double win = 0;

		final TextView lbTotal = (TextView) findViewById(R.id.lbTotal);
		final TextView lbWins = (TextView) findViewById(R.id.lbWins);
		
		final ListView myResultList = (ListView) findViewById(R.id.listViewResults);

		// On récupére le Player
		final Player thePlayer = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		// Initialisation du tableau de Results
		ArrayList<Result> aResults = new ArrayList<Result>();

		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				StatsActivity.this, Const.DATABASE, null, 1);

		// Récupération de la BDD
		SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

		// Argument pour la condition de la requête SQL
		String[] whereArg = { String.valueOf(thePlayer.getId()) };

		// Requête sur la BDD
		Cursor oCursor = dataBase.query(ResultContract.TABLE,
				ResultContract.COLS, ResultContract.COL_PLAYER1 + "=?",
				whereArg, null, null, null);

		// Si au moin un résultat...
		if (oCursor.moveToFirst()) {
			do {
				final Result oResult = new Result();

				total++;

				Date datePlayDate = new Date((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_PLAYDATE))));

				oResult.setId((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_ID))));
				oResult.setId_game((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_ID_GAME))));
				oResult.setPlayDate(datePlayDate);
				oResult.setId_player1((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_PLAYER1))));
				oResult.setId_player2((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_PLAYER2))));
				oResult.setScoreP1((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_SCOREP1))));
				oResult.setScoreP2((oCursor.getInt(oCursor
						.getColumnIndex(ResultContract.COL_SCOREP2))));

				if (oResult.getScoreP1() > oResult.getScoreP2()) {
					win++;
				}

				aResults.add(oResult);

			} while (oCursor.moveToNext());

			if (total != 0 && win != 0) {
					win = (win * 100) / total;
			}
		
			lbTotal.setText(lbTotal.getText() + " " + String.valueOf(total));
			lbWins.setText(lbWins.getText() + " " + String.valueOf(win) + "%");
			
			myResultAdapter oAdapter = new myResultAdapter(this,
					R.layout.row_score, aResults);

			myResultList.setAdapter(oAdapter);
		}
	}
}
