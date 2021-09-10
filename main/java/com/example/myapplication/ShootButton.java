package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ShootButton extends Button {
    Player p;
    public ShootButton(Player p) {
        super(GameActivity.windowWidth-300, GameActivity.windowHeight-120, 100, 100);
        this.p = p;
    }

    @Override
    public void onPress() {
        super.onPress();
        p.shoot();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText("Shoot",x+35,y+40,new Paint());
    }
}
