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
import com.iia.touchwin.ui.PlayActivity;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public abstract class ConnectWebService {

	public final static String JSON_LOGIN = "login";
	public final static String JSON_ID = "id";
	public final static String JSON_WIN = "win";
	public final static String JSON_TOTAL = "total";

	public static void getAllPlayerWebService(Activity oActivity) {

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

	}

	public static void getMsgWeb(final Activity oActivity) {

		RequestQueue oRequestQueue = Volley.newRequestQueue(oActivity
				.getApplicationContext());

		JsonArrayRequest getMsgWeb = new JsonArrayRequest(
				Const.WEBSERVICE_MSG_WEB, new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray jsonArray) {

						for (int i = 0; i < jsonArray.length(); i++) {
							try {

								JSONObject MsgJSON = jsonArray.getJSONObject(i);

								Toast.makeText(
										oActivity.getApplicationContext(),
										MsgJSON.getString("msg"),
										Toast.LENGTH_LONG).show();

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						Log.e("getMsgWeb", error.toString());
					}

				});

		// On ajoute la Request au RequestQueue pour la lancer
		oRequestQueue.add(getMsgWeb);
	}
}
