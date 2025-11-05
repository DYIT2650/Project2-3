package edu.tric.danielyoo;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class AddLocationScene extends VBox {
    private TextField nameField;
    private TextField xField;
    private TextField yField;
    private CheckBox hasGasStationCheckbox;
    private Label errorLabel;
    private LocationManager locationManager;
    private Vehicle currentVehicle;

    public AddLocationScene(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
        this.locationManager = App.getLocationManager();
        initializeUI();
    }

    private void initializeUI() {
        this.setPadding(new Insets(30));
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(400);

        Label titleLabel = new Label("Add New Location");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        errorLabel.setVisible(false);

        VBox nameContainer = new VBox(5);
        Label nameLabel = new Label("Location Name:");
        nameLabel.setStyle("-fx-font-size: 14px;");
        nameField = new TextField();
        nameField.setPromptText("Enter location name");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 8px;");
        nameContainer.getChildren().addAll(nameLabel, nameField);

        VBox xContainer = new VBox(5);
        Label xLabel = new Label("X Coordinate:");
        xLabel.setStyle("-fx-font-size: 14px;");
        xField = new TextField();
        xField.setPromptText("Enter X coordinate (e.g., 100)");
        xField.setStyle("-fx-font-size: 14px; -fx-padding: 8px;");
        xContainer.getChildren().addAll(xLabel, xField);

        VBox yContainer = new VBox(5);
        Label yLabel = new Label("Y Coordinate:");
        yLabel.setStyle("-fx-font-size: 14px;");
        yField = new TextField();
        yField.setPromptText("Enter Y coordinate (e.g., -50)");
        yField.setStyle("-fx-font-size: 14px; -fx-padding: 8px;");
        yContainer.getChildren().addAll(yLabel, yField);

        hasGasStationCheckbox = new CheckBox("Has Gas Station");
        hasGasStationCheckbox.setStyle("-fx-font-size: 14px; -fx-padding: 10px 0;");
        hasGasStationCheckbox.setSelected(false);

        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));

        Button saveButton = new Button("Save Location");
        saveButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; " +
                          "-fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setOnAction(e -> handleSubmit());

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        cancelButton.setOnAction(e -> handleCancel());

        buttonContainer.getChildren().addAll(saveButton, cancelButton);

        this.getChildren().addAll(
            titleLabel,
            errorLabel,
            nameContainer,
            xContainer,
            yContainer,
            hasGasStationCheckbox,
            buttonContainer
        );
    }

    private void handleSubmit() {
        errorLabel.setVisible(false);

        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            showError("Please enter a location name.");
            return;
        }

        double x, y;
        try {
            x = Double.parseDouble(xField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for X coordinate.");
            return;
        }

        try {
            y = Double.parseDouble(yField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for Y coordinate.");
            return;
        }

        Vector2 position = new Vector2(x, y);
        Location newLocation = new Location(name, position, hasGasStationCheckbox.isSelected());

        boolean success = locationManager.addLocation(newLocation);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Location Added");
            alert.setHeaderText(null);
            alert.setContentText("Location '" + name + "' has been added successfully.");
            alert.showAndWait();

            App.showLocationManagementScene(currentVehicle);
        } else {
            showError("A location with this name already exists. Please choose a different name.");
        }
    }

    private void handleCancel() {
        App.showLocationManagementScene(currentVehicle);
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}