package baloons_gui.patrykpawlicki.balloonsgui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Patryk Pawlicki on 02/02/2018.
 */

public class Balloon implements BalloonController {

    private Rect rectangle;
    private Bitmap bitmap;
    private int width, height;

    private int x, y;
    private int color;
    private boolean popped;

    public Balloon(Rect rect, int color, int x, int y) {
        //this.bitmap = bitmap;
        this.rectangle = rect;
        this.color = color;
        this.x = x;
        this.y = y;
        popped = false;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() { }

    @Override
    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
    }

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
        this.popped = popped;
    }

    public void handleTouchEvent(int x, int y) { }

}
