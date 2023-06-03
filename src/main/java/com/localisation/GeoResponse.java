package com.localisation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponse {
	@JsonProperty("results")
	private Result[] results;

	public Result[] getResult() {
		return results;
	}

	public void setResult(Result[] result) {
		this.results = result;
	}

}
