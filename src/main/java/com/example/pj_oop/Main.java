package com.example.pj_oop;

import com.example.pj_oop.PageControllers.ClientControll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneControll sceneControll=new SceneControll(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BeginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        ClientControll controll=fxmlLoader.getController();
        controll.setSceneControll(sceneControll);
        stage.setTitle("推箱子");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}