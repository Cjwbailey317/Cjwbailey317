package com.techelevator.model.dto;

import java.util.List;

public class GoogleGeocodeResponse {
    public List<GeoResult> results;

    public static class GeoResult {
        public Geometry geometry;
    }

    public static class Geometry {
        public Location location;
    }

    public static class Location {
        public double lat;
        public double lng;
    }
}