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

import java.io.*;
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
    private Game game1=new Game(330);
    private Game game2=new Game(330);
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
            nowgame.IsQuit=true;
            gameArrayList.remove(nowindex);
            graphicsContextArrayListArrayList.remove(nowindex);
            nowindex=0;
        }
        else if(keyEvent.getCode()==KeyCode.H) {
            nowgame.getPlayer().settool();
        }

        else {
            nowgame.Move(keyCodeHashMap.get(keyEvent.getCode()));
            if (nowgame.isOver()){
                gameArrayList.remove(nowindex);
                graphicsContextArrayListArrayList.remove(nowindex);
                nowindex=0;
            }
        }

        drawMap(nowgc,nowgame);

        if (gameArrayList.isEmpty()){
            ButtonType YesButton =new ButtonType("continue");
            ButtonType NotButton=     new ButtonType("return");
            Alert Continue =new Alert(Alert.AlertType.INFORMATION,winner(),YesButton,NotButton);
            Continue.setTitle("Continue");
            Optional<ButtonType> optionalButtonType=Continue.showAndWait();
            if(optionalButtonType.get()==NotButton){
                super.sceneControll.switchScene("BeginPage.fxml");
            }
            if (optionalButtonType.get()==YesButton){
                PageController controller= super.sceneControll.switchScene("doubleselect.fxml");
            }
        }



    }


    public String winner() {
        if (game1.IsQuit&&game2.IsQuit) return "draw";
        else if(game1.IsQuit&&!game2.IsQuit) return game2.playername+" win;";
        else if(!game1.IsQuit&&game2.IsQuit) return game1.playername+" win;";
        else {
            String result=(game1.getfinalcount()>game2.getfinalcount()? game2.playername+" win\n" : game1.playername+" win\n")+
                    game1.playername+" stepcount is" +game1.getfinalcount()+"\n"+
                    game2.playername+" stepcount is" +game2.getfinalcount();
            return result;
        }
    }

    public void StarNewGame(Integer order, Integer toolCount, String playername1, String playername2) {
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
    public void initialize() {

        gc1 = FirstCanvas.getGraphicsContext2D();
        gc2 = SecondCanvas.getGraphicsContext2D();
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
    @FXML
    private  void handleRestartButtonClick(){
        StarNewGame(noworder,nowtoolcount,game1.playername, game2.playername);
    }
    @FXML
    private void handleReturnButtonClick() throws IOException {
        super.sceneControll.switchScene("BeginPage.fxml");
    }
    @FXML
    public void handleSaveButtonClick(){
        String filePath = "src/main/resources/com/example/pj_oop/savespace/file/doublelasttime.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(game1.playername+'\n');
                writer.write(noworder+" "+nowtoolcount+" "+game1.toString()+" "+"\n");
                writer.write(game1.getPlayer().toString()+'\n');
                for (Box e:
                        game1.getBoxes()) {
                    writer.write(e.toString()+'\n');

                }
                writer.write("END" +'\n');
                writer.write(game2.playername+'\n');
                writer.write(noworder+" "+nowtoolcount+" "+game2.toString()+" "+"\n");
                writer.write(game2.getPlayer().toString()+'\n');
                for (Box e:
                        game2.getBoxes()) {
                    writer.write(e.toString()+'\n');

                }
                writer.write("END" +'\n');
            } catch (IOException e) {
                e.printStackTrace();
        }


    }
    @FXML
    public void handlechangebutton(){
        nowindex=(nowindex+1)%gameArrayList.size();
    }

    public void Load() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("src/main/resources/com/example/pj_oop/savespace/file/doublelasttime.txt"));
        int[] result={1,1};
        gameArrayList.add(game1);
        gameArrayList.add(game2);
        for (Game a:
             gameArrayList) {
            result= a.Loadfrom(in);
        }
        noworder=result[0];
        nowtoolcount=result[1];
        drawMap(gc1,game1);
        drawMap(gc2,game2);
    }

}
