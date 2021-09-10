package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class Collider extends MapObject {
    public float xvel;
    public float yvel;
    public float maxxvel;
    public float maxyvel;
    public float xint;
    public float yint;

    public boolean doDraw;

    public float xfight;
    public float gravity;

    ArrayList<Collidable> map;

    public Collider(float x, float y, float w, float h, Paint c, float xv, float yv, float mxv,
                    float myv, float xi, float yi, float xf, float g, ArrayList<Collidable> map) {
        super(x,y,w,h,c);
        xvel = xv;
        yvel = yv;
        maxxvel = mxv;
        maxyvel = myv;
        xint = xi;
        yint = yi;
        xfight = xf;
        gravity = g;
        this.map = map;
        doDraw = true;
    }
    
    public Collider(float x, float y, float w, float h, Paint c, float mxv,
                    float myv, float xi, float yi,float xf, float g, ArrayList<Collidable> map) {
        this(x,y,w,h,c,0,0,mxv,myv,xi,yi,xf,g,map);
    }

    @Override
    public void draw(Canvas canvas) {
        yvel+=(gravity);
        xvel+=xint;

        //if(Math.abs(xvel)<xfight) xvel=0;

        //physics modifiers for screen movement(doesnt really work?)
        if(GameActivity.screenRight || GameActivity.screenLeft){

            xvel = 0;
        }
        /*else if(xvel == 0){
            xvel = GameActivity.prevXvel;
        }*/
        if(GameActivity.screenUp || GameActivity.screenDown){

            yvel = 0;
        }
        /*else if(yvel == 0){
            yvel = GameActivity.prevYvel;
        }*/
/*
        if(xvel!=0.0) {
            xvel = xvel - (Math.abs(xvel)/xvel)*xfight;
        }
        */
        if(Math.abs(xvel)>maxxvel) xvel = (Math.abs(xvel)/xvel)*maxxvel;
        if(Math.abs(yvel)>maxyvel) yvel = (Math.abs(yvel)/yvel)*maxyvel;
        x+=xvel;
        y+=yvel;
        collideTest();
        if(doDraw) super.draw(canvas);
    }

    public void collideTest() {
        Iterator<Collidable> i = map.iterator();
        Collidable c;
        while(i.hasNext()) {
            c = i.next();
            if(c.isCollide(this) && !c.equals(this)) {
                try {
                    c.onCollide(this);
                }catch(Exception e) {
                    Log.i("error",e.toString());
                }
            }
        }
    }

    public void platformCollide(Platform p) {
        y-=yvel;
        if(!p.isCollide(this)) {
            if(yvel>0) {
                y = p.y-height;
            } else {
                y = p.y+p.height;
            }
            yvel = 0;
        } else {
            y+=yvel;
            if(xvel>0) {
                x = p.x-width;
            } else {
                x = p.x+p.width;
            }
            xvel = 0;
        }
    }
}
