package com.example.myapplication;

import android.graphics.Paint;
import android.util.Log;

public class Platform extends MapObject implements Collidable {

    public Platform(float x, float y, float w, float h, Paint c) {
        super(x,y,w,h,c);
    }

    @Override
    public boolean isCollide(MapObject c) {
        return (x+width>c.x && x<c.x+c.width) && (y+height>c.y && y<c.y+c.height);
    }

    @Override
    public void onCollide(Collider c) {
        switch(c.getClass().getSimpleName()) {
            case "Player":
                ((Player) c).platformCollide(this);
                break;
            case "Bullet":
                GameActivity.delete.add(c);
                break;
            default:
                c.platformCollide(this);
                break;
        }
    }
}
