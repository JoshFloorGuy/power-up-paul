package com.example.myapplication;

public interface Collidable {
    public boolean isCollide(MapObject c);
    public void onCollide(Collider c);
}
