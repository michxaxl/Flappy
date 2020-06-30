package pl.edu.pwr.s241222.flappy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameObject extends View {

    private Handler handler;

    private TextView pointsView;
    private int points;
    private Runnable runnable;
    private Bitmap background;
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

    private Boolean isPassed;


    public GameObject(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        points = 0;

        handler = new Handler();
//        background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
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
        pipe[0] = new Pipe(68, displayHeight, displayWidth, displayHeight, displayWidth, context);
        pipe[1] = new Pipe(68, displayHeight, displayWidth, displayHeight, displayWidth+400, context);

        pipe[0].setVelocity(7);
        pipe[1].setVelocity(7);

        gravity = 2;
        degrees = 0;


        isPassed = false;

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        displayWidth = canvas.getWidth();
        displayHeight = canvas.getHeight();


        if(isDead()) {
            handler.sendEmptyMessage(points); // Wyslanie punktow do Activity
            return;
        }



        paint.setColor(Color.rgb(200,10,0));
//        canvas.drawBitmap(background,0,0,paint);
//        canvas.drawRect(0, 0, displayWidth,displayHeight, paint);
        handler.postDelayed(runnable, 30);

        if(birdPosY < displayHeight-25 || velocity < 0) {
            velocity += gravity;
            birdPosY += velocity;
        }

        rect = new RectF(birdPosX-25, birdPosY-25, birdPosX+25, birdPosY+25);


        canvas.drawRect(rect, paint);



        pipe[0].draw(canvas);
        pipe[1].draw(canvas);

        if(pipe[0].getPassed() || pipe[1].getPassed()) {
            pipe[0].setVelocity(pipe[0].getVelocity() + 0.005f);
            pipe[1].setVelocity(pipe[1].getVelocity() + 0.005f);
        }


        for(int i=0; i<2; i++){
            if(birdPosX >= pipe[i].getPosX()-5 && !pipe[i].getPassed() && birdPosY > pipe[i].getPosY()-pipe[i].getDistance() && birdPosY < pipe[i].getPosY()-50){
                points++;
                pointsView.setText(String.valueOf(points));
            }
        }

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
        for(int i=0; i<2; i++) {
            int pipePosX = pipe[i].getPosX();
            int pipePosY = pipe[i].getPosY();
            int pipeSizeX = pipe[i].getSizeX();
            int pipeSizeY = pipe[i].getSizeY();
            int pipeDistance = pipe[i].getDistance();

            if ((birdPosX >= pipePosX && birdPosX <= pipePosX + pipeSizeX)) {
                if ((birdPosY >= pipePosY && birdPosY <= pipePosY + pipeSizeY) || (birdPosY <= pipePosY - pipeDistance)) {

                    return true;
                }
            }

        }
        return false;
    }

    public void setPointsView(TextView pointsView) {
        this.pointsView = (TextView)pointsView;
    }

    public int getPoints() {
        return points;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
