package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Patryk Pawlicki on 28/02/2018.
 */

public class BalloonManager {
    private Context context;
    private ArrayList<Balloon> balloons;
    private long startTime;
    int spawnY = MainThread.SCREEN_HEIGHT - 200;

    public BalloonManager(Context context) {
        this.context = context;
        balloons = new ArrayList<>();
        startTime = System.currentTimeMillis();
        balloons.add(new Balloon(context, new Rect(250, 250, 500, 500), Color.GREEN, MainThread.SCREEN_WIDTH/2, MainThread.SCREEN_HEIGHT/2));
    }

    public void generateBalloon() {
        balloons.add(new Balloon(context, new Rect(randX(), randX(), randX()+50, randX()+50), Color.GREEN, randX(), spawnY));
    }
    
    public static int randX(){
        Random rand = new Random();
        int randomNum = rand.nextInt(MainThread.SCREEN_WIDTH - 100);
        return randomNum;
    }

    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        
        for (Balloon b : balloons) {
            b.updatePosition(10);
        }
    }


    public void draw(Canvas canvas) {
        for(Balloon b : balloons) {
            b.draw(canvas);
        }
    }

    public int getBalloonsListSize() {
        return balloons.size();
    }

    public int balloonCordX() {
        return balloons.get(0).getY();
    }

}
