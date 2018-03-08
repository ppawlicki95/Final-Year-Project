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
        balloons.add(new Balloon(context, Color.RED, 1000, 1000, 150 ));
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
            b.updatePosition(20);  //TO IMPROVE : randomise speed in intervals instead of fixed speed
                                            //     ADD : different speed bounds based on elapsedTime
        }
    }

    public void handleTouchEvent(float x, float y) {
        for (Balloon b : balloons) {
            if(x >= b.getX() - b.getCircle().radius && y >= b.getY() - b.getCircle().radius &&
                    x <= b.getX() + b.getCircle().radius && y <= b.getY() + b.getCircle().radius){
                if (b.isPopped() != true)
                    MainThread.score++;
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

    public int getBalloonsListSize() {
        return balloons.size();
    }

}
