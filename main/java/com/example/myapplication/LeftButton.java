package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class LeftButton extends Button {
    Player p;
    public LeftButton(Player p) {
        super(50, GameActivity.windowHeight-120, 100, 100);
        this.p = p;
    }

    @Override
    public void onPress() {
        super.onPress();
        p.moveLeft();
    }

    @Override
    public void onRelease() {
        super.onRelease();
        p.stopMoving();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText("<",x+45,y+40,new Paint());
    }
}
