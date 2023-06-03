package com.localisation.calcul;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GeoConfig {
	OkHttpClient client;
	String depart;
	Request request;
	Response response;
	static String apiUrl="https://api.openrouteservice.org/geocode/search?api_key"
			+ "=5b3ce3597851110001cf6248999b7768655a4b41b10606ba60a7a7cb&text=";

//https://api.openrouteservice.org/geocode/search?api_key=5b3ce3597851110001cf6248999b7768655a4b41b10606ba60a7a7cb&text=
	public GeoConfig(String depart) {
		client = new OkHttpClient();
		this.depart = depart;
		this.request = new Request.Builder().url(apiUrl + depart).get().build();

	}
	public GeoResponse2 getAdress() {
		try {
		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();
		ObjectMapper mapper = new ObjectMapper();
		GeoResponse2 geocodeResponse = mapper.readValue(responseBody, GeoResponse2.class);

		return geocodeResponse;
	} catch (IOException e) {
		e.printStackTrace();
		return null;
	}
	}

}
