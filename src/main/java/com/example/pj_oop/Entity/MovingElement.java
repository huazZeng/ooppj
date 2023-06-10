package com.example.pj_oop.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingElement{
    protected int pos_x;
    protected int pos_y;
    protected int crashtime=0;
    protected   int throughtime=0;
    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }
    public MovingElement(int pos_x,int pos_y){
        this.pos_x=pos_x;
        this.pos_y=pos_y;

    }
    public void add(int i){
        if (i==0) crashtime++;
        if (i==1) throughtime++;
    }
    public void settimes(int i,int j){
        crashtime=i;
        throughtime=j;
    }
    public  int gettimecount(){
        return throughtime+crashtime;
    }
}
