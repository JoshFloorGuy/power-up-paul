package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Coin extends MapObject implements Collidable {

    public Coin(float x, float y) {
        super(x, y, 50,50, new Paint());
        super.color.setColor(Color.YELLOW);
    }

    @Override
    public boolean isCollide(MapObject c) {
            return ((x+width-5>c.x && x+5<c.x+c.width) && (y+height-5>c.y && y+5<c.y+c.height));
    }

    @Override
    public void onCollide(Collider c) {
        switch(c.getClass().getSimpleName()) {
            case "Player":
                ((Player) c).getCoin(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x+25,y+25,25, super.color);
        // super.draw(canvas);
    }
}
