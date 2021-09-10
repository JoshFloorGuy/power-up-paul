package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Player extends Collider {
    public boolean landed;
    public int coins;
    public int health;
    public int hitCooldown;
    public int deathCounter;
    public ArrayList<MapObject> toRemove = new ArrayList<MapObject>();
    public Paint text = new Paint();
    public int score;

    public boolean hasGun;
    public int ammo;
    public Paint gunPaint;
    public int direction;
    public int shootCooldown;
    public Bullet newBullet;

    public Player(ArrayList<Collidable> map) {
        super(100,100,100,100, new Paint(),8,20,0,0,0,(float)0.4,map);
        hasGun = true;
        ammo = 10;
        deathCounter=0;
        health = 5;
        hitCooldown = 0;
        landed = false;
        coins = 0;
        text.setTextSize(32);
        score = 0;
        direction = 1;
        gunPaint = new Paint();
        gunPaint.setColor(Color.rgb(0,128,0));
        shootCooldown = 0;
        newBullet = null;
    }


    public void stopMoving() {
        if(deathCounter==0) {
            xvel = 0;
        }
    }

    public void moveRight() {
        if(deathCounter==0) {
            xvel = maxxvel;
        }
        direction = 1;
    }

    public void moveLeft() {
        if(deathCounter==0) {
            xvel = -maxxvel;
        }
        direction = -1;
    }

    @Override
    public void draw(Canvas canvas) {
        landed = false;
        if(deathCounter==0) {
            if (hitCooldown > 0) hitCooldown--;
            if (shootCooldown > 0) shootCooldown--;
            super.doDraw = (hitCooldown % 4 < 2);
        } else {
            deathCounter++;
        }
        if(!gameDone()) {
            super.draw(canvas);
            if(hasGun) {
                if(direction<0) {
                    canvas.drawRect(x-50,y+40,x+20,y+60,gunPaint);
                    canvas.drawRect(x,y+60,x+20,y+80,gunPaint);
                } else {
                    canvas.drawRect(x+70,y+40,x+150,y+60,gunPaint);
                    canvas.drawRect(x+70,y+60,x+100,y+80,gunPaint);
                }
            }
        } else {
            canvas.drawText("Game over!",600,344,text);
        }
        canvas.drawText("Coins: "+coins,20,30, text);
        canvas.drawText("Health: "+health,20,70, text);
    }

    @Override
    public void platformCollide(Platform p) {
        if(deathCounter==0) {
            y -= yvel;
            if (!p.isCollide(this)) {
                if (yvel > 0) {
                    landed = true;
                    y = p.y - height;
                } else {
                    y = p.y + p.height;
                }
                yvel = 0;
            } else {
                y += yvel;
                if (xvel > 0) {
                    x = p.x - width;
                } else {
                    x = p.x + p.width;
                }
                xvel = 0;
            }
        }
    }

    public void getCoin(Coin c) {
        deleteItem(c);
        coins++;
    }

    public void jump() {
        if(landed) yvel = -16;
    }

    public void hit() {
        if(hitCooldown==0 && deathCounter==0) {
            health--;
            if(health>0) {
                hitCooldown = 60;
            } else {
                deathCounter++;
                xvel = (float) Math.random();
                xfight = 0;
                xint = 0;
                yint = 0;
                yvel = -6;
            }
        }
    }

    public boolean gameDone() {
        return deathCounter>100;
    }

    public void hitBadGuy(BadGuy b, Bullet bl) {
        score+=b.score;
        deleteItem(b);
        deleteItem(bl);
    }

    public void deleteItem(MapObject o) {
        toRemove.add(o);
    }

    public void shoot() {
        if(ammo>0 && shootCooldown==0) {
            newBullet = new Bullet(x + 25 + (90 * direction), y + 25, 10 * direction, 0, this, map);
            ammo--;
            shootCooldown = 60;
        }
    }
}