package com.example.pj_oop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ClientControll {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleBeginButtonClick() throws IOException {
        ButtonType YesButton =new ButtonType("Y");
        ButtonType NotButton=     new ButtonType("N");
        Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue?",YesButton,NotButton);
        Continue.setTitle("Continue");
        Optional<ButtonType> optionalButtonType=Continue.showAndWait();
        if(optionalButtonType.get()==NotButton){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SelectPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            SelectController controller=fxmlLoader.getController();
            stage.setScene(scene);
            controller.setStage(stage);
        }
        if (optionalButtonType.get()==YesButton){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GamePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            GamePageController controll=fxmlLoader.getController();
            controll.setStage(stage);
            controll.Load();
            stage.setScene(scene);



        }
    }
    @FXML
    private void handleReadButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        HelloController controll=fxmlLoader.getController();
        controll.setStage(stage);
    }
    @FXML
    private void handleHelpButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HelpPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        HelpControll controll=fxmlLoader.getController();
        controll.setStage(stage);
    }

}
