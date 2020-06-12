package pl.edu.pwr.s241222.flappy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameObject extends View {

    private TextView pointsView;
    private int points;
    private Runnable runnable;
    private Bitmap background;
    private Handler handler;
    private Paint paint;
    private int displayWidth, displayHeight;
    private int birdPosX, birdPosY;

    private Pipe[] pipe;
//    private int pipePosX, pipePosY;
//    private int pipeSizeX, pipeSizeY;
    private float pipeVelocity;

    private Boolean isTooHigh;
    private int velocity, gravity;
    private Display display;
    private Point point;

    private RectF rect;
    private int degrees;

    public GameObject(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        points = 0;

        handler = new Handler();
        background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        displayWidth = point.x;
        displayHeight = point.y;

        birdPosX = displayWidth/2;
        birdPosY = displayHeight/2;

        pipe = new Pipe[2];
        pipe[0] = new Pipe(100, displayHeight, displayWidth, displayHeight, displayWidth);
        pipe[1] = new Pipe(100, displayHeight, displayWidth, displayHeight, displayWidth+400);

        pipe[0].setVelocity(7);
        pipe[1].setVelocity(7);

        gravity = 2;
        degrees = 0;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        displayWidth = canvas.getWidth();
        displayHeight = canvas.getHeight();


        paint.setColor(Color.rgb(200,10,0));
//        canvas.drawBitmap(background,0,0,paint);
        handler.postDelayed(runnable, 30);

        if(birdPosY < displayHeight-25 || velocity < 0) {
            velocity += gravity;
            birdPosY += velocity;
        }

        rect = new RectF(birdPosX-25, birdPosY-25, birdPosX+25, birdPosY+25);
        degrees++;
        RectF r2 = new RectF(rect);
        Matrix mat = new Matrix();
        mat.setRotate(degrees, r2.centerX(), r2.centerY());
        mat.mapRect(r2);
        //rect.contains(birdPosX-25, birdPosY-25, birdPosX+25, birdPosY+25);

        canvas.drawRect(rect, paint);

        /* Jeśli znika z ekranu */
//        if(pipePosX < -pipeSizeX) {
//            pipePosX = displayWidth;
//        }

//        canvas.drawRect(pipePosX, pipePosY, pipePosX + pipeSizeX, pipePosY + pipeSizeY, paint);



        pipe[0].draw(canvas);
        pipe[1].draw(canvas);

        pipeVelocity = pipe[0].getVelocity()+0.005f;
        pipe[0].setVelocity(pipeVelocity);
        pipe[1].setVelocity(pipeVelocity);

        if(birdPosX > pipe[0].getPosX() || birdPosX > pipe[1].getPosX()){
            points++;
//            pointsView.setText("x");
        }
//        if(isDead()) {
//            System.out.println("Dead");
//        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            velocity -= 30;
//            birdPosY += velocity;
        }
        return true;
    }

    /* Sprawdza czy uderzono w rure */
    public boolean isDead() {
//        for(){} // TODO: Petla dla kazdej rury
        int pipePosX = pipe[0].getPosX();
        int pipePosY = pipe[0].getPosY();
        int pipeSizeX = pipe[0].getSizeX();
        int pipeSizeY = pipe[0].getSizeY();
        int pipeDistance = pipe[0].getDistance();

        if((birdPosX >= pipePosX && birdPosX <= pipePosX+pipeSizeX)){
            if((birdPosY >= pipePosY && birdPosY <= pipePosY+pipeSizeY) || (birdPosY <= pipePosY-pipeDistance)){
                return true;
            }
        }
        return false;
    }

    public int getPoints() {
        return points;
    }
}
