package com.example.myapplication;

import android.graphics.Paint;

import java.util.ArrayList;

public class BadGuy extends Collider implements Collidable {
    private int interval;
    private int step;
    public int score;

    public BadGuy(float x, float y, float w, float h, Paint c, float mxv,
                  float myv, float xi, float yi,float xf, float g, int interval, ArrayList<Collidable> map) {
        super(x,y,w,h,c,mxv,myv,xi,yi,xf,g,map);
        this.interval = interval;
        step = 0;
        score = 50;
    }

    @Override
    public boolean isCollide(MapObject c) {
        return (x+width>c.x && x<c.x+c.width) && (y+height>c.y && y<c.y+c.height);
    }

    @Override
    public void onCollide(Collider c) {
        switch(c.getClass().getSimpleName()) {
            case "Player":
                ((Player) c).hit();
                break;
            case "Bullet":
                if(((Bullet) c).parent.getClass().equals(Player.class)) {
                    ((Player) ((Bullet) c).parent).hitBadGuy(this,(Bullet) c);
                }
                break;
            default:
                break;
        }
    }
}
