package com.iia.touchwin.views;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import com.iia.touchwin.R;
import com.iia.touchwin.contracts.ResultContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.entities.Result;
import com.iia.touchwin.request.PlayerRequest;
import com.iia.touchwin.utils.ConnectWebService;
import com.iia.touchwin.utils.Const;
import com.iia.touchwin.utils.DateUtils;
import com.iia.touchwin.utils.TouchWinSqlLiteOpenHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class StatsActivity extends Activity {

	private static class MyResultAdapter extends ArrayAdapter<Result> {

		private Context context;
		private int resource;
		private LayoutInflater monInflateur;
		private Player oPlayer1;

		// On créer un adapater pour la listView
		public MyResultAdapter(Context context, int resource,
				List<Result> objects, Player thePlayer) {
			super(context, resource, objects);

			this.context = context;
			this.resource = resource;
			this.monInflateur = LayoutInflater.from(this.context);
			this.oPlayer1 = thePlayer;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View oView = monInflateur.inflate(this.resource, null);

			TextView lbPlayer = (TextView) oView.findViewById(R.id.lbPlayer);
			TextView lbDate = (TextView) oView.findViewById(R.id.lbDate);
			TextView lbResult = (TextView) oView.findViewById(R.id.lbResult);

			// On récupére le Result en fonction de l'index et on l'affiche
			Result oResult = this.getItem(position);

			Player oPlayer2 = null;

			if (oPlayer1.getId() == oResult.getId_player2()) {
				oPlayer2 = PlayerRequest.getPlayer(context,
						oResult.getId_player1());
			} else {
				oPlayer2 = PlayerRequest.getPlayer(context,
						oResult.getId_player2());
			}

			if (oPlayer2 != null) {

				lbPlayer.setText(String.valueOf(oPlayer2.getLogin()));
				lbDate.setText(DateUtils.formatDateTimeToString(
						oResult.getPlayDate(), context));

				if (oResult.getId_player1() == oPlayer1.getId()) {
					if (oResult.getScoreP1() > oResult.getScoreP2()) {
						oView.setBackgroundColor(R.color.yellow);
					} else {
						oView.setBackgroundColor(R.color.red);
					}

					lbResult.setText(String.valueOf(oResult.getScoreP1())
							+ " - " + String.valueOf(oResult.getScoreP2()));
				} else {
					if (oResult.getScoreP1() < oResult.getScoreP2()) {
						oView.setBackgroundColor(R.color.yellow);
					} else {
						oView.setBackgroundColor(R.color.red);
					}

					lbResult.setText(String.valueOf(oResult.getScoreP2())
							+ " - " + String.valueOf(oResult.getScoreP1()));
				}
			}

			return oView;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

//		ConnectWebService.getAllWebService(this);

		double total = 0;
		double win = 0;

		final TextView lbTotal = (TextView) findViewById(R.id.lbTotal);
		final TextView lbWins = (TextView) findViewById(R.id.lbWins);

		final ListView myResultList = (ListView) findViewById(R.id.listViewResults);

		// On récupére le Player
		final Player oPlayer1 = (Player) getIntent().getExtras()
				.getSerializable(Const.BUNDLE_PLAYER);

		// Initialisation du tableau de Results
		ArrayList<Result> aResults = new ArrayList<Result>();

		TouchWinSqlLiteOpenHelper oDbHelper = new TouchWinSqlLiteOpenHelper(
				StatsActivity.this, Const.DATABASE, null, 1);

		// Récupération de la BDD
		SQLiteDatabase dataBase = oDbHelper.getReadableDatabase();

		// Argument pour la condition de la requête SQL
		String[] whereArg = { String.valueOf(oPlayer1.getId()),
				String.valueOf(oPlayer1.getId()) };

		// Requête sur la BDD
		Cursor oCursor = dataBase.query(ResultContract.TABLE,
				ResultContract.COLS, ResultContract.COL_PLAYER1 + "=? OR "
						+ ResultContract.COL_PLAYER2 + "=?", whereArg, null,
				null, null);

		// Si au moin un résultat...
		if (oCursor.moveToFirst()) {
			do {
				final Result oResult = new Result();

				total++;

				DateTime datePlayDate = DateUtils.formatStringToDate(oCursor
						.getString(oCursor
								.getColumnIndex(ResultContract.COL_PLAYDATE)),
						StatsActivity.this);

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

				if (oResult.getId_player1() == oPlayer1.getId()) {
					if (oResult.getScoreP1() > oResult.getScoreP2()) {
						win++;
					}
				} else {
					if (oResult.getScoreP1() < oResult.getScoreP2()) {
						win++;
					}
				}

				aResults.add(oResult);

			} while (oCursor.moveToNext());

			if (total != 0 && win != 0) {
				win = (win * 100) / total;
			}

			lbTotal.setText(lbTotal.getText() + " " + String.format("%.0f", total));

			lbWins.setText(lbWins.getText() + " " + String.format("%.1f", win)
					+ "%");

			MyResultAdapter oAdapter = new MyResultAdapter(this,
					R.layout.row_score, aResults, oPlayer1);

			myResultList.setAdapter(oAdapter);

			Toast.makeText(getApplicationContext(), Const.TOAST_STATS,
					Toast.LENGTH_LONG).show();
		}

		myResultList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Result oResult = (Result) myResultList.getItemAtPosition(arg2);

				String shareString = "";
				String resultatFinal = "";
				Player oPlayer2 = null;

				if (oResult.getId_player1() == oPlayer1.getId()) {
					oPlayer2 = PlayerRequest.getPlayer(StatsActivity.this,
							oResult.getId_player2());

					if (oResult.getScoreP1() > oResult.getScoreP2()) {
						shareString = Const.SHARE_WINNER + oPlayer2.getLogin();
					} else {
						shareString = Const.SHARE_LOOSER + oPlayer2.getLogin();
					}

					resultatFinal = Const.SHARE_RESULT
							+ String.valueOf(oResult.getScoreP1()) + " - "
							+ String.valueOf(oResult.getScoreP2());
				} else {
					oPlayer2 = PlayerRequest.getPlayer(StatsActivity.this,
							oResult.getId_player1());

					if (oResult.getScoreP1() < oResult.getScoreP2()) {
						shareString = Const.SHARE_WINNER + oPlayer2.getLogin();
					} else {
						shareString = Const.SHARE_LOOSER + oPlayer2.getLogin();
					}

					resultatFinal = Const.SHARE_RESULT
							+ String.valueOf(oResult.getScoreP2()) + " - "
							+ String.valueOf(oResult.getScoreP1());
				}

				shareString = shareString + resultatFinal;

				Intent sendIntent = new Intent(
						android.content.Intent.ACTION_SEND);

				sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						Const.SHARE_SUBJECT);
				sendIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareString);
				sendIntent.setType("text/plain");

				startActivity(Intent.createChooser(sendIntent,
						Const.SHARE_TITLE));

			}

		});
	}
}
