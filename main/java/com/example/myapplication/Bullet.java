package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Bullet extends Collider {
    public MapObject parent;
    public Bullet(float x, float y, float xv, float yv, MapObject p, ArrayList<Collidable> map) {
        super(x, y, 50, 50, new Paint(), xv, yv, xv*2, yv*2, 0, 0, 0, 0, map);
        parent = p;
        this.color.setColor(Color.rgb(255,166,0));
    }


}
