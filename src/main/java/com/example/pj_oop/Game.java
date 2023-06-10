package com.example.pj_oop;

import com.example.pj_oop.Entity.*;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Game {
    private int validtimecount=0;
    public boolean IsQuit=false;
    public String playername;
  

    private boolean over =false;



    private ArrayList<MapElement[]> map=new ArrayList<>();
    private int scenelength;

    public   Player player;
    private  ArrayList<Box> Boxes =new ArrayList<Box>();
    private static int ImgWidth;

    public int getValidtimecount() {
        return validtimecount;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
    public Game(int scenelength){
        this.scenelength=scenelength;
    }
    public static int getImgWidth() {
        return ImgWidth;
    }

    public ArrayList<MapElement[]> getMap() {
        return map;
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
    private static HashMap<Character, Function<Character,MapElement>> CharToConstruct =new HashMap<>(){
        {
            put('0', Empty::new);
            put('1', Wall::new);
            put('2',Empty::new);
            put('5',Empty::new);
            put('3',finalPoint::new);
            put('4',Gap::new);
        }
    };

    public  ArrayList<Box> getBoxes() {
        return Boxes;
    }


    public  void readMap(Integer order,Integer toolCount,String playername){
        this.playername=playername;
        map=new ArrayList<>();
        Boxes=new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(OrderToPath.get(order)));
            String str;
            int y_axis = 0;
            int max_x_axis=0;
            while ((str = in.readLine()) != null) {
                String[] row = str.split(" ");
                MapElement[] elements = new MapElement[row.length];
                int x_axis ;
                for (x_axis=0; x_axis < row.length; x_axis++) {
                    if (row[x_axis].charAt(0)=='2'){
                        player=new Player(x_axis,y_axis,toolCount);
                        elements[x_axis]=CharToConstruct.get(row[x_axis].charAt(0)).apply('0');
                    }
                    if (row[x_axis].charAt(0)=='5') {
                        Boxes.add(new Box(x_axis, y_axis));
                        elements[x_axis]=CharToConstruct.get(row[x_axis].charAt(0)).apply('0');
                    }
                    else elements[x_axis]=CharToConstruct.get(row[x_axis].charAt(0)).apply(row[x_axis].charAt(0));
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
    public String toString(){
        return  validtimecount+"";
    }
    public int[] Loadfrom(BufferedReader in) throws IOException {
        String input=in.readLine();
        playername=input;

        input=in.readLine();
        String[] imfor=input.split(" ");
        int Order = Integer.parseInt(imfor[0]);
        int toll = Integer.parseInt(imfor[1]);
        this.validtimecount=Integer.parseInt(imfor[2]);
        int [] result={Order,toll};
        input=in.readLine();
        imfor=input.split(" ");
        this.readMap(Order,Integer.parseInt(imfor[1]),playername);


        player.setPos_x(Integer.parseInt(imfor[0]));
        player.setPos_y(Integer.parseInt(imfor[1]));
        player.setToolcount(Integer.parseInt(imfor[3]));
        player.settimes(Integer.parseInt(imfor[4]),Integer.parseInt(imfor[5]));
        int i=0;
        while(!(input=in.readLine()).equals("END")){
            imfor=input.split(" ");
            Boxes.get(i).sestatus(Integer.parseInt(imfor[0]),Integer.parseInt(imfor[1]),Boolean.parseBoolean(imfor[2]));
            Boxes.get(i).settimes(Integer.parseInt(imfor[3]),Integer.parseInt(imfor[4]));
            i++;
        }
        return result;
    }



    public  Player getPlayer() {
        return player;
    }

    public boolean Move(Integer[] bias) {
        validtimecount++;
        boolean result=player.move(map,Boxes,bias);
        over=true;
        for (Box e : Boxes
        ) {
                if (!e.isInfinal()){
                    over=false;
                    break;
                }

        }

        return result;
    }

    public int getfinalcount(){
        int boxtimecount=0;
        for (Box a:
             Boxes  ) {
            boxtimecount+=a.gettimecount();
        }
        return validtimecount+player.gettimecount()+boxtimecount;
    }
}
