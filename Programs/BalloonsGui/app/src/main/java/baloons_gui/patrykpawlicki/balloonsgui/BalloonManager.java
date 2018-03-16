package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Patryk Pawlicki on 28/02/2018.
 */

public class BalloonManager {
    private Context context;
    private ArrayList<Balloon> balloons;
    private long startTime;
    private long spawnTime;

    private static final String TAG = MainThread.class.getSimpleName();

    public BalloonManager(Context context) {
        this.context = context;
        balloons = new ArrayList<>();
        startTime = System.currentTimeMillis();
        spawnTime = (int) (System.currentTimeMillis() - startTime) /1000;
    }

    public int getBalloonsListSize() {
        return balloons.size();
    }

    public void generateBalloon(int elapsedTime) {
        if (elapsedTime <= 30) {
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
        } else if (elapsedTime > 30 && elapsedTime <= 60) {
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
        } else if (elapsedTime > 60) {
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
            balloons.add(new Balloon(context, Color.RED, randX(), randY(), 150, elapsedTime));
        }

    }
    
    public static int randX(){
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_WIDTH - 100);
        return randomNum;
    }

    public static int randY() {
        Random rand = new Random();
        int randomNum = rand.nextInt(600) + MainThread.SCREEN_HEIGHT + 500;
        return randomNum;
    }

    public void update() {
        int elapsedTime = ((int) (System.currentTimeMillis() - startTime) /1000);
        Log.d(TAG, "Elapsed time: " + elapsedTime);
        //Log.d(TAG, "Spawn time: " + spawnTime);

        if (elapsedTime > spawnTime + 1) {
            generateBalloon(elapsedTime);
            Log.d(TAG, "generateBalloon()");
            Log.d(TAG, " Number of balloons: " + balloons.size());
            spawnTime = spawnTime + 2;
        }

        for(Balloon b : balloons) {
            b.updatePosition(b.getSpeed());

            if (b.getY() < 0 - b.getCircle().radius) {
                if (b.isPopped() != true)
                    MainThread.lives--;
                b.setPopped(true);
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
                if (b.isPopped() != true)
                    MainThread.score++;
                b.setPopped(true);
                //break;
            }
        }
    }
}
