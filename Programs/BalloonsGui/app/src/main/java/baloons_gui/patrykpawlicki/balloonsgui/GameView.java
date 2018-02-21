package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Patryk Pawlicki on 26/01/2018.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainThread.class.getSimpleName();
    private MainThread thread;
    private Balloon balloon;
    private Point balloonPoint;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        balloon = new Balloon(new Rect(250, 250, 500, 500), Color.RED, MainThread.SCREEN_WIDTH/2, MainThread.SCREEN_HEIGHT/2);
        System.out.println("x: " + balloon.getX()  + " y: " + balloon.getY());
        balloonPoint = new Point(balloon.getX(), balloon.getY());
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean trying = true;
        while (trying) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            trying = false;
        }
    }


    @Override
    public void draw(Canvas gameCanvas){
        super.draw(gameCanvas);
        gameCanvas.drawColor(Color.BLACK);
        balloon.draw(gameCanvas);
    }

    public void update() {
        balloon.update(balloonPoint);
    }

    public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:

                    Log.d(TAG, "Touch Coords: x = " + event.getX() + ", y = " + event.getY());
                    Log.d(TAG, "Balloon Coords: x = " + balloon.getX() + ", y = " + balloon.getY());
            }

        /*if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() <= balloonPoint.x +200 && event.getX() >= balloonPoint.x -200
                    && event.getY() <= balloonPoint.y +200 && event.getY() >= balloonPoint.y -200) {
                        balloon.setPopped(true);
                        Log.d(TAG, "Popped = true");
            }
        }*/

        return true;
        //return super.onTouchEvent(event);
    }

}