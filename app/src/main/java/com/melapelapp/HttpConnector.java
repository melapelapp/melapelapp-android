package com.melapelapp;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpConnector {
	
	String domainUrl="";
	
	public HttpConnector(String domainUrl)
	{
		this.domainUrl=domainUrl;
	}
	
	public String getResponseText(String query) //throws IOException
	{
		String fullUrl = domainUrl + "?query=" + query;
		
		//StringBuilder response  = new StringBuilder();
	    String response="";
		
	    URL url;
		try 
		{
			url = new URL(fullUrl);
		    
			DefaultHttpClient defaultClient = new DefaultHttpClient();
		    HttpGet httpGetRequest = new HttpGet(fullUrl);
		    HttpResponse httpResponse = defaultClient.execute(httpGetRequest);

		    Scanner scanner = new Scanner(httpResponse.getEntity().getContent(), "UTF-8");       

		    while(scanner.hasNextLine()) { // scanner looks ahead for an end-of-line
		        response += scanner.nextLine() + "\n"; // read the full line, you can append a \n
		    }   

			 
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return response.toString();
	}

	public String getResponseText2(String query) //throws IOException
	{
		String fullUrl = domainUrl + "?query=" + query;

		//StringBuilder response  = new StringBuilder();
		String response="";

		URL url;
		try
		{
			url = new URL(fullUrl);

			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.getRequestMethod();
			httpURLConnection.connect();
			Scanner scanner = new Scanner(httpURLConnection.getInputStream(), "UTF-8");

			while(scanner.hasNextLine()) { // scanner looks ahead for an end-of-line
				response += scanner.nextLine() + "\n"; // read the full line, you can append a \n
			}


		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}


}
