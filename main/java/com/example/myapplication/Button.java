package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Button implements Drawable {
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean isPressed;
    public boolean marked;
    private Paint paint;

    public Button(float x, float y, float width, float height) {
        isPressed = false;
        marked = false;
        paint = new Paint();
        paint.setColor(Color.RED);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(x,y,x+width,y+height,paint);
    }

    public void onPress() {
        isPressed = true;
        paint.setColor(Color.GREEN);
    }

    public void onRelease() {
        isPressed = false;
        paint.setColor(Color.RED);
    }

    public boolean isPress(Finger f) {
        return (f.x>x && f.x<x+width && f.y>y && f.y<f.y+height);
    }
}
