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

    //TODO: add funcitonality to add to the locationloader, but now it feels little bit like its a useless wrapper over locationloader...?
}
