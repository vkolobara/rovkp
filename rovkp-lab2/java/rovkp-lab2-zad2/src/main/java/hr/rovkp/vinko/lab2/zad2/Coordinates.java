/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.rovkp.vinko.lab2.zad2;

/**
 *
 * @author vkolobara
 */
public class Coordinates {

    private final double longitude;
    private final double latitude;

    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean isInBoundingBox(Coordinates start, Coordinates end) {

        double minLat, minLong, maxLat, maxLong;

        minLat = Math.min(start.latitude, end.latitude);
        maxLat = Math.max(start.latitude, end.latitude);
        minLong = Math.min(start.longitude, end.longitude);
        maxLong = Math.max(start.longitude, end.longitude);

        return minLong <= longitude
                && minLat <= latitude
                && maxLong >= longitude
                && maxLat >= latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
