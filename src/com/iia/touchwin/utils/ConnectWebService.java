package com.iia.touchwin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
import android.util.Log;

public abstract class ConnectWebService {

	public static String callWebService(String url) {
		InputStream inputStream = null;
		String result = "";
		
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else {
				result = "Did not work!";
			}

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		Log.d("result", result);
		return result;
	}

	// convert inputstream to String
	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		
		String line = "";
		String result = "";
		
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}
		
		inputStream.close();
		
		return result;

	}

	// check network connection
	// public boolean isConnected(){
	// ConnectivityManager connMgr = (ConnectivityManager)
	// getSystemService(this.CONNECTIVITY_SERVICE);
	// NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	// if (networkInfo != null && networkInfo.isConnected())
	// return true;
	// else
	// return false;
	// }

}
