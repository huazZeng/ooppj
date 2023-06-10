package com.example.pj_oop.PageControllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.HashMap;

public class DoubleSelectController extends PageController {
    @FXML
    public ComboBox<String> difficultySelectionComboBox;
    @FXML
    private TextField playername1;
    @FXML
    private TextField playername2;
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

    @FXML
    private void handleSubmitButtonClick() throws IOException {
        PageController controller= sceneControll.switchScene("DoubleGamePage.fxml");

        ((DoubleGameController)controller).StarNewGame(StringToMap.get(orderSelectionComboBox.getValue())
                ,StringToCount.get(difficultySelectionComboBox.getValue()),playername1.getText(),playername2.getText());



    }
}
