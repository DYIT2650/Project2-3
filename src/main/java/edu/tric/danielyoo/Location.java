package edu.tric.danielyoo;

public class Location {
    private String name;
    private Vector2 position;
    private boolean hasGasStation;

    // Default constructor for Jackson
    public Location() {
    }

    public Location(String name, Vector2 position, boolean hasGasStation) {
        this.name = name;
        this.position = position;
        this.hasGasStation = hasGasStation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setHasGasStation(boolean hasGasStation) {
        this.hasGasStation = hasGasStation;
    }

    public String getName() {
        return name;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean hasGasStation() {
        return hasGasStation;
    }

    @Override
    public String toString() {
        String gasString = this.hasGasStation ? "Yes" : "No";
        return String.format("%s: %s | Gas Station: %s", this.name, this.position, gasString);
    }
}