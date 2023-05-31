package com.example.pj_oop.Entity;

import java.util.ArrayList;
import java.util.List;

public class Box extends MovingElement{

    public Box(int pos_x, int pos_y, EntityIcons entityIcons) {
        super(pos_x, pos_y, entityIcons);
    }

    @Override
    public boolean passby(List<Element[]> Map, ArrayList<MovingElement> movingElements, Integer[] bias) {
        if (Map.get(pos_y+bias[1])[pos_x+bias[0]] instanceof Empty){
            for (MovingElement e:
                    movingElements) {
                if (e.pos_x==pos_x+bias[1] &&e.pos_y==pos_y+bias[0])
                    return false;
            }
            return true;
        }


        else return false;
    }
}
