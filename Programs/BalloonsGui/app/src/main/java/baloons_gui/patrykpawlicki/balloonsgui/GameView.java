package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    BalloonManager balloonManager;
    //Balloon balloon;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        balloonManager = new BalloonManager(context);

        //balloon = new Balloon(context, new Rect(250, 250, 500, 500), Color.BLUE, 50, 50);
        //System.out.println("x: " + balloon.getX()  + " y: " + balloon.getY());
        //balloonPoint = new Point(balloon.getX(), balloon.getY());
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
        gameCanvas.drawColor(Color.YELLOW);
        //if (balloon != null)
        //balloon.draw(gameCanvas);
        balloonManager.draw(gameCanvas);
    }

    public void update() {
        //if (balloonPoint != null)
        //    balloon.update(balloonPoint);
        balloonManager.update();
    }

    public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:

                    Log.d(TAG, "Touch Coords: x = " + event.getX() + ", y = " + event.getY());
                    Log.d(TAG, "List size: " + balloonManager.getBalloonsListSize());
                    Log.d(TAG, "Spawn X: " + balloonManager.randX() + " Spawn Y: " + balloonManager.spawnY);
                    Log.d(TAG, "XXXXX@: " + balloonManager.balloonCordX());

                    balloonManager.generateBalloon();
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