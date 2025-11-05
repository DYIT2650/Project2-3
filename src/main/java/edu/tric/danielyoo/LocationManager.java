package edu.tric.danielyoo;

import java.util.List;

public class LocationManager {
    private List<Location> locations;
    private String path;

    public LocationManager(String resourcePath){
        this.locations = LocationLoader.loadFromResource(resourcePath);
        this.path = resourcePath;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public boolean addLocation(Location location) {
        for (Location loc : locations) {
            if (loc.getName().equals(location.getName())) {
                System.err.println("Location with name '" + location.getName() + "' already exists.");
                return false;
            }
        }

        locations.add(location);
        return saveLocations();
    }

    public boolean deleteLocation(String locationName) {
        if (locations.size() <= 2) {
            System.err.println("Cannot delete location. At least 2 locations required for game.");
            return false;
        }

        Location toRemove = null;
        for (Location loc : locations) {
            if (loc.getName().equals(locationName)) {
                toRemove = loc;
                break;
            }
        }

        if (toRemove != null) {
            locations.remove(toRemove);
            return saveLocations();
        } else {
            System.err.println("Location '" + locationName + "' not found.");
            return false;
        }
    }

    private boolean saveLocations() {
        // Convert resource path to file system path for saving
        return LocationLoader.saveToFile(locations, "src/main/resources" + path);
    }
}
