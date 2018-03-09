package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Patryk Pawlicki on 28/02/2018.
 */

public class BalloonManager {
    private Context context;
    private ArrayList<Balloon> balloons;
    private long startTime;
    int spawnY = MainThread.SCREEN_HEIGHT + 200;

    public BalloonManager(Context context) {
        this.context = context;
        balloons = new ArrayList<>();
        startTime = System.currentTimeMillis();
    }

    public int getBalloonsListSize() {
        return balloons.size();
    }

    public void generateBalloon() {
        balloons.add(new Balloon(context, Color.RED, randX(), spawnY, 150 ));
    }
    
    public static int randX(){
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_WIDTH - 100);
        return randomNum;
    }

    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);

        for (Balloon b : balloons) {
            b.updatePosition(b.getSpeed());  //TO IMPROVE : randomise speed in intervals instead of fixed speed
                                            //     ADD : different speed bounds based on elapsedTime
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
