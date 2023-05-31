package com.example.pj_oop.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 10:35
 */

public class Player extends MovingElement{


    public Player(int pos_x, int pos_y, EntityIcons entityIcons) {
        super(pos_x,pos_y,entityIcons);

    }

    public EntityIcons getEntityIcons() {
        return entityIcons;
    }

    public void setEntityIcons(EntityIcons entityIcons) {
        this.entityIcons = entityIcons;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }



    @Override
    public boolean passby(List<Element[]> Map, ArrayList<Box> movingElements, Integer[] bias) {
        if (Map.get(pos_y+bias[1])[pos_x+bias[0]] instanceof Empty){
           for (Box e:
                 movingElements) {
                if (e.pos_x==pos_x+bias[0] &&e.pos_y==pos_y+bias[1])
                    return e.passby(Map,movingElements,bias);
            }
            return true;
        }

        else if (Map.get(pos_y+bias[1])[pos_x+bias[0]] instanceof Gap){
            return true;

        }
        return false;
    }
}
