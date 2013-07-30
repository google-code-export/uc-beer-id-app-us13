package edu.uc.beeridapp.dao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetworkDAO {
	
	/**
	 * Simple boilerplate logic.  Connect to a web server with HTTP Get and a URL.  Return results as a String.
	 * 
	 * @param uri web domain name and URL where we want to connect.
	 * @return the data that we get from that website.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String request(String uri) throws ClientProtocolException, IOException {
	    HttpClient httpClient = new DefaultHttpClient();
	     HttpGet httpGet = new HttpGet(uri);
	     ResponseHandler<String> responseHandler = new BasicResponseHandler();
	     String responseBody = "";
	     responseBody = httpClient.execute(httpGet, responseHandler);

		return responseBody;
	}

}
