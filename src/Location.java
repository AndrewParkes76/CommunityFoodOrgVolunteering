/**
 * Class Location
 * Author: Andrew Parkes
 */
public class Location {

    public static final double EARTH_RADIUS = 3958.8; // in miles

    private double latitude;
    private double longitude;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Location(double latitude, double longitude, String address, String city, String state, String zipCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public double distanceTo(Location other) {
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(other.latitude);
        double lon2 = Math.toRadians(other.longitude);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}