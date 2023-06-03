package com.localisation.calcul;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponse2 {
	@JsonProperty("features")
	private List<Feature> features;

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Feature {
		@JsonProperty("geometry")
		private Geometry geometry;
		@JsonProperty("properties")
		private Properties properties;

		@JsonIgnoreProperties(ignoreUnknown = true)
		public Geometry getGeometry() {
			return geometry;
		}

		public void setGeometry(Geometry geometry) {
			this.geometry = geometry;
		}

		public Properties getProperties() {
			return properties;
		}

		public void setProperties(Properties properties) {
			this.properties = properties;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Geometry {
		@JsonProperty("coordinates")
		private double[] coordinates;

		public double[] getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(double[] coordinates) {
			this.coordinates = coordinates;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Properties {
		@JsonProperty("label")
		private String label;

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
}
