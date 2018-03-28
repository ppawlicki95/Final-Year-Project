package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Patryk Pawlicki on 28/02/2018.
 */

/**
 * Class responsible for managing the Balloon objects
 */
public class BalloonManager {
    private Context context;
    //CopyOnWriteArrayList instead of regular ArrayList to avoid ConcurrentModificationException
    //as the list gets modified whilst being iterated over
    private CopyOnWriteArrayList<Balloon> balloons;
    private SoundController soundController;
    private long startTime;
    private long spawnTime;
    private int spawnRounds;
    private long gameEndTime;

    private static final String TAG = MainThread.class.getSimpleName();

    /**
     * Constructor for the Balloon Manager
     * @param context - interface which allows access to resources and states
     */
    public BalloonManager(Context context) {
        this.context = context;
        balloons = new CopyOnWriteArrayList<>();
        soundController = new SoundController(context);

        startTime = System.currentTimeMillis();
        spawnTime = (int) (System.currentTimeMillis() - startTime) /1000;
        MainThread.score = 0;
        MainThread.lives = 3;
    }

    /**
     * Getter for the size of the balloons list
     * @return size of the balloons list
     */
    public int getBalloonsListSize() {
        return balloons.size();
    }

    /**
     * Method used for the generation of new balloons and their addition to the list
     * @param context - interface which allows access to resources and states
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param radius - circle objects radius that the balloon is based on
     * @param elapsedTime - time elapsed since the start of the Game
     */
    public void generateBalloon(Context context, int x, int y, int radius, int elapsedTime) {
        if (elapsedTime <= 30) { spawnRounds = 1; }
        else if (elapsedTime > 30 && elapsedTime <= 60) { spawnRounds = 2; }
        else if (elapsedTime > 60) { spawnRounds = 3; }

        if ((elapsedTime % 10 == 0)) {
            balloons.add(new Balloon(context, BalloonType.BLACK, x, y, radius, elapsedTime));
        } else if ((elapsedTime % 35 ==0)){
            balloons.add(new Balloon(context, BalloonType.RAINBOW, x, y, radius, elapsedTime));
        }
        // Amount of iterations (effectively balloons spawned)
        // based on elapsed time increasing as game progresses
        for (int i = 0; i < spawnRounds; i++) {
            balloons.add(new Balloon(context, BalloonType.STANDARD, x, y, radius, elapsedTime));
        }
    }

    /**
     * Method for generating a random X coordinate within a specified range for balloon spawning
     * @return Random X coordinate within a spawn area range
     */
    public static int randX(){
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_WIDTH - MainThread.SCREEN_WIDTH/10);
        return randomNum;
    }

    /**
     * Method for generating a random Y coordinate within a specified range for balloon spawning
     * @return Random Y coordinate within a spawn area range
     */
    public static int randY() {
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_HEIGHT/4) + MainThread.SCREEN_HEIGHT + MainThread.SCREEN_HEIGHT/4;
        return randomNum;
    }

    /**
     * Update method responsible for updating the balloons as well as managing their
     * spawning at the right time intervals
     */
    public void update() {
        if (!MainThread.gameOver) {
            int elapsedTime = ((int) (System.currentTimeMillis() - startTime) / 1000);
            if (elapsedTime > spawnTime + 1) { // 1 second countdown between spawns
                generateBalloon(context, randX(), randY(), MainThread.SCREEN_WIDTH/5, elapsedTime);
                spawnTime++;
            }
            for (Balloon b : balloons) {
                b.updatePosition(b.getSpeed());
                if (b.getY() < 0 - b.getCircle().radius) { // if balloon goes outside the screen
                    if (b.isPopped() != true)
                        MainThread.lives--;
                    b.setPopped(true);
                }
                if (MainThread.lives <= 0) {
                    MainThread.gameOver = true;
                    balloons.clear();
                    balloons = null;
                    gameEndTime = System.currentTimeMillis();
                }
            }
        }
    }

    /**
     * Method responsible for drawing the balloons to the canvas
     * @param canvas - surface that is being drawn on
     */
    public void draw(Canvas canvas) {
        for(Balloon b : balloons) {
            if(b.isPopped() == false)
                b.draw(canvas);
        }
    }

    /**
     * Method responsible for acting upon the touch even if collision occurs
     * Updating score, lives as well as playing sounds
     * @param x - X coordinate of the touch event
     * @param y - Y coordinate of the touch event
     */
    public void handleTouchEvent(float x, float y) {
        for (Balloon b : balloons) {
            if (b.handleTouchEvent(x, y) == true) { // Balloon.Type.BLACK collision handler
                if (b.isPopped() != true) {
                    if (b.getType() == BalloonType.BLACK) {
                        if (!MainThread.muted)
                            soundController.snd.play(soundController.beep_sound,
                                    1, 1, 1, 0, 1);
                        b.setTypeStandard();
                    } else if (b.getType() == BalloonType.RAINBOW) { // Balloon.Type.RAINBOW collision handler
                        b.setPopped(true);
                        MainThread.score = MainThread.score + 20;
                        if (MainThread.lives < 3) {MainThread.lives++;}
                        if (!MainThread.muted)
                            soundController.snd.play(soundController.rainbow_sound,
                                    1, 1, 1, 0, 1);
                    } else { // Regular balloon collision handler
                        MainThread.score++;
                        b.setPopped(true);
                        if (!MainThread.muted)
                            soundController.snd.play(soundController.pop_sound,
                                    1, 1, 1, 0, 1);
                    }
                }
            }
        }
    }

    /**
     * Getter for the Game start time
     * @return Game start time in milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

    public long getGameEndTime() { return gameEndTime; }
}
