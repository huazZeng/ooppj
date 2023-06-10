package com.example.pj_oop.PageControllers;

import com.example.pj_oop.Entity.Box;
import com.example.pj_oop.Entity.EntityIcons;
import com.example.pj_oop.Entity.MapElement;
import com.example.pj_oop.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class DoubleGameController extends PageController {
    @FXML
    private Canvas FirstCanvas;
    @FXML
    private BorderPane container;
    @FXML
    private Canvas SecondCanvas;
    private GraphicsContext gc1;
    private GraphicsContext gc2;
    private Integer noworder;
    private Integer nowtoolcount;
    private int nowindex=0;
    private Game game1;
    private Game game2;
    private ArrayList<Game> gameArrayList=new ArrayList<>();
    private ArrayList<GraphicsContext> graphicsContextArrayListArrayList=new ArrayList<>();
    private static HashMap<KeyCode, Integer[]> keyCodeHashMap =new HashMap<>(){
        {
            put(KeyCode.W,new Integer[]{0,-1});
            put(KeyCode.S,new Integer[]{0,1});
            put(KeyCode.A,new Integer[]{-1,0});
            put(KeyCode.D,new Integer[]{1,0});
            put(KeyCode.H,new Integer[]{0});
            put(KeyCode.Q,new Integer[]{});
        }
    };
    private static HashMap<Character, EntityIcons> CharToEntityIcon =new HashMap<>(){
        {
            put('0',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100));
            put('3',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/final.png",100,100));
            put('1',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/wall.png",100,100));

            put('4',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/Gap.png",100,100));
        }
    };
    private ArrayList<KeyCode> AccessableKeyborad=new ArrayList<>(Arrays.asList(KeyCode.A,KeyCode.W,KeyCode.S,KeyCode.D,KeyCode.H,KeyCode.Q));
    public  void drawMap(GraphicsContext gc,Game _GameController) {
        ArrayList<MapElement[]> map =_GameController.getMap();
        gc.clearRect(0,0,500,500);
        for(int y=0;y<map.size();y++){
            for (int x=0;x<map.get(y).length;x++){

                gc.drawImage(CharToEntityIcon.get(map.get(y)[x].classify).getImage(),x*_GameController.getImgWidth(),
                        y*_GameController.getImgWidth(),_GameController.getImgWidth(),_GameController.getImgWidth());
            }
        }
        for (Box e:
                _GameController.getBoxes()) {
            gc.drawImage(new Image("file:src/main/resources/com/example/pj_oop/savespace/img/box.png"), e.getPos_x()*_GameController.getImgWidth(), e.getPos_y()*_GameController.getImgWidth(),_GameController.getImgWidth(),_GameController.getImgWidth());
        }
        gc.drawImage(new Image("file:src/main/resources/com/example/pj_oop/savespace/img/character.png"), _GameController.player.getPos_x()*_GameController.getImgWidth(), _GameController.player.getPos_y()*_GameController.getImgWidth(),_GameController.getImgWidth(),_GameController.getImgWidth());
    }


    private void thekeyboardfilter(KeyEvent E) throws IOException{
        if (AccessableKeyborad.contains(E.getCode())){
            KeyBoardHandler(E);
        }
    }
    public void KeyBoardHandler(KeyEvent keyEvent) throws IOException {
        Game nowgame=gameArrayList.get(nowindex);
        GraphicsContext nowgc=graphicsContextArrayListArrayList.get(nowindex);
        if (keyEvent.getCode()==KeyCode.Q){
            nowgame.setOver(true);
            gameArrayList.remove(nowindex);
            nowindex=(nowindex+1)%(gameArrayList.size());
        }
        else if(keyEvent.getCode()==KeyCode.H) {
            nowgame.getPlayer().settool();
        }
        else {
            nowgame.Move(keyCodeHashMap.get(keyEvent.getCode()));
            nowindex=(nowindex+1)%(gameArrayList.size());
            if (nowgame.isOver()){
                gameArrayList.remove(nowindex);
            }

        }
        drawMap(nowgc,nowgame);

        if (gameArrayList.isEmpty()){
            ButtonType YesButton =new ButtonType("Y");
            ButtonType NotButton=     new ButtonType("N");
            Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue",YesButton,NotButton);
            Continue.setTitle("Continue");
            Optional<ButtonType> optionalButtonType=Continue.showAndWait();
            if(optionalButtonType.get()==NotButton){
                super.sceneControll.switchScene("BeginGamePage.fxml");
            }
            if (optionalButtonType.get()==YesButton){
                PageController controller= super.sceneControll.switchScene("SelectPage.fxml");
            }
        }



    }
    public void StarNewGame(Integer order,Integer toolCount,String playername1,String playername2) {
        game1=new Game(330);
        game2=new Game(330);
        gameArrayList.add(game1);
        gameArrayList.add(game2);
        noworder = order;
        nowtoolcount=toolCount;
        game1.readMap(order, toolCount,playername1);
        game2.readMap(order, toolCount,playername2);
        drawMap(gc1,game1);
        drawMap(gc2,game2);

    }
    @FXML
    public void initialize(){

        gc1= FirstCanvas.getGraphicsContext2D();
        gc2=SecondCanvas.getGraphicsContext2D();
        graphicsContextArrayListArrayList.add(gc1);
        graphicsContextArrayListArrayList.add(gc2);
        this.container.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            try {
                thekeyboardfilter(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


}
