package baloons_gui.patrykpawlicki.balloonsgui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.text.format.DateUtils;
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

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        balloonManager = new BalloonManager(context);
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
    public void draw(Canvas gameCanvas) {
        super.draw(gameCanvas);
        gameCanvas.drawColor(Color.rgb(140, 207, 255));

        if (!MainThread.gameOver){
            balloonManager.draw(gameCanvas);

            Paint score = new Paint();
            score.setTextSize(MainThread.SCREEN_WIDTH / 15);
            score.setColor(Color.WHITE);
            gameCanvas.drawText("Score: " + thread.score, MainThread.SCREEN_WIDTH /20 *1, MainThread.SCREEN_HEIGHT/20 *1, score);

            Paint lives = new Paint();
            lives.setTextSize(MainThread.SCREEN_WIDTH / 15);
            lives.setColor(Color.WHITE);
            gameCanvas.drawText("Lives: " + thread.lives, MainThread.SCREEN_WIDTH /20 *15, MainThread.SCREEN_HEIGHT/20 *1, lives);
        } else {
            Drawable gameOver = getResources().getDrawable(R.drawable.game_over);
            gameOver.setBounds(getLeft(), getTop(), getRight(), getBottom());
            gameOver.draw(gameCanvas);

            Paint gameOver_score = new Paint();
            gameOver_score.setTextSize(MainThread.SCREEN_WIDTH / 10);
            gameOver_score.setColor(Color.WHITE);
            gameCanvas.drawText("" + thread.score, MainThread.SCREEN_WIDTH/20 *9, MainThread.SCREEN_HEIGHT/20 *14, gameOver_score);

            long timeInGameSeconds = (balloonManager.gameEndTime - balloonManager.getStartTime()) / 1000;
            String timeInGame = DateUtils.formatElapsedTime(timeInGameSeconds);
            Paint gameOver_time = new Paint();
            gameOver_time.setTextSize(MainThread.SCREEN_WIDTH / 10);
            gameOver_time.setColor(Color.WHITE);
            gameCanvas.drawText("" + timeInGame, MainThread.SCREEN_WIDTH/100 *40, MainThread.SCREEN_HEIGHT/100 *87, gameOver_score);
        }
    }

    public void update() {
        if (!MainThread.gameOver)
            balloonManager.update();
    }

    public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //Log.d(TAG, "Touch Coords: x = " + event.getX() + ", y = " + event.getY());
                    //Log.d(TAG, "List size: " + balloonManager.getBalloonsListSize());
                    MainThread.lives--;
                    //balloonManager.generateBalloon();
                    if (!MainThread.gameOver) {
                        balloonManager.handleTouchEvent(event.getX(), event.getY());
                    }
            }

        return true;
        //return super.onTouchEvent(event);
    }

}