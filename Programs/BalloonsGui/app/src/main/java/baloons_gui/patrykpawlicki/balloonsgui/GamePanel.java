package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Patryk Pawlicki on 26/01/2018.
 */

/**
 * The GamePanel class is responsible for what goes on the screen
 * all of the objects and text paint is called and updated from here
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //GamePanel structure written with the aid of a tutorial: https://www.youtube.com/watch?v=Rliwg0sELJo

    private static final String TAG = MainThread.class.getSimpleName();
    private MainThread thread;
    BalloonManager balloonManager;

    /**
     * GamePanel constructor
     * @param context - interface which allows access to resources and states
     */
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

    /**
     * Method responsible for drawing all of the objects and paint to the screen
     * @param gameCanvas - the Game canvas for the graphics
     */
    @Override
    public void draw(Canvas gameCanvas) {
        super.draw(gameCanvas);
        gameCanvas.drawColor(Color.rgb(140, 207, 255));

        if (!MainThread.gameOver){ // if game in progress
            balloonManager.draw(gameCanvas);
            drawGamePaint(gameCanvas);
        } else {                    // if game over
            long timeInGameSeconds = (balloonManager.getGameEndTime() - balloonManager.getStartTime()) / 1000;
            String timeInGame = DateUtils.formatElapsedTime(timeInGameSeconds);
            drawGameOverPaint(gameCanvas, thread.score, timeInGame);
            updateHighscores(thread.score, timeInGame);
        }
    }

    /**
     * Method which calls the appropriate objects' update methods
     */
    public void update() {
        if (!MainThread.gameOver)
            balloonManager.update();
    }

    /**
     * Method which notified the BalloonManager whether a touch event collided with an object
     * @param event - touch event
     * @return boolean which is true when collision occured, false otherwise
     */
    public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!MainThread.gameOver) {
                        balloonManager.handleTouchEvent(event.getX(), event.getY());
                    }
            }
        return true;
    }

    /**
     * Method which contains all of the code responsible for drawing the Game text paint
     * such as the score, lives during the Game
     * @param canvas
     */
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

    /**
     * Method which contains all of the code responsible for drawing the Game text paint
     * such as the score or elapsed time after the player looses a Game (Game over screen)
     * @param canvas
     * @param score
     * @param timeInGame
     */
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

    /**
     * Method which updates the Highscores table after a Game if score is higher
     * than the current Highscores stored
     * @param newScore
     * @param timeInGame
     */
    public void updateHighscores(int newScore, String timeInGame) {
        if (newScore > Highscores.highscore3 &&
                newScore < Highscores.highscore2 &&
                newScore < Highscores.highscore1) { // updating the 3rd high score and time
            Highscores.highscore3 = newScore;
            Highscores.newtime3 = timeInGame;
        }
        if (newScore > Highscores.highscore2 &&
                newScore < Highscores.highscore1) { // updating the 2nd highscore and time
            int tempScore = Highscores.highscore2;
            String tempTime = Highscores.newtime2;
            Highscores.highscore2 = newScore;
            Highscores.newtime2 = timeInGame;
            Highscores.highscore3 = tempScore;
            Highscores.newtime3 = tempTime;
        }
        if (newScore > Highscores.highscore1) { // updating the 1st highscore and time
            int tempScore = Highscores.highscore1;
            int tempScore2 = Highscores.highscore2;
            String tempTime = Highscores.newtime1;
            String tempTime2 = Highscores.newtime2;
            Highscores.highscore1 = newScore;
            Highscores.newtime1 = timeInGame;
            Highscores.highscore2 = tempScore;
            Highscores.newtime2 = tempTime;
            Highscores.highscore3 = tempScore2;
            Highscores.newtime3 = tempTime2;
        }
    }
}