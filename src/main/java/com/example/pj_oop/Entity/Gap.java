package com.example.pj_oop.Entity;

public class Gap extends MapElement{


    public Gap(char a) {
        this.classify=a;
    }

    @Override
    public boolean passby(MovingElement element) {
        if (element instanceof Player){
            element.add(0);
            return true;
        }
        else if(element instanceof Box && ((Box) element).playstatus){
            element.add(0);
            return true;
        }
        else return false;
    }
}
