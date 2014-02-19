package com.iia.touchwin.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.iia.touchwin.R;
import com.iia.touchwin.entities.Rank;
import com.iia.touchwin.utils.ConnectWebService;
import com.iia.touchwin.utils.Const;

public class RankActivity extends Activity {

	public final static String JSON_LOGIN = "login";
	public final static String JSON_ID = "id";
	public final static String JSON_WIN = "win";
	public final static String JSON_TOTAL = "total";

	public class MyRankAdapter extends ArrayAdapter<Rank> {

		private Context context;
		private int resource;
		private LayoutInflater monInflateur;

		// On créer un adapater pour la listView
		public MyRankAdapter(Context context, int resource, ArrayList<Rank> objects) {
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
			TextView lbTotal = (TextView) oView.findViewById(R.id.lbTotal);
			TextView lbWin = (TextView) oView.findViewById(R.id.lbWin);

			// On récupére le Rank en fonction de l'index et on l'affiche
			Rank oRank = this.getItem(position);

			lbPlayer.setText(oRank.getLogin());
			lbTotal.setText(oRank.getTotal());
			lbWin.setText(oRank.getWin());

			return oView;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);

		final ListView myRankList = (ListView) findViewById(R.id.listViewRank);

		// Initialisation du tableau de Results
		final ArrayList<Rank> aRanks = new ArrayList<Rank>();


		
//		MyRankAdapter oAdapter = new MyRankAdapter(
//				RankActivity.this, R.layout.row_score, aRanks);
//
//		myRankList.setAdapter(oAdapter);
	
	}
}
