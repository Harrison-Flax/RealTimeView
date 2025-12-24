package com.RealTimeView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ApplicationEvent;

@SpringBootApplication
// Extends JavaFX Application class
public class RealTimeViewApp extends Application {

    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(RealTimeViewApp.class, args);
    }

    @Override
    public void init() {
        // Initialize Spring application context
        springContext = new SpringApplicationBuilder(RealTimeViewApp.class).run();
    }

    @Override
    public void start(Stage stage) {
        // Will be read in by the StageInitializer to show the GUI
        springContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
}