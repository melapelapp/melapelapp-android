package com.melapelapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/*
 Programmer:  Jonathan Gama
 */

//Input Progress Output
public class ServerConnector extends AsyncTask<String, Integer, String> {

	/** Activity to access to UI **/
	private final PersonListActivity activity;
	/** Progress dialog to display the progress **/
	private ProgressDialog progDialog;
	/** ArrayList with the Data object **/
	//private ArrayList<Person> records = new ArrayList<Person>();
    private String json;

	public ServerConnector(PersonListActivity activity, String json) {
		this.activity = activity;
		this.json = json;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Create and display a progress dialog to inform the user about the
		// download
		String title = "Load Title and Name";
		String message = "Loading title and name  ...";
		progDialog = ProgressDialog.show(this.activity, title, message, true, false);

	}

	@Override
	protected String doInBackground(String... params) {
		String urlString = params[0];
		try {
			// Create HttpClient and make GET request to the given URL
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(urlString));

			// If response not OK (Code 200)
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.d("responseCode", "responseCode != HttpStatus.SC_OK "
						+ httpResponse.getStatusLine().getStatusCode());
				return json;
			} else {
				// Get the result on a String to parse as JSON object
				json = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (IOException ioException) {
			Log.d("ioException", "ioException EntityUtils.toString(httpResponse.getEntity() "
					+ ioException.toString());
			return json;
		}
		return json;
	}


	@Override
	protected void onProgressUpdate(Integer... progress) {

		progDialog.setProgress(progress[0]);
	//activity.setProgressPercent(progress[0]);
	}


	@Override
	protected void onPostExecute(String result) {
		progDialog.dismiss();
		activity.updateUI(json);
	}

}
