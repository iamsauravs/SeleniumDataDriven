package com.automation.core.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class ReadJSON {

	/*
	 * To Be Used when Array of Values is to be retrieved
	*/
	public ArrayList<String> getValuesUsingJsonPath(String url, String jsonPath) {
		
		String jsonResponse = getResponseFromURL(url);
		ArrayList<String> response = new ArrayList<String>();
		
		JSONArray jArray = JsonPath.read(jsonResponse, jsonPath);
		Iterator<Object> jIt = jArray.iterator();
		
		while(jIt.hasNext()) {
			response.add(jIt.next().toString());
		}
	    return response;
        
	}
	
	/*
	 * To Be Used when Single Value is to be retrieved
	*/
	public String getValueUsingJsonPath(String url, String jsonPath) {
		
		String jsonResponse = getResponseFromURL(url);
		String response = null;
		
		response = JsonPath.read(jsonResponse, jsonPath);
		return response;
	}
	
	/*
	 * To Be Used when Descendant Value is to be retrieved
	*/
	public Map<String,String> getValuesAsMapUsingJsonPath(String url, String jsonPath) {
		
		String jsonResponse = getResponseFromURL(url);
		Map<String, String> response = new HashMap<String, String>();
		
		response = JsonPath.read(jsonResponse, jsonPath);
		return response;
	}
	
	// To Get Value using keyName
	public String getValueForKey(String url, String keyName) {
		
		String jsonResponse = getResponseFromURL(url);
		
		JSONObject jObj = new JSONObject(jsonResponse);
		return jObj.getString(keyName);
        
	}
	
	/*
	 * To Get TestData from JSON response of DCR
	 * url - JSON Request URL
	 * jsonpath - JSONPATH of required Test Data from Response
	*/
	public Object[][] getTestData(String url, String jsonPath) {
		
		Object data[][] = null;
		
		String jsonResponse = getResponseFromURL(url);
		Object responseObject = JsonPath.read(jsonResponse, jsonPath);
		
		if(responseObject instanceof Map) {
			
			Map<String, String> response = new HashMap<String, String>();
			response = JsonPath.read(jsonResponse, jsonPath);
			
			data = new Object[response.size()][2];
			
			int counter=0;
			for(Map.Entry<String, String> entry : response.entrySet()){
			   
			    data[counter][0] = entry.getKey();
			    data[counter][1] = entry.getValue();

			    counter++;
			}
			
		} else if(responseObject instanceof JSONArray) {
			
			JSONArray jArray = JsonPath.read(jsonResponse, jsonPath);
			
			data = new Object[jArray.size()][1];
			
			Iterator<Object> jIt = jArray.iterator();
			int counter=0;
			
			while(jIt.hasNext()) {
				data[counter][0] = jIt.next().toString();
				counter++;
			}
		} else if(responseObject instanceof String) {
			
			data = new Object[1][1];
			data[0][0] = JsonPath.read(jsonResponse, jsonPath);
		} else {
			System.out.println("INVALID RESPONSE TYPE ENCOUNTERED!!!");
		}
		
		return data;
	}
	
	
	/*
	 * Get Response from HTTP URL
	*/
	private String getResponseFromURL(String url)  {
		StringBuffer response = null;
		
		try {
			URL urlForGetRequest = new URL(url);
			String readLine = null;
			response = new StringBuffer();
		    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		    
		    conection.setRequestMethod("GET");
		    //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
		    int responseCode = conection.getResponseCode();
		    
		    if (responseCode == HttpURLConnection.HTTP_OK) {
		        BufferedReader in = new BufferedReader(
		            new InputStreamReader(conection.getInputStream()));
		        
		        while ((readLine = in .readLine()) != null) {
		            response.append(readLine);
		        }
		        
		        in .close();
		        
		    } else {
		        System.out.println("GET NOT WORKED");
		    }
		} catch(Exception e) {
			
		}
	    
	    return response.toString();
	}
}
