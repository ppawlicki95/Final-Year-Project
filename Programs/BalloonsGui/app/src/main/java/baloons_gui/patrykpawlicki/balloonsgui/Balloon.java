package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Patryk Pawlicki on 02/02/2018.
 */

/**
 * Balloon Game object class
 */
public class Balloon implements BalloonController {
    // balloon bitmap src:
    // http://www.ballooncity.com.ar/Globo%20latex%2012%C2%B4%C2%B4%20Balloon%20Per.%20Rojo%20(Suelto)

    private Context context;
    private Circle circle;
    private Bitmap bitmap;

    private int x, y;
    private int speed;
    private boolean popped;
    private BalloonType type;

    /**
     * Constructor for the balloon object.
     * @param context - interface which allows access to resources and states
     * @param type - enum to distinguish regular balloons with balloons with special effects
     * @param x - object X coordinate used for generating the circle which balloons are based on
     * @param y - object Y coordinate used for generating the circle which balloons are based on
     * @param radius - radius of the circle object corresponding to the balloon
     * @param elapsedTime - elapsed time since the start of the Game
     */
    public Balloon(Context context, BalloonType type, int x, int y, int radius, int elapsedTime) {
        this.context = context;
        this.type = type;
        bitmap = setBalloonColour();
        circle = new Circle(x, y, radius);
        //Speed = base speed + time based speed + randomSpeed(rnd boundaries increasing with time)
        this.speed = 20 + elapsedTime/4 + randSpeed(elapsedTime);
        this.x = x;
        this.y = y;
        popped = false;
    }

    /**
     * Class used for randomising and assigning colours to the appropriate balloons
     * @return Balloon object bitmap
     */
    public Bitmap setBalloonColour() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_red);

        Random rand = new Random();
        int randomNum = rand.nextInt(4);

        if(randomNum == 0) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_red);
        } else if(randomNum == 1) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_blue);
        } else if(randomNum == 2) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_green);
        } else if(randomNum == 3) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_yellow);
        }
        if(type == BalloonType.BLACK) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_black);
        } else if (type == BalloonType.RAINBOW) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon_rainbow);
        }
        return bitmap;
    }

    /**
     * Method which returns the circle object.
     * @return  - circle object
     */
    public Circle getCircle() { return circle; }

    /**
     * Method used for updating the balloon position on the screen.
     * Decrements the Y coordinate of the balloon object as well as the corresponding circle.
     * @param speed - variable which determines the movement speed of the object on screen
     */
    public void updatePosition(int speed) {
        y -= speed;
        circle.y -= speed;
    }

    /**
     * Method for generating a random movement speed for the balloon object.
     * @param elapsedTime - time elapsed since the start of the Game
     * @return randomSpeed integer
     */
    public static int randSpeed(int elapsedTime){
        if(elapsedTime >= 60) {elapsedTime = 60;}
        Random rand = new Random();
        int randomSpeed = rand.nextInt(elapsedTime);
        return randomSpeed;
    }

    /**
     * Draw method for the balloon object. Draws the appropriate bitmap as well as circle object
     * on canvas which is taken as a method parameter.
     * @param canvas - canvas object which the object will be drawn on
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,
                new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
                new Rect((circle.getX() - circle.radius),
                        (circle.getY() - circle.radius),
                        (circle.getX() + circle.radius),
                        (circle.getY() + circle.radius)),
                null);
    }

    /**
     * Touch event handler for the balloon object. Responsible for detection whether the
     * click event has taken place within the boundaries of the object.
     * @param x - X coordinate of the click event
     * @param y - Y coordinate of the click event
     * @return collision boolean, true if collision within object radius, false otherwise
     */
    public boolean handleTouchEvent(float x, float y) {
        boolean collision = false;
            if(x >= getX() - getCircle().radius && y >= getY() - getCircle().radius &&
                    x <= getX() + getCircle().radius && y <= getY() + getCircle().radius){
                collision = true;
            }
            return collision;
    }

    /**
     * Getter for the balloon bitmap
     * @return bitmap graphic for the balloon object
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Setter for the balloon bitmap
     * @param bitmap - graphic used for the balloon object
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Getter for the X coordinate of the balloon object
     * @return X coordinate of the balloon object
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the Y coordinate of the balloon object
     * @return Y coordinate of the balloon object
     */
    public int getY() { return y; }

    /**
     * Setter for the X coordinate of the balloon object
     * @param x - X coordinate of the balloon object
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter for the X coordinate of the balloon object
     * @param y - Y coordinate of the balloon object
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter for the speed of the balloon object
     * @return speed of the balloon object
     */
    public int getSpeed() { return speed; }

    /**
     *Setter for the speed of the balloon object
     * @param speed - speed for the balloon object
     */
    public void setSpeed(int speed) { this.speed = speed; }

    /**
     * Method to check whether the balloon is popped
     * @return true if balloon is popped, false otherwise
     */
    public boolean isPopped() {
        return popped;
    }

    /**
     * Setter for the popped variable of the balloon object
     * @param popped - boolean to check if balloon has been popped
     *               (popped balloons don't draw on screen)
     */
    public void setPopped(boolean popped) {
        this.popped = true;
    }

    /**
     * Getter for the balloon type enum
     * @return Balloon type
     */
    public BalloonType getType() {
        return type;
    }

    /**
     * Method used for setting the balloon type to standard as well as reassigning its bitmap
     */
    public void setTypeStandard() {
        type = BalloonType.STANDARD;
        setBitmap(setBalloonColour());
    }

}
