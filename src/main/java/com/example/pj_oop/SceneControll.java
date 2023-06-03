package com.example.pj_oop;

import com.example.pj_oop.PageControllers.PageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneControll {
    private Stage stage;
    public SceneControll(Stage stage){
        this.stage=stage;
    }
    public PageController switchScene(String fxmlfile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlfile));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        PageController controller=fxmlLoader.getController();
        controller.setSceneControll(this);
        return controller;
    }
}
