package com.iia.touchwin.utils;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.util.Log;

public abstract class ConnectWebService {

	public void callWebService(Activity oActivity) {

		RequestQueue queue = Volley.newRequestQueue(oActivity
				.getApplicationContext());

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, Const.WEBSERVICE_GETRESULTS, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						Log.e("response", response.toString());
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("erreur", error.toString());
					}

				});

		queue.add(jsObjRequest);
	}

//	/**
//	 * 
//	 * @return
//	 */
//	public boolean isConnected(){
//	 ConnectivityManager connMgr = (ConnectivityManager)
////	 getSystemService(this.CONNECTIVITY_SERVICE);
//	 NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//	 if (networkInfo != null && networkInfo.isConnected())
//	 return true;
//	 else
//	 return false;
//	 }
}
