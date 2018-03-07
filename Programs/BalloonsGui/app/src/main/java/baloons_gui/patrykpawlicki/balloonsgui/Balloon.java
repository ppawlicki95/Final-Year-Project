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

    private Rect rectangle;
    private Bitmap bitmap;
    private int width, height;

    private int x, y;
    private Point point;
    private int color;
    private boolean popped;

    public Balloon(Context context, Rect rect, int color, int x, int y) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon);
        this.rectangle = rect;
        this.color = color;
        this.x = x;
        this.y = y;
        popped = false;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    public void updatePosition(int speed) {
        rectangle.top -= speed;
        rectangle.bottom -= speed;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);

        canvas.drawBitmap(bitmap, rectangle.left, rectangle.top, paint);
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
