package com.example.pj_oop.Entity;

/**
 * @author zhsyy
 * @version 1.0
 * @date 2023/5/17 10:35
 */

public class Empty extends MapElement{


    public Empty(char  a) {
        this.classify='0';
    }

    @Override
    public boolean passby(MovingElement element) {
        return true;
    }
}
