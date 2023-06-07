package com.example.pj_oop.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingElement extends Element{
    protected int pos_x;
    protected int pos_y;
    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }
    public MovingElement(int pos_x,int pos_y,EntityIcons entityIcons){
        this.pos_x=pos_x;
        this.pos_y=pos_y;
        this.entityIcons=entityIcons;
    }


}
