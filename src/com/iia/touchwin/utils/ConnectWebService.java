package com.iia.touchwin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.iia.touchwin.app.TouchWin;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.entities.Rank;
import com.iia.touchwin.entities.Result;

import android.app.Activity;
import android.util.Log;

public abstract class ConnectWebService {

	public final static String JSON_LOGIN = "login";
	public final static String JSON_ID = "id";
	public final static String JSON_WIN = "win";
	public final static String JSON_TOTAL = "total";

	public static ArrayList<Player> getAllPlayerWebService(Activity oActivity) {

		final ArrayList<Player> aPlayers = new ArrayList<Player>();

		RequestQueue oRequestQueue = Volley.newRequestQueue(oActivity
				.getApplicationContext());

		JsonArrayRequest getAllPlayerRequest = new JsonArrayRequest(
				Const.WEBSERVICE_GET_ALL_PLAYERS,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {

						for (int i = 0; i < jsonArray.length(); i++) {
							try {

								Player oPlayer = new Player();

								JSONObject PlayerJSON = jsonArray
										.getJSONObject(i);

								oPlayer.setId(PlayerJSON
										.getInt(PlayerContract.COL_ID));
								oPlayer.setLogin(PlayerJSON
										.getString(PlayerContract.COL_LOGIN));
								oPlayer.setPassword(PlayerJSON
										.getString(PlayerContract.COL_PASSWORD));
								oPlayer.setAvatar(PlayerJSON
										.getString(PlayerContract.COL_AVATAR));

								aPlayers.add(oPlayer);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Log.e("getAllWebServiceError", error.toString());
					}

				});

		// On ajoute la Request au RequestQueue pour la lancer
		oRequestQueue.add(getAllPlayerRequest);

		return aPlayers;
	}

	public static void getAllResultsWebService(Activity oActivity) {

		final ArrayList<Rank> aRanks = new ArrayList<Rank>();
		
		TouchWin TouchWinApp = ((TouchWin)oActivity.getApplication());

		RequestQueue oRequestQueue = Volley.newRequestQueue(oActivity
				.getApplicationContext());

		JsonArrayRequest getAllPlayerRequest = new JsonArrayRequest(
				Const.WEBSERVICE_GET_ALL_RESULTS,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {

						for (int i = 0; i < jsonArray.length(); i++) {
							try {

								Rank oRank = new Rank();

								JSONObject ResultsJSON = jsonArray
										.getJSONObject(i);

								String login = ResultsJSON.getString(JSON_LOGIN);

								int id = ResultsJSON.getInt(JSON_ID);

								int win = ResultsJSON.getInt(JSON_WIN);

								int total = ResultsJSON.getInt(JSON_TOTAL);

								oRank.setId(id);
								oRank.setLogin(login);
								oRank.setTotal(total);
								oRank.setWin(win);
								
								aRanks.add(oRank);
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Log.e("getAllResultsWebService", error.toString());
					}

				});

		// On ajoute la Request au RequestQueue pour la lancer
		oRequestQueue.add(getAllPlayerRequest);

		TouchWinApp.setRanks(aRanks);
	}

	// /**
	// *
	// * @return
	// */
	// public boolean isConnected(){
	// ConnectivityManager connMgr = (ConnectivityManager)
	// // getSystemService(this.CONNECTIVITY_SERVICE);
	// NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	// if (networkInfo != null && networkInfo.isConnected())
	// return true;
	// else
	// return false;
	// }
}
