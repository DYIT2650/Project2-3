package edu.tric.danielyoo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class VehicleConfigScene extends VBox {

    private final LocationManager locationManager;
    private TextField fuelField;
    private TextField mpgField;
    private Label errorLabel;
    private List<Location> locations;

    public VehicleConfigScene() {
        super(15); // spacing between elements
        this.locationManager = App.getLocationManager();
        this.locations = locationManager.getLocations();

        // Configure this container
        setAlignment(Pos.CENTER);
        setPadding(new Insets(30));
        setStyle("-fx-background-color: #f0f0f0;");

        // Initialize UI
        initializeUI();
    }

    private void initializeUI() {
        // Title
        Label titleLabel = new Label("Vehicle Configuration");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Starting location
        String startingLocationName = locations.isEmpty() ? "Unknown" : locations.get(0).getName();
        Label locationLabel = new Label("Starting Location: " + startingLocationName);

        // Fuel input section
        Label fuelLabel = new Label("Max Fuel (5-50 gallons):");
        fuelField = new TextField("30");
        fuelField.setMaxWidth(200);

        // MPG input section
        Label mpgLabel = new Label("Miles Per Gallon (10-100):");
        mpgField = new TextField("25");
        mpgField.setMaxWidth(200);

        // Error message label
        errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        // Start button
        Button startButton = createStartButton();

        // Add all components to the scene
        getChildren().addAll(
            titleLabel,
            locationLabel,
            fuelLabel,
            fuelField,
            mpgLabel,
            mpgField,
            errorLabel,
            startButton
        );
    }

    private Button createStartButton() {
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        startButton.setOnAction(e -> handleStartGame());
        return startButton;
    }

    private void handleStartGame() {
        try {
            double maxFuel = Double.parseDouble(fuelField.getText());
            double mpg = Double.parseDouble(mpgField.getText());

            // Validate inputs
            if (!validateInputs(maxFuel, mpg)) {
                return;
            }

            // Create vehicle and start game
            Vehicle vehicle = new Vehicle(locations.get(0), maxFuel, mpg);
            App.showGameScene(vehicle);

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers!");
        }
    }

    private boolean validateInputs(double maxFuel, double mpg) {
        if (maxFuel < 5 || maxFuel > 50) {
            showError("Fuel must be between 5 and 50 gallons!");
            return false;
        }

        if (mpg < 10 || mpg > 100) {
            showError("MPG must be between 10 and 100!");
            return false;
        }

        if (locations.isEmpty()) {
            showError("No locations available!");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void clearError() {
        errorLabel.setText("");
    }

    public double getFuelValue() {
        try {
            return Double.parseDouble(fuelField.getText());
        } catch (NumberFormatException e) {
            return 30.0; // default
        }
    }

    public double getMpgValue() {
        try {
            return Double.parseDouble(mpgField.getText());
        } catch (NumberFormatException e) {
            return 25.0; // default
        }
    }
}