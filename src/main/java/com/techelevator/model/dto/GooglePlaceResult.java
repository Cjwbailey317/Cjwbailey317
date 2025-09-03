package com.techelevator.model.dto;

public class GooglePlaceResult {
    public String name;
    public String place_id;
    public String vicinity;
    public Geometry geometry;

    public static class Geometry {
        public Location location;
    }

    public static class Location {
        public double lat;
        public double lng;
    }
}