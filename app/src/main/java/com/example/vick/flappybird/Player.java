package com.example.vick.flappybird;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Created by vick on 12/19/16.
 */

public class Player extends GameObject {
    private int radius = 26;
    private int playerScore;
    private boolean playing;
    private Paint paint;
    private boolean up;

    public Player(){
        super.x = (2*radius)+150;
        super.y = GamePanel.newHeight/2;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    public void update(){
        dy+= 10;

        if(up) {
            dy -= 17;
        }

        y+=dy;

        if(y>GamePanel.newHeight+161 || y<-161){
            dy = 0;
            y = GamePanel.newHeight/2;
            playing = false;
        }


    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x,y,radius,paint);
    }

    public void setUpJump(boolean x){up = x;}
    public boolean getUpJump(){return up;}
    public void setPlaying(boolean x){playing = x;}
    public boolean getPlaying(){return playing;}

}
