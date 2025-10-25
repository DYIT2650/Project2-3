package edu.tric.danielyoo;

public class Vehicle {
    private Location location;

    private double maxFuel;
    private double currentFuel;
    private double mpg;

    public Vehicle(Location location, double maxFuel, double mpg) {
        this.location = location;
        this.maxFuel = maxFuel;
        this.currentFuel = maxFuel;
        this.mpg = mpg;
    }

    public boolean flyTo(Location newLocation){
        double distanceTo = this.location.getPosition().distance(newLocation.getPosition());
        double maxTravelableMiles = getMaximumMiles();
        boolean canFlyTo = maxTravelableMiles >= distanceTo;
        if (canFlyTo){
            this.currentFuel -= getGallonsToTravelTo(newLocation);
            this.location = newLocation;
            return true;
        }
        return false;
    }

    public double getGallonsToTravelTo(Location newLocation){
        double distanceTo = this.location.getPosition().distance(newLocation.getPosition());
        return distanceTo/this.mpg;
    }

    public double getMaximumMiles(){
        return this.currentFuel * this.mpg;
    }
    
    public Location getLocation() {
        return location;
    }

    public boolean refuel(){
        if (this.location.hasGasStation()){
            this.currentFuel = maxFuel;
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return String.format("Located at %s | %.1f/%.1f Gallons Remaining | %.1f MPG | Maximum Miles: %.1f", this.location.getName(), this.currentFuel, this.maxFuel, this.mpg, getMaximumMiles());
    }
}