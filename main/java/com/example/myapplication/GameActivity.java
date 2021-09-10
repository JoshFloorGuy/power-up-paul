package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Math.max;

public class GameActivity extends View {
    static ArrayList<Drawable> drawMap;
    static ArrayList<Collidable> collideMap;
    MapObject a;
    Paint white;
    Player p;
    boolean setNewBounds = true;
    String coords = "coords";
    ArrayList<Finger> fingers;
    ArrayList<Button> buttons;
    public static boolean screenRight, screenLeft, screenUp, screenDown;
    int scrollSpeed = 8;
    public static float prevXvel;
    public static float prevYvel;

    public static ArrayList<MapObject> delete = new ArrayList<MapObject>();

    public static int windowWidth;
    public static int windowHeight;

    //all of our current map platforms
    // x, y, x+width, y+height
    int[][] MapCoords = {
            //left
            {0,0,10,720},
            //right
            {1770,0,10,1680},
            //bottom 1
            {0,650,1620,10},
            //bottom 2
            {150,1280,1680,10},
            //middle, aerial platform
            {400,500,400,10}
    };
    int [] screenView = {0, 0, 1280, 720};

    public GameActivity(Context context) {
        super(context);
        windowWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        screenView[2] = windowWidth;
        screenView[3] = windowHeight;
        Paint blue = new Paint();
        white = new Paint();
        white.setColor(Color.WHITE);
        blue.setColor(Color.BLUE);
        screenLeft = false;
        screenRight = false;
        screenUp = false;
        screenDown = false;

        drawMap = new ArrayList<Drawable>();
        collideMap = new ArrayList<Collidable>();
        for(int i = 0; i < MapCoords.length; i++) {
            Platform q = new Platform(MapCoords[i][0],MapCoords[i][1],MapCoords[i][2],
                    MapCoords[i][3],blue);
            drawMap.add(q);
            collideMap.add(q);
        }
        LeftRightBadGuy bg = new LeftRightBadGuy(1000,100,180, (float)-0.2,collideMap);
        drawMap.add(bg);
        collideMap.add(bg);
        a = new MapObject(50,50,500,100,blue);
        p = new Player(collideMap);
        fingers = new ArrayList<Finger>();
        buttons = new ArrayList<Button>();
        buttons.add(new LeftButton(p));
        buttons.add(new RightButton(p));
        buttons.add(new JumpButton(p));
        buttons.add(new ShootButton(p));
        Coin c = new Coin(600,200);
        collideMap.add(c);
        drawMap.add(c);
        c = new Coin(1000,300);
        collideMap.add(c);
        drawMap.add(c);
        c = new Coin(1000,400);
        collideMap.add(c);
        drawMap.add(c);
        c = new Coin(1000,500);
        collideMap.add(c);
        drawMap.add(c);
        c = new Coin(900,300);
        collideMap.add(c);
        drawMap.add(c);
        c = new Coin(900,400);
        collideMap.add(c);
        drawMap.add(c);
        c = new Coin(900,500);
        collideMap.add(c);
        drawMap.add(c);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        MapObject t;
        while(!p.toRemove.isEmpty()) {
            t = p.toRemove.remove(0);
            drawMap.remove(t);
            collideMap.remove(t);
        }
        while(!delete.isEmpty()) {
            t = delete.remove(0);
            drawMap.remove(t);
            collideMap.remove(t);
        }

        if(p.newBullet!=null) {
            drawMap.add(p.newBullet);
            p.newBullet = null;
        }

        //all conditions will have a player placement portion and a screen frame portion

        //if the right of the screen - player's right side is lower than 100 and
        // the (left side of the screen + width)->right of screen is less than 1780(max x coord)

        //NOTE: possibly make screen movement with player centered(and stopped)
        //if they hit the middle the screen will move around them until they near a side
        if(screenView[2] - (p.x+100) < 500 && (screenView[0]+ screenView[2] - 100)< 1780){
            //potentially scale scroll based off player velocity
            screenView[0] += scrollSpeed;
            screenRight = true;
        }
        //if player's x is lower than 100 and left side of screen is still larger than 0
        if(p.x < 500 && screenView[0] > 0){
            screenView[0] -= scrollSpeed;
            screenLeft = true;
        }
        //condition for screen moving up
        if(p.y-50+screenView[1] < screenView[1] && (screenView[1] > 0)){
            screenView[1] -= scrollSpeed;
            screenView[1] = max(screenView[1],0);
            screenUp = true;
        }
        //condition for screen moving down
        if(p.y+p.height+50 >= screenView[3]){
            screenView[1] += scrollSpeed;
            screenDown = true;
        }
        updateMap(screenRight, screenLeft, screenUp, screenDown, p);


        if(!p.gameDone()) {
            Iterator<Drawable> i = drawMap.iterator();
            while (i.hasNext()) {
                i.next().draw(canvas);
            }
        // canvas.drawText(map.toString(),0,100,new Paint());
        // canvas.drawText(canvas.getClipBounds().toString(),0,200,new Paint());
        //a.draw(canvas);
        //a.x++;1
            canvas.drawText(coords,100,400, new Paint());
            canvas.drawText("YVel: "+p.yvel+", XVel: "+p.xvel
                    +", XInt: "+p.xint+", XFight: "+p.xfight,100,300, new Paint());

            canvas.drawText("ScreenView: (" +screenView[0]+ ", " +screenView[1]+
                    ", "+screenView[2]+ ", "+screenView[3]+")",40,200,new Paint());
            if(screenRight){
                canvas.drawText("screen right, viewX: " + screenView[0] + " a",400,250,new Paint());
            }if(screenLeft){
                canvas.drawText("screen left, viewX: "+ screenView[0] + " a",400,300,new Paint());
            }

            if(p.y > 2000){
                p.y = 100;
                p.x = 600;
            }
        }
        p.draw(canvas);
        if(!p.gameDone()) {
            Iterator<Button> b = buttons.iterator();
            while (b.hasNext()) {
                b.next().draw(canvas);
            }
        }
        invalidate();
    }

