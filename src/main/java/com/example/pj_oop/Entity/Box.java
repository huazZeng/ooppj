package com.example.pj_oop.Entity;

import java.util.ArrayList;
import java.util.List;

public class Box extends MovingElement{
    private boolean infinal=false;
    protected boolean playstatus=false;
    public boolean isInfinal() {
        return infinal;
    }

    public Box(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }

    @Override
    public String toString() {
        return pos_x+" "+pos_y+" "+infinal;
    }
    public boolean move(List<MapElement[]> Map, ArrayList<Box> movingElements, Integer[] bias, boolean toolstaus,boolean ispushbox){
        if (passby(Map, movingElements, bias, toolstaus,ispushbox)){
            pos_y+=bias[1];
            pos_x+=bias[0];
            if(Map.get(pos_y)[pos_x] instanceof finalPoint) infinal=true;
            return true;
        }
        return false;
    }
    public boolean passby(List<MapElement[]> Map, ArrayList<Box> movingElements, Integer[] bias, boolean toolstaus,boolean ispushbox) {
        playstatus=toolstaus;

        for (Box E:
                movingElements) {
            if (E.pos_x==pos_x+bias[0] &&E.pos_y==pos_y+bias[1]&& !ispushbox) {
                return E.passby(Map, movingElements, bias, toolstaus,true);
            }
            else if (E.pos_x==pos_x+bias[0] &&E.pos_y==pos_y+bias[1]&& ispushbox) return false;
        }
        return Map.get(pos_y+bias[1])[pos_x+bias[0]].passby(this);
    }

    public void sestatus(int parseInt, int parseInt1,boolean infinal) {
        this.infinal=infinal;
        pos_x=parseInt;
        pos_y=parseInt1;
    }
}
