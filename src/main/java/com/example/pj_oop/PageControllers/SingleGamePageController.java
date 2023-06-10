package com.example.pj_oop.PageControllers;

import com.example.pj_oop.Entity.*;
import com.example.pj_oop.Game;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.util.*;


public class SingleGamePageController extends PageController {
    @FXML
    private Canvas StaticCanvas;
    @FXML
    private GraphicsContext gc;
    @FXML
    private BorderPane container;
    @FXML
    private Label game_data;
    private Game _GameController=new Game(500);
    private StringProperty stringProperty ;

    private Integer noworder;
    private Integer nowtoolcount;
    private ArrayList<KeyCode> AccessableKeyborad=new ArrayList<>(Arrays.asList(KeyCode.A,KeyCode.W,KeyCode.S,KeyCode.D,KeyCode.H,KeyCode.Q));
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



    private void thekeyboardfilter(KeyEvent E) throws IOException{
        if (AccessableKeyborad.contains(E.getCode())){
            KeyBoardHandler(E);
        }
    }



    public void initialize(){

        gc = StaticCanvas.getGraphicsContext2D();
        this.container.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            try {
                thekeyboardfilter(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }



    public void StarNewGame(Integer order,Integer toolCount,String playername) {
        _GameController=new Game(500);
        noworder=order;
        nowtoolcount=toolCount;
        _GameController.readMap(order, toolCount,playername);
        drawMap(gc);
        stringProperty=new SimpleStringProperty("Playername:"+_GameController.playername+"\n"+"toolcount:"
                +_GameController.player.getToolcount()+"\nstepcount:"+_GameController.getValidtimecount());
        game_data.textProperty().bind(stringProperty);
    }

    public  void drawMap(GraphicsContext gc) {
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




@FXML
    private void handleReturnButtonClick() throws IOException {
        super.sceneControll.switchScene("BeginPage.fxml");
    }
    @FXML
    private  void handleRestartButtonClick(){
        StarNewGame(noworder,nowtoolcount,_GameController.playername);
    }

    public void Load() throws IOException {
        int[] result=this._GameController.Loadfrom(new BufferedReader(new FileReader("src/main/resources/com/example/pj_oop/savespace/file/singlelasttime.txt")));
        stringProperty=new SimpleStringProperty("Playername:"+_GameController.playername+"\n"+"toolcount:"
                +_GameController.player.getToolcount()+"\nstepcount:"+_GameController.getValidtimecount()) ;
        game_data.textProperty().bind(stringProperty);
        nowtoolcount=result[1];
        noworder=result[0];
        drawMap(gc);

    }
@FXML
    public void KeyBoardHandler(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode()==KeyCode.Q){
            _GameController.setOver(true);
            _GameController.IsQuit=true;
        }
        else if(keyEvent.getCode()==KeyCode.H) {
            _GameController.getPlayer().settool();
        }
        else _GameController.Move(keyCodeHashMap.get(keyEvent.getCode()));
        stringProperty.setValue("Playername:"+_GameController.playername+"\n"+"toolcount:"
                +_GameController.player.getToolcount()+"\nstepcount:"+_GameController.getValidtimecount()) ;
        drawMap(gc);
        if (_GameController.isOver()){
            ButtonType YesButton =new ButtonType("Y");
            ButtonType NotButton=     new ButtonType("N");
            Alert Continue =new Alert(Alert.AlertType.INFORMATION,"continue",YesButton,NotButton);
            Continue.setTitle("Continue");
            Optional<ButtonType> optionalButtonType=Continue.showAndWait();
            if(optionalButtonType.get()==NotButton){
                super.sceneControll.switchScene("BeginPage.fxml");
            }
            if (optionalButtonType.get()==YesButton){
                PageController controller= super.sceneControll.switchScene("SelectPage.fxml");


            }
        }



    }

    @FXML
    public void handleSaveButtonClick(){
        String filePath = "src/main/resources/com/example/pj_oop/savespace/file/singlelasttime.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(_GameController.playername+'\n');
            writer.write(noworder+" "+nowtoolcount+" "+_GameController.toString()+" "+"\n");
            writer.write(_GameController.getPlayer().toString()+'\n');
            for (Box e:
                 _GameController.getBoxes()) {
                writer.write(e.toString()+'\n');

            }
            writer.write("END\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