    public void updateMap(boolean screenRight, boolean screenLeft, boolean screenUp, boolean screenDown, Player p) {
        Collidable temp;
        //theoretically can make all objects move during scroll
        //need to deal with player movement
        for (int i = 0; i<collideMap.size(); i++)
        {
            temp = collideMap.get(i);
            if(screenRight){
                ((MapObject) temp).x -= scrollSpeed;

            }
            if(screenLeft){
                ((MapObject) temp).x += scrollSpeed;

            }
            if(screenUp){
                ((MapObject) temp).y += scrollSpeed;
            }
            if(screenDown){
                ((MapObject) temp).y -= scrollSpeed;
            }
        }
        if(screenRight){
            p.x -= scrollSpeed;
        }
        if(screenLeft){
            p.x += scrollSpeed;
        }
        if(screenUp){
            p.y += scrollSpeed;
        }
        if(screenDown){
            p.y -= scrollSpeed;
        }
        this.screenRight = false;
        this.screenLeft = false;
        this.screenUp = false;
        this.screenDown = false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Log.i("Touch",event.toString());
        Finger f;
        fingers.clear();
        for(int i=0; i<event.getPointerCount(); i++) {
            f = new Finger(event.getPointerId(i));
            if(event.getAction()!=MotionEvent.ACTION_UP) {
                fingers.add(new Finger(event.getX(i),event.getY(i),event.getPointerId(i)));
            }
        }
        // coords = buttons.toString() + ", "+fingers.toString();
        Iterator<Finger> fi = fingers.iterator();
        Iterator<Button> bi = null;
        Button b = null;
        while(fi.hasNext()) {
            f = fi.next();
            bi = buttons.iterator();
            while(bi.hasNext()) {
                b=bi.next();
                if(b.isPress(f)) {
                    b.marked = true;
                    break;
                }
            }
        }

        bi = buttons.iterator();

        while(bi.hasNext()) {
            b = bi.next();
            if(b.marked && !b.isPressed) b.onPress();
            if(!b.marked && b.isPressed) b.onRelease();
            b.marked = false;
        }
        /*if(event.getAction()==MotionEvent.ACTION_UP) {
            p.xint = 0;
        } else {
            if(event.getX()<180) {
               // if (event.getAction() == MotionEvent.ACTION_DOWN)
                    //p.xvel = -2;
                    p.xint = (float) -0.4;
            }
            if(event.getX()>1100) {
              //  if(event.getAction()==MotionEvent.ACTION_DOWN)
                    //p.xvel = 2;
                    p.xint = (float) 0.4;
            }
            if(event.getY()<150 && p.landed) {
                p.yvel = -12;
                p.landed = false;
            }
        }*/
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getAction();
        //int keyCode = event.getKeyCode();

        //TODO fix physics with player movements
        if (event.getRepeatCount() == 0) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                case KeyEvent.KEYCODE_W:
                    if (p.landed) {
                        p.yvel = -12;
                        p.landed = false;
                    }
                    coords = event.toString();
                    return true;
                //left
                case KeyEvent.KEYCODE_DPAD_UP:
                case KeyEvent.KEYCODE_A:

                    p.moveLeft();
                    /*
                    if(screenLeft){
                        p.xint = 0;
                    }
                    */
                    coords = event.toString();
                    return true;
                //right
                case KeyEvent.KEYCODE_DPAD_DOWN:
                case KeyEvent.KEYCODE_D:

                    p.moveRight();
                    /*
                    if(screenRight){
                        p.xint = 0;
                    }

                     */
                    coords = event.toString();
                    return true;

                case KeyEvent.KEYCODE_SPACE:
                    p.shoot();

                    coords = event.toString();
                    return true;
                default:
                    p.stopMoving();
                    //p.xint = 0;

                    coords = event.toString();
                    return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        int action = event.getAction();
        //int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_A:
                //p.xint = (float)-0.29;

            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_D:
                //p.xint = (float)0.29;

                coords = event.toString();
                return true;

            default:
              //  p.xint = 0;
               // coords = event.toString();
                return super.onKeyUp(keyCode, event);

        }
    }
}