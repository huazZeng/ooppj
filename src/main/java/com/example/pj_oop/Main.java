package com.example.pj_oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BeginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        ClientControll controll=fxmlLoader.getController();
        controll.setStage(stage);
        stage.setTitle("推箱子");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}