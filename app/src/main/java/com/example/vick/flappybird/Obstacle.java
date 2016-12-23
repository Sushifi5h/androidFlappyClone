package com.example.vick.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by vick on 12/22/16.
 */

public class Obstacle extends GameObject {
    private Paint paint;
    private int screenHeight;

    public Obstacle(int x, int y, int screenHeightM){
        super.x = x;
        super.y = y;
        screenHeight = screenHeightM;
        paint = new Paint();
        paint.setColor(Color.GREEN);

    }

    public void update(){
        x += GamePanel.GAMESPEED;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(x, y,(float) x+100, (float)y+screenHeight, paint);
    }
}
