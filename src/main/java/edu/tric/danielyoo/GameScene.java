package edu.tric.danielyoo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class GameScene extends VBox {

    private final Vehicle vehicle;
    private Label statusLabel;
    private Label messageLabel;
    private final LocationManager locationManager;

    public GameScene(Vehicle vehicle) {
        super(20); // spacing between elements
        this.vehicle = vehicle;
        this.locationManager = App.getLocationManager();

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #f5f5f5;");

        initializeUI();
        updateDisplay();
    }

    private void initializeUI() {
        // Title
        Label title = new Label("Vehicle Travel Game");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Status display
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px;");

        // Message label
        messageLabel = new Label("");
        messageLabel.setStyle("-fx-font-weight: bold;");

        getChildren().addAll(title, statusLabel, messageLabel);
    }

    private void updateDisplay() {
        getChildren().clear();

        // Title
        Label title = new Label("Vehicle Travel Game");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Current status
        String status = String.format(
            "Location: %s\nFuel: %.1f/%.1f gallons\nMax Distance: %.1f miles%s",
            vehicle.getLocation().getName(),
            vehicle.getCurrentFuel(),
            vehicle.getMaxFuel(),
            vehicle.getMaximumMiles(),
            vehicle.getLocation().hasGasStation() ? "\n⛽ Gas Station Available" : ""
        );
        statusLabel = new Label(status);
        statusLabel.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-padding: 10px;");

        getChildren().addAll(title, statusLabel, messageLabel);

        // Separator
        getChildren().add(new Label("--- Available Destinations ---"));

        // Add destinations
        displayDestinations();

        // Add action buttons at bottom
        displayActionButtons();
    }

    private void displayDestinations() {
        List<Location> locations = locationManager.getLocations();
        Location currentLocation = vehicle.getLocation();

        for (Location location : locations) {
            if (location.equals(currentLocation)) {
                continue;
            }

            HBox locationBox = createLocationRow(location, currentLocation);
            getChildren().add(locationBox);
        }
    }

    private HBox createLocationRow(Location destination, Location currentLocation) {
        double distance = currentLocation.getPosition().distance(destination.getPosition());
        double fuelNeeded = vehicle.getGallonsToTravelTo(destination);
        boolean canReach = fuelNeeded <= vehicle.getCurrentFuel();

        HBox locationBox = new HBox(10);
        locationBox.setAlignment(Pos.CENTER_LEFT);

        // Location info label
        String locationInfo = String.format("%s (%.1f mi, %.1f gal)%s",
            destination.getName(),
            distance,
            fuelNeeded,
            destination.hasGasStation() ? " ⛽" : "");

        Label locationLabel = new Label(locationInfo);
        locationLabel.setPrefWidth(250);

        // Travel button
        Button travelButton = new Button("Travel");
        travelButton.setDisable(!canReach);
        travelButton.setStyle(canReach ? "-fx-background-color: #4CAF50;" : "");

        travelButton.setOnAction(e -> handleTravel(destination));

        locationBox.getChildren().addAll(locationLabel, travelButton);
        return locationBox;
    }

    private void displayActionButtons() {
        HBox actions = new HBox(20);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(20, 0, 0, 0));

        Button refuelButton = new Button("Refuel");
        refuelButton.setDisable(!vehicle.getLocation().hasGasStation());
        refuelButton.setOnAction(e -> handleRefuel());

        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> handleNewGame());

        actions.getChildren().addAll(refuelButton, newGameButton);
        getChildren().add(actions);
    }

    private void handleTravel(Location destination) {
        if (vehicle.flyTo(destination)) {
            showMessage("Traveled to " + destination.getName(), Color.GREEN);
            updateDisplay();
        }
    }

    private void handleRefuel() {
        if (vehicle.refuel()) {
            showMessage("Tank refueled!", Color.GREEN);
            updateDisplay();
        }
    }

    private void handleNewGame() {
        App.showConfigScene();
    }

    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setTextFill(color);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}