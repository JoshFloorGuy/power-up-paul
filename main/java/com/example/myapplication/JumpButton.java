package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class JumpButton extends Button {
    Player p;
    public JumpButton(Player p) {
        super(GameActivity.windowWidth-150, GameActivity.windowHeight-120, 100, 100);
        this.p = p;
    }

    @Override
    public void onPress() {
        super.onPress();
        p.jump();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText("^",x+45,y+40,new Paint());
    }
}
