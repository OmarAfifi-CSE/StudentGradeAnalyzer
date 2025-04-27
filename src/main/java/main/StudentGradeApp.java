package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader; // Import FXMLLoader
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.MainController; // Import the controller

import java.io.IOException; // May be needed for getResource
import java.io.InputStream;
import java.util.Objects;   // For null checks

public class StudentGradeApp extends Application {

    // Declare a variable to hold the controller instance
    private MainController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/view/MainView.fxml"), "Cannot find FXML file"));
        Parent root = loader.load();

        controller = loader.getController();
        if (controller == null) {
            throw new IllegalStateException("Controller instance is null. Check fx:controller in FXML.");
        }

        Scene scene = new Scene(root, 900, 700);

        // --- Set Application Icons ---
        try {
            InputStream iconStream256 = getClass().getResourceAsStream("/icons/icon256.png");
            if (iconStream256 != null) {
                primaryStage.getIcons().add(new Image(iconStream256));
            } else {
                System.err.println("Warning: Could not load icon256.png");
            }
        } catch (Exception e) {
            System.err.println("Error loading application icon: " + e.getMessage());
            e.printStackTrace();
        }
        // --- End Icon Setting ---

        primaryStage.setTitle("Student Grade Analyzer");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Window close request received.");
        });
        primaryStage.show();
    }

    /**
     * This method is called when the application should stop, and is the
     * ideal place to perform cleanup operations like saving data.
     */
    @Override
    public void stop() throws Exception {
        System.out.println("Application stop() method called - attempting to save data."); // Debug log
        if (controller != null) {
            controller.saveData(); // Call the public save method in the controller
        } else {
            // This shouldn't happen if start() completed successfully
            System.err.println("Error: Controller instance was null during stop(). Data cannot be saved.");
        }
        super.stop(); // Important to call the superclass method
    }


    public static void main(String[] args) {
        launch(args);
    }
}