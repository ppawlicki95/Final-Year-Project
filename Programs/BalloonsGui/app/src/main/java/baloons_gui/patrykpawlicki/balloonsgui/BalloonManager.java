package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Patryk Pawlicki on 28/02/2018.
 */

public class BalloonManager {
    private Context context;
    private CopyOnWriteArrayList<Balloon> balloons;
    private long startTime;
    private long spawnTime;
    public int spawnRounds;
    public long gameEndTime;

    SoundPool snd;
    int pop_sound;

    private static final String TAG = MainThread.class.getSimpleName();

    public BalloonManager(Context context) {
        this.context = context;
        balloons = new CopyOnWriteArrayList<>();

        startTime = System.currentTimeMillis();
        spawnTime = (int) (System.currentTimeMillis() - startTime) /1000;
        MainThread.score = 0;
        MainThread.lives = 3;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            snd = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(aa)
                    .build();
            pop_sound = snd.load(context, R.raw.pop_sound, 1);
        } else {
            snd = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
            pop_sound = snd.load(context, R.raw.pop_sound, 1);
        }
    }

    public int getBalloonsListSize() {
        return balloons.size();
    }

    public void generateBalloon(Context context, int x, int y, int radius, int elapsedTime) {
        if (elapsedTime <= 30) { spawnRounds = 1; }
        else if (elapsedTime > 30 && elapsedTime <= 60) { spawnRounds = 2; }
        else if (elapsedTime > 60) { spawnRounds = 3; }

        for (int i = 0; i < spawnRounds; i++) {
            balloons.add(new Balloon(context, x, y, radius, elapsedTime));
        }
    }
    
    public static int randX(){
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_WIDTH - MainThread.SCREEN_WIDTH/10);
        return randomNum;
    }

    public static int randY() {
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_HEIGHT/4) + MainThread.SCREEN_HEIGHT + MainThread.SCREEN_HEIGHT/4;
        return randomNum;
    }

    public void update() {
        if (!MainThread.gameOver) {
            int elapsedTime = ((int) (System.currentTimeMillis() - startTime) / 1000);
            //Log.d(TAG, "Elapsed time: " + elapsedTime);
            //Log.d(TAG, "Spawn time: " + spawnTime);

            if (elapsedTime > spawnTime + 1) {
                generateBalloon(context, randX(), randY(), MainThread.SCREEN_WIDTH/5, elapsedTime);
                //Log.d(TAG, "generateBalloon()");
                //Log.d(TAG, " Number of balloons: " + balloons.size());
                spawnTime++;
            }

            for (Balloon b : balloons) {
                b.updatePosition(b.getSpeed());

                if (b.getY() < 0 - b.getCircle().radius) {
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

    public void draw(Canvas canvas) {
        for(Balloon b : balloons) {
            if(b.isPopped() == false)
                b.draw(canvas);
        }
    }

    public void handleTouchEvent(float x, float y) {
        for (Balloon b : balloons) {
            if (b.handleTouchEvent(x, y) == true) {
                if (b.isPopped() != true) {
                    MainThread.score++;
                    b.setPopped(true);
                    if (!MainThread.muted)
                        snd.play(pop_sound, 1, 1, 1, 0, 1);
                }
            }
        }
    }

    public long getStartTime() {
        return startTime;
    }
}
