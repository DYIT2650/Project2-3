package edu.tric.danielyoo;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Optional;

public class LocationManagementScene extends VBox {
    private LocationManager locationManager;
    private VBox locationsList;
    private Vehicle currentVehicle;

    public LocationManagementScene(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
        this.locationManager = App.getLocationManager();
        initializeUI();
    }

    private void initializeUI() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Location Management");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        locationsList = new VBox(10);
        locationsList.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(locationsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(400);
        scrollPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");

        refreshLocationsList();

        HBox bottomButtons = new HBox(20);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(15, 0, 0, 0));

        Button addLocationButton = new Button("Add New Location");
        addLocationButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        addLocationButton.setOnAction(e -> handleAddLocation());

        Button backButton = new Button("Back to Game");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        backButton.setOnAction(e -> handleBack());

        bottomButtons.getChildren().addAll(addLocationButton, backButton);

        this.getChildren().addAll(titleLabel, scrollPane, bottomButtons);
    }

    private void refreshLocationsList() {
        locationsList.getChildren().clear();

        for (Location location : locationManager.getLocations()) {
            HBox locationRow = new HBox(10);
            locationRow.setAlignment(Pos.CENTER_LEFT);
            locationRow.setPadding(new Insets(8));
            locationRow.setStyle("-fx-background-color: white; -fx-border-color: #dddddd; " +
                                "-fx-border-radius: 5px; -fx-background-radius: 5px;");

            VBox locationInfo = new VBox(3);
            Label nameLabel = new Label(location.getName());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label positionLabel = new Label(String.format("Position: (%.1f, %.1f)",
                location.getPosition().x, location.getPosition().y));
            positionLabel.setStyle("-fx-font-size: 12px;");

            Label gasStationLabel = new Label("Gas Station: " +
                (location.hasGasStation() ? "Yes" : "No"));
            gasStationLabel.setStyle("-fx-font-size: 12px;");

            locationInfo.getChildren().addAll(nameLabel, positionLabel, gasStationLabel);

            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; " +
                                "-fx-padding: 5px 15px;");

            if (currentVehicle != null &&
                currentVehicle.getLocation().getName().equals(location.getName())) {
                deleteButton.setDisable(true);
                deleteButton.setText("Current Location");
                deleteButton.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; " +
                                    "-fx-padding: 5px 15px;");
            } else {
                deleteButton.setOnAction(e -> handleDeleteLocation(location));
            }

            HBox.setHgrow(locationInfo, javafx.scene.layout.Priority.ALWAYS);
            locationRow.getChildren().addAll(locationInfo, deleteButton);

            locationsList.getChildren().add(locationRow);
        }
    }

    private void handleAddLocation() {
        App.showAddLocationScene(currentVehicle);
    }

    private void handleDeleteLocation(Location location) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Location");
        alert.setHeaderText("Delete " + location.getName() + "?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = locationManager.deleteLocation(location.getName());

            if (success) {
                refreshLocationsList();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Location Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText(location.getName() + " has been deleted.");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Could not delete location");
                errorAlert.setContentText("At least 2 locations are required for the game to function.");
                errorAlert.showAndWait();
            }
        }
    }

    private void handleBack() {
        App.showGameScene(currentVehicle);
    }
}