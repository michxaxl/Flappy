package pl.edu.pwr.s241222.flappy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Pipe {

    private Canvas canvas;
    private Paint paint;
    private int sizeX, sizeY;
    private int posX, posY;
    private float velocity;
    private int distance;
    private int displayWidth, displayHeight;

    public Pipe(int sizeX, int sizeY, int displayWidth, int displayHeight, int startPosX) {
//        this.canvas = canvas;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        velocity = 8;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(70, 120, 180));

        posX = startPosX;
        posY = displayHeight/2;

        distance = 200;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public void draw(Canvas canvas) {

        posX -= velocity;
        if(posX < -sizeX) {
            if(velocity < 10) {
                posY = new Random().nextInt(displayHeight/2 - distance * 2) + distance * 2;
            }else{
                posY = new Random().nextInt(displayHeight - distance * 2) + distance * 2;
            }
            posX = canvas.getWidth();
        }
        canvas.drawRect(posX, 0, posX + sizeX, posY-distance, paint);
        canvas.drawRect(posX, posY, posX + sizeX, posY + sizeY, paint);

    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getDistance() {
        return distance;
    }

    public float getVelocity() {
        return velocity;
    }


}
