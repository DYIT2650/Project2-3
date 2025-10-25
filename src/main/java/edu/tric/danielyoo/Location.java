package edu.tric.danielyoo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    private String name;
    private Vector2 position;
    private boolean hasGasStation;

    // Default constructor for Jackson
    public Location() {
        this.position = new Vector2(0, 0);
    }

    public Location(String name, Vector2 position, boolean hasGasStation) {
        this.name = name;
        this.position = position;
        this.hasGasStation = hasGasStation;
    }

    // Jackson will use these setters during deserialization
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("x")
    public void setX(double x) {
        if (this.position == null) {
            this.position = new Vector2(0, 0);
        }
        this.position.x = x;
    }

    @JsonProperty("y")
    public void setY(double y) {
        if (this.position == null) {
            this.position = new Vector2(0, 0);
        }
        this.position.y = y;
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
    public String toString(){
        String gasString = this.hasGasStation ? "Yes" : "No";
        return String.format("%s: %s | Gas Station: %s", this.name, this.position, gasString);
    }
}