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
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

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

    /**
     * Switch to a new scene by changing the root node
     */
    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    /**
     * Show the game scene with the configured vehicle
     */
    public static void showGameScene(Vehicle vehicle) {
        setRoot(new GameScene(vehicle));
    }

    /**
     * Show the configuration scene to start a new game
     */
    public static void showConfigScene() {
        setRoot(new VehicleConfigScene());
    }

    /**
     * Get the LocationManager instance
     */
    public static LocationManager getLocationManager() {
        return locationManager;
    }

    public static void main(String[] args) {
        launch();
    }

}