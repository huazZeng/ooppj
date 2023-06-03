package com.example.pj_oop.PageControllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpControll extends PageController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
@FXML
    private void handleReturnButtonClick() throws IOException {
        sceneControll.switchScene("BeginPage.fxml");
    }
}
