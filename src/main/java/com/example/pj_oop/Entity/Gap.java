package com.example.pj_oop.Entity;

public class Gap extends MapElement{


    public Gap(char a) {
        this.classify=a;
    }

    @Override
    public boolean passby(MovingElement element) {
        if (element instanceof Player) return true;
        else if(element instanceof Box && ((Box) element).playstatus) return true;
        else return false;
    }
}
