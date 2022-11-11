package com.example.variant_diagram;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Автор Евкина
 */
public class HelloApplication extends Application {
    /**
     * @param stage сцена
     * @throws IOException исключение
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 720);
        stage.setTitle("Конструктор диаграмм вариантов использования");

        HelloController helloController = fxmlLoader.getController();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case LEFT:
                        helloController.previousButton_Click();
                        break;
                    case RIGHT:
                        helloController.nextButton_Click();
                        break;
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args из консоли
     */
    public static void main(String[] args) {
        launch();
    }
}