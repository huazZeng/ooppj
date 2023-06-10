package com.example.pj_oop.Entity;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 10:34
 */

public class Wall extends MapElement{



    public Wall(char a) {
        this.classify=a;
    }

    @Override
    public boolean passby(MovingElement element) {
        element.add(1);
        return false;
    }
}
