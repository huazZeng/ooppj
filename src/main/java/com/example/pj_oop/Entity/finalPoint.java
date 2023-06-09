package com.example.pj_oop.Entity;

public class finalPoint extends   MapElement{


    public finalPoint(char a) {
        this.classify=a;
    }

    @Override
    public boolean passby(MovingElement element) {
        return true;
    }
}
