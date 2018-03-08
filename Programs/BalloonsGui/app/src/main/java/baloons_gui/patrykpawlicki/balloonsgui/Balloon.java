package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Patryk Pawlicki on 02/02/2018.
 */

public class Balloon implements BalloonController {

    private Context context;
    private Circle circle;
    private Bitmap bitmap;

    private int x, y;
    private int color;
    private boolean popped;

    public Balloon(Context context, int color, int x, int y, int radius) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon2);
        circle = new Circle(x, y, radius);
        this.color = color;
        this.x = x;
        this.y = y;
        popped = false;
    }

    public Circle getCircle() { return circle; }

    public void updatePosition(int speed) {
        y -= speed;
        circle.y -= speed;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawBitmap(bitmap,
                new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
                new Rect((circle.getX() - circle.radius),
                        (circle.getY() - circle.radius),
                        (circle.getX() + circle.radius),
                        (circle.getY() + circle.radius)),
                null);
    }

    @Override
    public void update() { }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPopped() {
        return popped;
    }

    public void setPopped(boolean popped) {
        this.popped = true;
    }

    public void handleTouchEvent(int x, int y) { }

}
