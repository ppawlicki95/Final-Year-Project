package baloons_gui.patrykpawlicki.balloonsgui;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Patryk Pawlicki on 07/03/2018.
 */

public final class Circle implements Parcelable {

    public int x;
    public int y;
    private Point center;
    public int radius;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        setCenter();
    }

    public void set(Circle c) {
        this.x = c.x;
        this.y = c.y;
        this.radius = c.radius;
        setCenter();
    }

    private void setCenter() {
        center = new Point(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    protected Circle(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        center = in.readParcelable(Point.class.getClassLoader());
        radius = in.readInt();
    }

    public static final Creator<Circle> CREATOR = new Creator<Circle>() {
        @Override
        public Circle createFromParcel(Parcel in) {
            return new Circle(in);
        }

        @Override
        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(x);
        parcel.writeInt(y);
        parcel.writeParcelable(center, i);
        parcel.writeFloat(radius);
    }
}
