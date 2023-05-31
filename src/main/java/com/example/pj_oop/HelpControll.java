package com.example.pj_oop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpControll {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
@FXML
    private void handleReturnButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("BeginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        ClientControll controll=fxmlLoader.getController();
        controll.setStage(stage);
    }
}
