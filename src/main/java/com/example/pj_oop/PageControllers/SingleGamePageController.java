package com.example.pj_oop.PageControllers;

import com.example.pj_oop.Entity.Box;
import com.example.pj_oop.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;


public class SingleGamePageController extends PageController {
    @FXML
    private Canvas StaticCanvas;
    @FXML
    private GraphicsContext gc;
    @FXML
    private BorderPane container;



    private Game _GameController=new Game(500);





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
        this._GameController.Loadfrom("src/main/resources/com/example/pj_oop/savespace/file/singlelasttime.txt");

        _GameController.drawMap(gc);
    }
@FXML
    public void Move(KeyEvent keyEvent) {
        boolean over=false;


        over=_GameController.Conmandcontroll(keyEvent,gc);
        if (over){
            container.removeEventHandler(KeyEvent.KEY_PRESSED,this::Move);
            Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue?",new ButtonType("Y"),new ButtonType("N"));
            Optional<ButtonType> optionalButtonType=Continue.showAndWait();

        }
    }

    @FXML
    public void handleSaveButtonClick(){
        String filePath = "src/main/resources/com/example/pj_oop/savespace/file/singlelasttime.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(Game.getPlayer().toString()+'\n');
            for (Box e:
                 Game.getBoxes()) {
                writer.write(e.toString()+'\n');

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
