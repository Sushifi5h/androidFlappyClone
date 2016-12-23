package com.example.vick.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by vick on 12/20/16.
 */

public class BackGround {
    private Bitmap image;
    private int x, y, dx;

    public BackGround(Bitmap res) {
        this.image = res;
    }

    public void update() {
        x+=GamePanel.GAMESPEED;
        if(x<-GamePanel.newWidth){
            x=0;
        }

    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);
        if(x<0){
            canvas.drawBitmap(image, x+GamePanel.newWidth,y,null);
        }
    }
}
