package edu.tric.danielyoo;

import java.util.List;

public class LocationManager {
    private List<Location> locations;

    public LocationManager(String locationsPath){
        this.locations = LocationLoader.loadFromResource(locationsPath);
    }

    public List<Location> getLocations() {
        return locations;
    }
}
