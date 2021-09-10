package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class MapObject implements Drawable {
    public float x;
    public float y;
    public float width;
    public float height;
    Paint color;

    public MapObject(float x, float y, float w, float h, Paint c) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.color = c;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(x,y,x+width,y+height,color);
    }
}
