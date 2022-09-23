package com.example.variant_diagram;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author Evkina
 */
public class HelloApplication extends Application {
    /**
     * @param stage contains a scene
     * @throws IOException for working with files
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 720);
        stage.setTitle("Конструктор диаграмм вариантов использования");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args from console
     */
    public static void main(String[] args) {
        launch();
    }
}