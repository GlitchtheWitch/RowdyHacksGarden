package org.Confidental;

public class PositionUpdate {

    private String deviceId;

    private double latitude;
    private double longitude;

    private double x;
    private double y;

    // Device ID
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    // GPS latitude
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // GPS longitude
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Map X position
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    // Map Y position
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}