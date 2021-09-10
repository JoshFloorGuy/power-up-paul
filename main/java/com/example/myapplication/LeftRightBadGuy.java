package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class LeftRightBadGuy extends BadGuy {
    private int interval;
    private int step;
    private float i;

    public LeftRightBadGuy(float x, float y, int interval, float i, ArrayList<Collidable> map) {
        super(x,y,100,100, new Paint(),3,20, (float) 0.2,0, (float) 0.15, (float) 0.4, interval, map);
        color.setColor(Color.MAGENTA);
        this.interval = interval;
        step = 0;
        this.i = i;
    }

    @Override
    public void draw(Canvas canvas) {
        if(yvel==0) {
            step++;
            xint = i;
        } else {
            xint = 0;
        }
        if(step==interval) {
            xint = -xint;
            i = xint;
            step = 0;
        }
        super.draw(canvas);
    }
}
