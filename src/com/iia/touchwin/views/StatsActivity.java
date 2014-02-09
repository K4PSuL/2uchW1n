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
import android.widget.ListView;
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = monInflateur.inflate(this.resource, null);

			// On récupére le Result en fonction de l'index et on l'affiche
			Result oResult = this.getItem(position);

			return view;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		final ListView myResultList = (ListView) findViewById(R.id.listView1);

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

				aResults.add(oResult);

			} while (oCursor.moveToNext());
			
			myResultAdapter oAdapter = new myResultAdapter(this, R.layout.row_score, aResults);

			myResultList.setAdapter(oAdapter);
			
		}
	}

}
