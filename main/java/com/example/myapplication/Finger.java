package com.example.myapplication;

public class Finger {
    public float x;
    public float y;
    public int id;
    public Finger(float x, float y, int id) {
         this.x = x;
         this.y = y;
         this.id = id;
    }

    public Finger(int id) {this.id = id;}

    public void setCoords(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass()!=this.getClass()) return false;
        return ((Finger) o).id==id;
    }
}
