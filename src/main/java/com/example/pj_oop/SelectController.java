package com.example.pj_oop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SelectController {
    private Stage stage;
    @FXML
    public ComboBox<String> difficultySelectionComboBox;

    @FXML
    public ComboBox<String> orderSelectionComboBox;

    private HashMap<String,Integer> StringToCount=new HashMap<>(){{
        put("简单",5);
        put("普通",2);
        put("困难",1);
    }};
    private HashMap<String,Integer> StringToMap=new HashMap<>(){{
        put("关卡1",1);
        put("关卡2",2);
        put("关卡3",3);
    }};
    public void setStage(Stage stage) {
        this.stage=stage;
    }

    @FXML
    private void handleSubmitButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GamePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        SingleGamePageController controll=fxmlLoader.getController();
        controll.StarNewGame(StringToMap.get(orderSelectionComboBox.getValue())
                ,StringToCount.get(difficultySelectionComboBox.getValue()));
        stage.setScene(scene);

        controll.setStage(stage);
    }



}
