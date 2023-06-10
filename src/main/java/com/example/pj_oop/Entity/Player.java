package com.example.pj_oop.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 10:35
 */

public class Player extends MovingElement{

    private int toolcount;
    private boolean toolstaus=false;
    public Player(int pos_x, int pos_y,int toolcount) {
        super(pos_x,pos_y);
        this.toolcount=toolcount;
    }

    public int getToolcount() {
        return toolcount;
    }

    public void setToolcount(int toolcount) {
        this.toolcount = toolcount;
    }

    public void settool() {
        toolstaus=true;
        toolcount--;
    }


    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }


    @Override
    public String toString() {
        return pos_x+" "+pos_y+' '+toolstaus+' '+toolcount+' '+crashtime+" "+throughtime;
    }
    public boolean move(List<MapElement[]> Map, ArrayList<Box> movingElements, Integer[] bias){
        if (passby(Map, movingElements, bias)){
            pos_y+=bias[1];
            pos_x+=bias[0];
            return true;
        }
        return false;
    }
    public boolean passby(List<MapElement[]> Map, ArrayList<Box> movingElements, Integer[] bias) {
        boolean result =false;
        Boolean status=toolstaus;
        toolstaus=(toolstaus==true) ? !toolstaus:toolstaus;
        for (Box E:
                movingElements) {
            if (E.pos_x==pos_x+bias[0] &&E.pos_y==pos_y+bias[1])
                return  E.move(Map,movingElements,bias,status,false);

        }
       return Map.get(pos_y+bias[1])[pos_x+bias[0]].passby(this);
    }
}
