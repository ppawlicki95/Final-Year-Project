package baloons_gui.patrykpawlicki.balloonsgui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Patryk Pawlicki on 26/01/2018.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainThread.class.getSimpleName();
    private MainThread thread;
    BalloonManager balloonManager;

    public GamePanel(Context context) {
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
            drawGamePaint(gameCanvas);
        } else {
            long timeInGameSeconds = (balloonManager.gameEndTime - balloonManager.getStartTime()) / 1000;
            String timeInGame = DateUtils.formatElapsedTime(timeInGameSeconds);
            drawGameOverPaint(gameCanvas, thread.score, timeInGame);
            updateHighscores(thread.score, timeInGame);
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
                    if (!MainThread.gameOver) {
                        balloonManager.handleTouchEvent(event.getX(), event.getY());
                    }
            }
        return true;
    }

    public void drawGamePaint(Canvas canvas) {
        Paint score = new Paint();
        score.setTextSize(MainThread.SCREEN_WIDTH / 15);
        score.setColor(Color.WHITE);
        canvas.drawText("Score: " + thread.score,
                MainThread.SCREEN_WIDTH /20 *1,
                MainThread.SCREEN_HEIGHT/20 *1, score);

        Paint lives = new Paint();
        lives.setTextSize(MainThread.SCREEN_WIDTH / 15);
        lives.setColor(Color.WHITE);
        canvas.drawText("Lives: " + thread.lives,
                MainThread.SCREEN_WIDTH /20 *15,
                MainThread.SCREEN_HEIGHT/20 *1, lives);
    }

    public void drawGameOverPaint(Canvas canvas, int score, String timeInGame) {
        Drawable gameOver = getResources().getDrawable(R.drawable.game_over);
        gameOver.setBounds(getLeft(), getTop(), getRight(), getBottom());
        gameOver.draw(canvas);

        Paint gameOver_score = new Paint();
        gameOver_score.setTextSize(MainThread.SCREEN_WIDTH / 10);
        gameOver_score.setColor(Color.WHITE);
        canvas.drawText("You scored: " + score,
                MainThread.SCREEN_WIDTH/100 *20,
                MainThread.SCREEN_HEIGHT/100 *75, gameOver_score);

        Paint gameOver_time = new Paint();
        gameOver_time.setTextSize(MainThread.SCREEN_WIDTH / 10);
        gameOver_time.setColor(Color.WHITE);
        canvas.drawText("Time played: " + timeInGame,
                MainThread.SCREEN_WIDTH/100 *10,
                MainThread.SCREEN_HEIGHT/100 *85, gameOver_score);
    }

    public void updateHighscores(int newScore, String timeInGame) {
        if (newScore > highscores.highscore3 &&
                newScore < highscores.highscore2 &&
                newScore < highscores.highscore1) {
            highscores.highscore3 = newScore;
            highscores.newtime1 = timeInGame;
        }
        if (newScore > highscores.highscore2 &&
                newScore < highscores.highscore1) {
            int tempScore = highscores.highscore2;
            String tempTime = highscores.newtime2;
            highscores.highscore2 = newScore;
            highscores.newtime2 = timeInGame;
            highscores.highscore3 = tempScore;
            highscores.newtime3 = tempTime;
        }
        if (newScore > highscores.highscore1) {
            int tempScore = highscores.highscore1;
            int tempScore2 = highscores.highscore2;
            String tempTime = highscores.newtime1;
            String tempTime2 = highscores.newtime2;
            highscores.highscore1 = newScore;
            highscores.newtime1 = timeInGame;
            highscores.highscore2 = tempScore;
            highscores.newtime2 = tempTime;
            highscores.highscore3 = tempScore2;
            highscores.newtime3 = tempTime2;
        }
    }

}