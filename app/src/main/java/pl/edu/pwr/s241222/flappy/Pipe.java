package pl.edu.pwr.s241222.flappy;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;


public class Pipe {

    private Canvas canvas;
    private Matrix pipeMatrix;
    private Bitmap pipeImg, pipeImgRotated;
    private Paint paint;
    private int sizeX, sizeY;
    private int posX, posY;
    private float velocity;
    private int distance;
    private int displayWidth, displayHeight;
    private Boolean isPassed;
    private Context appContext;

    public Pipe(int sizeX, int sizeY, int displayWidth, int displayHeight, int startPosX, Context context) {
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

        pipeImg = BitmapFactory.decodeResource(context.getResources(),R.drawable.pipe);

        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        matrix.preScale(-1, 1);
        pipeImgRotated = Bitmap.createBitmap(pipeImg, 0, 0, pipeImg.getWidth(), pipeImg.getHeight(), matrix, true);

        isPassed = false;

        distance = 200;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public void draw(Canvas canvas) {

        posX -= velocity;
        if(posX+pipeImg.getWidth() < -sizeX) {
            if(velocity < 10) {
                posY = new Random().nextInt(displayHeight/2 - distance * 2) + distance * 2;
            }else{
                posY = new Random().nextInt(displayHeight - distance * 2) + distance * 2;
            }
            posX = canvas.getWidth();
        }


        canvas.drawBitmap(pipeImg,posX,posY,paint);
        canvas.drawBitmap(pipeImgRotated, posX, -pipeImg.getHeight()+posY-distance, paint);

        if(posX<displayWidth/2){
            isPassed = true;
        }else{
            isPassed = false;
        }
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

    public Boolean getPassed() {
        return isPassed;
    }
}
