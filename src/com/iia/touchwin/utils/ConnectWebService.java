package com.iia.touchwin.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.iia.touchwin.contracts.PlayerContract;
import com.iia.touchwin.entities.Player;
import com.iia.touchwin.entities.Result;

import android.app.Activity;
import android.util.Log;

public abstract class ConnectWebService {

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
	
	
	public static ArrayList<Result> getAllResultsWebService(Activity oActivity, Player oPlayer) {

		final ArrayList<Result> aResults = new ArrayList<Result>();

		RequestQueue oRequestQueue = Volley.newRequestQueue(oActivity
				.getApplicationContext());

		JsonArrayRequest getAllPlayerRequest = new JsonArrayRequest(
				Const.WEBSERVICE_GET_ALL_RESULTS + oPlayer.getId(),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {

						for (int i = 0; i < jsonArray.length(); i++) {
							try {
								
								Result oResult = new Result();
								
								JSONObject ResultJSON = jsonArray
										.getJSONObject(i);

								Result.setId(ResultJSON
										.getInt(ResultContract.COL_ID));
								Result.setLogin(ResultJSON
										.getString(ResultContract.COL_LOGIN));
								Result.setPassword(ResultJSON
										.getString(ResultContract.COL_PASSWORD));
								Result.setAvatar(ResultJSON
										.getString(ResultContract.COL_AVATAR));

								aResults.add(oResult);

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

		return aResults;
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
