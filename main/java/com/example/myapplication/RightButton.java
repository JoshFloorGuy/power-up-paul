package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RightButton extends Button {
    Player p;
    public RightButton(Player p) {
        super(200, GameActivity.windowHeight-120, 100, 100);
        this.p = p;
    }

    @Override
    public void onPress() {
        super.onPress();
        p.moveRight();
    }

    @Override
    public void onRelease() {
        super.onRelease();
        p.stopMoving();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText(">",x+45,y+40,new Paint());
    }
}
