 package baloons_gui.patrykpawlicki.balloonsgui;

 import android.graphics.Canvas;
 import android.view.SurfaceHolder;

 /**
 * Created by Patryk Pawlicki on 26/01/2018.
 */

public class MainThread extends Thread {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static int score = 0;
    public static int lives = 3;
    public static boolean muted = false;

    private static final String TAG = MainThread.class.getSimpleName();

    private GamePanel gamePanel;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private long FPS = 0;
    private static int maxFPS = 30;
    public static Canvas gameCanvas;
    public static boolean gameOver = false;

    private boolean isSurfaceLocked = false;

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isSurfaceLocked() { return isSurfaceLocked; }

    @Override
    public void run() {
        int frameCount = 0;
        long startTime;
        long timeMillis = 1000 / maxFPS;
        long waitTime;
        long totalTime = 0;
        long targetTime = 1000 / maxFPS;
        long loopcount = 0L;

        //Log.d(TAG, "Starting game loop");

        while (running) {
            startTime = System.nanoTime();
            gameCanvas = null;
            loopcount++;

            try {
                gameCanvas = this.surfaceHolder.lockCanvas();
                isSurfaceLocked = true;
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    gamePanel.draw(gameCanvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (gameCanvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(gameCanvas);
                        isSurfaceLocked = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == maxFPS) {
                FPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println("FPS: " + FPS);
            }
        }
        //Log.d(TAG, "Game loop finished, loop executed " + loopcount + " times.");
    }
}