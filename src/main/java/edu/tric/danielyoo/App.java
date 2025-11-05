package edu.tric.danielyoo;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App - Vehicle Travel Game
 */
public class App extends Application {

    private static Scene scene;
    private static LocationManager locationManager;

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the LocationManager
        locationManager = new LocationManager("/edu/tric/danielyoo/locations.json");

        // Create the main scene with initial configuration
        scene = new Scene(new VehicleConfigScene(), 850, 650);

        // Set up the primary stage
        stage.setTitle("Vehicle Travel Game");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    public static void showGameScene(Vehicle vehicle) {
        setRoot(new GameScene(vehicle));
    }

    public static void showConfigScene() {
        setRoot(new VehicleConfigScene());
    }

    // We need to expose these top level methods for each scene to consume; we'll have buttons bind to the App definition methods that can read each scene. Maybe theres a ebtter way but this works
    public static void showLocationManagementScene(Vehicle vehicle) {
        setRoot(new LocationManagementScene(vehicle));
    }

    public static void showLocationManagementScene() {
        setRoot(new LocationManagementScene(null));
    }

    public static void showAddLocationScene() {
        setRoot(new AddLocationScene(null));
    }

    public static void showAddLocationScene(Vehicle vehicle) {
        setRoot(new AddLocationScene(vehicle));
    }

    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void main(String[] args) {
        launch();
    }

}