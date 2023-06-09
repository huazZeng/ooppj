package com.example.pj_oop.PageControllers;

import com.example.pj_oop.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.util.Optional;

public class ClientControll extends PageController{


    @FXML
    private void handleBeginButtonClick() throws IOException {
        ButtonType YesButton =new ButtonType("Y");
        ButtonType NotButton=     new ButtonType("N");
        Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue?",YesButton,NotButton);
        Continue.setTitle("Continue");
        Optional<ButtonType> optionalButtonType=Continue.showAndWait();
        if(optionalButtonType.get()==NotButton){
          super.sceneControll.switchScene("SelectPage.fxml");
        }
        if (optionalButtonType.get()==YesButton){
            PageController controller= super.sceneControll.switchScene("GamePage.fxml");
            SingleGamePageController gamePageController= (SingleGamePageController) controller;
            gamePageController.Load();


        }
    }
    @FXML
    private void handleReadButtonClick() throws IOException {
        super.sceneControll.switchScene("HelpPage.fxml");
    }
    @FXML
    private void handleDoubleButtonClick() throws IOException {
        ButtonType YesButton =new ButtonType("Y");
        ButtonType NotButton=     new ButtonType("N");
        Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue?",YesButton,NotButton);
        Continue.setTitle("Continue");
        Optional<ButtonType> optionalButtonType=Continue.showAndWait();
        if(optionalButtonType.get()==NotButton){
            super.sceneControll.switchScene("DoubleGamePage.fxml");
        }
        if (optionalButtonType.get()==YesButton){
            PageController controller= super.sceneControll.switchScene("DoubleGamePage.fxml");
            SingleGamePageController gamePageController= (SingleGamePageController) controller;
            gamePageController.Load();


        }
    }
    @FXML
    private void handleHelpButtonClick() throws IOException {
        super.sceneControll.switchScene("HelpPage.fxml");
    }

}
