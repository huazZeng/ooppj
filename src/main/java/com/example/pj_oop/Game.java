package com.example.pj_oop;

import com.example.pj_oop.Entity.*;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Game {
    private static List<Element[]> map;

    private static Player player;
    private static ArrayList<Box> Boxes =new ArrayList<Box>();
    private static Double ImgWidth;

    public static Double getImgWidth() {
        return ImgWidth;
    }
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
        }
    };



    private static HashMap<Character, EntityIcons> CharToEntityIcon =new HashMap<>(){
        {
            put('0',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100));
            put('5',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100) );
            put('4',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/background.png",100,100));
            put('1',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/wall.png",100,100));
            put('2',new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/empty.png",100,100));

        }
    };
    public  void readMap(Integer order,Integer toolCount){
        map = new ArrayList<>();
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
                        player=new Player(x_axis,y_axis,new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/character.png",100,100));
                    if (row[x_axis].charAt(0)=='5')
                        Boxes.add(new Box(x_axis,y_axis,new EntityIcons("src/main/resources/com/example/pj_oop/savespace/img/character.png",100,100)));
                    EntityIcons EntityIconsOfChar=CharToEntityIcon.get(row[x_axis].charAt(0));
                    elements[x_axis]=CharToConstruct.get(row[x_axis].charAt(0)).apply(EntityIconsOfChar);
                }
                if (x_axis>max_x_axis) max_x_axis=x_axis;
                map.add(elements);
                y_axis++;
            }
            ImgWidth=(500.0/y_axis)>(500.0/max_x_axis)?(500.0/max_x_axis):(500.0/y_axis);
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


    public boolean Move(Integer[] bias ,GraphicsContext gc) {
        if(player.passby(map,Boxes,bias)){
            player.setPos_x(player.getPos_x()+bias[0]);
            player.setPos_y(player.getPos_y()+bias[1]);
            drawMap(gc);
        }
        for (Box e:Boxes
             ) {
            if (!e.isInfinal()) return false;
        }
        return true;
    }
}
