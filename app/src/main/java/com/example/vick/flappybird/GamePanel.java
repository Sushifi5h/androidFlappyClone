package com.example.vick.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vick on 12/19/16.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public static final int WIDTH = 856, HEIGHT = 480;
    public static int newWidth, newHeight;
    public static final int GAMESPEED = -10;
    private Player player;
    private BackGround bg;
    private Bitmap scaledBg;
    private ArrayList<Obstacle> pillars;
    private long pillarTime, elaspedTime;
    private Random rand;
    //private Obstacle pillarTest;

    //construct requires State or Context
    public GamePanel(Context context){
        super(context);//surface view construct requires context
        getHolder().addCallback(this);//events on phone
        setFocusable(true);
        rand = new Random();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000){
            counter++;
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            }catch(InterruptedException e){e.printStackTrace();}

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        player = new Player();
        //pillarTest = new Obstacle(getWidth()-300, getHeight()-500, getHeight());
        pillars = new ArrayList<Obstacle>();


        Bitmap bgimage = BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1);

        float scale = (float) bgimage.getHeight()/getHeight();
        newWidth = Math.round(bgimage.getWidth()/scale);
        newHeight = Math.round(bgimage.getHeight()/scale);
        scaledBg = Bitmap.createScaledBitmap(bgimage, newWidth, newHeight, true);
        bg = new BackGround(scaledBg);

        //start game loop
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
        //crease surfaces

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if(!player.getPlaying())
                player.setPlaying(true);
            else
                player.setUpJump(true);
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            player.setUpJump(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        if(player.getPlaying()) {
            bg.update();
            player.update();
            //pillarTest.update();


            if(pillars.isEmpty()) {
                pillars.add(new Obstacle(getWidth() - 200, getHeight()/2, getHeight()));
                pillars.add(new Obstacle(getWidth() - 200, getHeight()/2-getHeight()-200, getHeight()));
                pillarTime = (System.nanoTime() / 1000000000);
            }else{
                elaspedTime = (System.nanoTime()/1000000000) - pillarTime;
                if(elaspedTime > 5){
                    double randomShit = rand.nextDouble() * 0.8;
                    double workAround = getHeight() * randomShit;
                    pillars.add(new Obstacle(getWidth()+125, (int) workAround + 200, getHeight()));
                    pillars.add(new Obstacle(getWidth()+125, (((int) workAround + 200)-getHeight()) - 200, getHeight()));
                    pillarTime = (System.nanoTime()/1000000000);
                    System.out.println(pillars.size());
                    System.out.println(pillars.get(0).getX());
                }
            }

            for(int i = 0; i < pillars.size(); i++){
                pillars.get(i).update();
                if(collision(player,pillars.get(i)))
                    System.out.println("Hello");
                if (pillars.get(i).x < -125){
                    System.out.println("Hello");
                    pillars.remove(i);
                }


        }


        }


    }

    @Override
    public void draw(Canvas canvas)
    {
        //final float scaleFactorX = (float) getWidth()/WIDTH;
        //final float scaleFactorY = (float) getHeight()/HEIGHT;

        //System.out.println(getWidth());

        if (canvas!=null) {
            final int savedState = canvas.save();
            //canvas.scale(scaleFactorX, scaleFactorY);
            //call draw
            bg.draw(canvas);
            player.draw(canvas);
            //pillarTest.draw(canvas);

            for(int i = 0; i < pillars.size(); i++){
                pillars.get(i).draw(canvas);
            }


            canvas.restoreToCount(savedState);
        }
    }

    public boolean collision(GameObject x, GameObject y){
        if(Rect.intersects(x.getRectangle(), y.getRectangle())){
            return true;
        }
        return false;
    }


}
