package com.example.pj_oop;

import com.example.pj_oop.Entity.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Game {
    private ArrayList<Element[]> map=new ArrayList<>();
    private int scenelength;
    public Game(int scenelength){
        this.scenelength=scenelength;
    }
    private static Player player;
    private static ArrayList<Box> Boxes =new ArrayList<Box>();
    private static int ImgWidth;
    public static int getImgWidth() {
        return ImgWidth;
    }

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
    private static HashMap<Integer, String> OrderToPath =new HashMap<>(){
        {
            put(1,"src/main/resources/com/example/pj_oop/savespace/map/map.txt");

            put(2,"src/main/resources/com/example/pj_oop/savespace/map/map.txt");
            put(3,"src/main/resources/com/example/pj_oop/savespace/map/map.txt");

        }
    };
    private static HashMap<Character, Function<EntityIcons,Element>> CharToConstruct =new HashMap<>(){
        {
            put('0', Empty::new);
            put('1', Wall::new);
            put('2',Empty::new);
            put('5',Empty::new);
            put('4',finalPoint::new);
            put('3',Gap::new);
        }
    };

    public static ArrayList<Box> getBoxes() {
        return Boxes;
    }

    private static HashMap<Character, EntityIcons> CharToEntityIcon =new HashMap<>(){
        {
            put('0',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100));
            put('5',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100) );
            put('4',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/final.png",100,100));
            put('1',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/wall.png",100,100));
            put('2',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100));
            put('3',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/Gap.png",100,100));
        }
    };
    public  void readMap(Integer order,Integer toolCount){
        map=new ArrayList<>();
        Boxes=new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(OrderToPath.get(order)));
            String str;
            int y_axis = 0;
            int max_x_axis=0;
            while ((str = in.readLine()) != null) {
                String[] row = str.split(" ");
                Element[] elements = new Element[row.length];
                int x_axis ;
                for (x_axis=0; x_axis < row.length; x_axis++) {
                    if (row[x_axis].charAt(0)=='2')
                        player=new Player(x_axis,y_axis,toolCount,new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/character.png",100,100));
                    if (row[x_axis].charAt(0)=='5')
                        Boxes.add(new Box(x_axis,y_axis,new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/box.png",100,100)));
                    EntityIcons EntityIconsOfChar=CharToEntityIcon.get(row[x_axis].charAt(0));
                    elements[x_axis]=CharToConstruct.get(row[x_axis].charAt(0)).apply(EntityIconsOfChar);
                }
                if (x_axis>max_x_axis) max_x_axis=x_axis;
                map.add(elements);
                y_axis++;
            }
            ImgWidth=(scenelength/y_axis)>(scenelength/max_x_axis)?(scenelength/max_x_axis):(scenelength/y_axis);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public  void drawMap(GraphicsContext gc) {
        gc.clearRect(0,0,500,500);
        for(int y=0;y<map.size();y++){
            for (int x=0;x<map.get(y).length;x++){
                gc.drawImage(map.get(y)[x].getEntityIcons().getImage(), ImgWidth*x,ImgWidth*y,ImgWidth,ImgWidth);
            }
        }
        for (Box e:
             Boxes) {
            gc.drawImage(e.getEntityIcons().getImage(), e.getPos_x()*ImgWidth, e.getPos_y()*ImgWidth,ImgWidth,ImgWidth);
        }
            gc.drawImage(player.getEntityIcons().getImage(), player.getPos_x()*ImgWidth, player.getPos_y()*ImgWidth,ImgWidth,ImgWidth);
    }

    public void Loadfrom(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String input=in.readLine();
        String[] imfor=input.split(" ");
        int Order = Integer.parseInt(imfor[0]);
        this.readMap(Order,Integer.parseInt(imfor[1]));
        player.setPos_x(Integer.parseInt(imfor[1]));
        player.setPos_y(Integer.parseInt(imfor[2]));

    }


    public Boolean Conmandcontroll(KeyEvent keyEvent, GraphicsContext gc){
        if (keyEvent.getCode()==KeyCode.Q)
            return true;
        else if(keyEvent.getCode()==KeyCode.H){
            player.settool();
            return false;
        }
        else return Move(keyEvent,gc);
    }

    public static Player getPlayer() {
        return player;
    }

    public boolean Move(KeyEvent keyEvent, GraphicsContext gc) {
        Integer[] bias=keyCodeHashMap.get(keyEvent.getCode());
        if (player.passby(map, Boxes, bias)) {
                player.setPos_x(player.getPos_x() + bias[0]);
                player.setPos_y(player.getPos_y() + bias[1]);
                drawMap(gc);
        }

        for (Box e : Boxes
        ) {
                if (!e.isInfinal()) return false;
        }
        return true;
        }


}
