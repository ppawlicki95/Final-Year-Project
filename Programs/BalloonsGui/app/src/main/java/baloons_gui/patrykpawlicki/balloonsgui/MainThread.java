 package baloons_gui.patrykpawlicki.balloonsgui;

 import android.graphics.Canvas;
 import android.view.SurfaceHolder;

 /**
 * Created by Patryk Pawlicki on 26/01/2018.
 */

 /**
  * Class which contains the logic for the Game loop
  */
 public class MainThread extends Thread {
     //Game loop code has been based on a tutorial I found:
     // https://www.youtube.com/watch?v=OojQitoAEXs&t=5s
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static int score = 0;
    public static int lives = 3;
    public static boolean muted = false; // muted button status

    private static final String TAG = MainThread.class.getSimpleName();

    private GamePanel gamePanel;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private long FPS = 0;
    private static int maxFPS = 30;
    public static Canvas gameCanvas;
    public static boolean gameOver = false;

    private boolean isSurfaceLocked = false;

     /**
      * Constructor class for the MainThread class
      * @param surfaceHolder - surfaceHolder used to handle graphics
      * @param gamePanel - object which takes care of most Game and graphics related aspects
      */
    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

     /**
      * Setter for the running boolean
      * @param running - boolean to state whether the Game loop is running
      */
    public void setRunning(boolean running) {
        this.running = running;
    }

     /**
      * Game loop method responsible for calling the appropriate draw and update methods,
      * locking the canvas and updating some variables such as time and FPS.
      */
    @Override
    public void run() {
        int frameCount = 0;
        long startTime;
        long timeMillis = 1000 / maxFPS;
        long waitTime;
        long totalTime = 0;
        long targetTime = 1000 / maxFPS;
        long loopcount = 0L;

        //Log.d(TAG, "Starting Game loop");

        while (running) {
            startTime = System.nanoTime();
            gameCanvas = null;
            loopcount++;

            try {
                gameCanvas = this.surfaceHolder.lockCanvas(); // lock canvas to implement UI changes
                isSurfaceLocked = true;
                synchronized (surfaceHolder) {
                    this.gamePanel.update();    // update the screen
                    gamePanel.draw(gameCanvas); // draw on screen
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (gameCanvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(gameCanvas); // implement changes
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