package com.example.pj_oop.PageControllers;

import com.example.pj_oop.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;


public class SingleGamePageController extends PageController {
    @FXML
    private Canvas StaticCanvas;
    @FXML
    private GraphicsContext gc;




    private Game _GameController=new Game();
    private static HashMap<KeyCode, Integer[]> keyCodeHashMap =new HashMap<>(){
        {
            put(KeyCode.W,new Integer[]{0,-1});
            put(KeyCode.S,new Integer[]{0,1});
            put(KeyCode.A,new Integer[]{-1,0});
            put(KeyCode.D,new Integer[]{1,0});
        }
    };




    public void initialize() {
        gc = StaticCanvas.getGraphicsContext2D();
    }



    public void StarNewGame(Integer order,Integer toolCount) {
        _GameController.readMap(order, toolCount);
        _GameController.drawMap(gc);
    }




@FXML
    private void handleReturnButtonClick() throws IOException {
        super.sceneControll.switchScene("BeginPage.fxml");
    }
    @FXML
    private  void handleRestartButtonClick(){
        StarNewGame(1,1);
    }

    public void Load() throws IOException {
        this._GameController.Loadfrom("src/main/resources/com/example/pj_oop/savespace/file/lasttime.txt");

        _GameController.drawMap(gc);
    }
@FXML
    public void Move(KeyEvent keyEvent) {
        boolean over=false;
        Integer[] bias=keyCodeHashMap.get(keyEvent.getCode());
        if (bias!=null)
            over=_GameController.Move(bias,gc );
        if (over){
            Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue?",new ButtonType("Y"),new ButtonType("N"));
            Optional<ButtonType> optionalButtonType=Continue.showAndWait();
        }
    }
}