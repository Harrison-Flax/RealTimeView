package com.realtimeview.app.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// This is the JavaFX entry point
// For setting up the frontend and starting it
public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/realtimeview/app/frontend/dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1200, 800);

        // Will use CSS with JavaFX
        // Will be implemented later on and just leaving placeholder for now
        // scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("RealTimeView");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
