package baloons_gui.patrykpawlicki.balloonsgui;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Patryk Pawlicki on 07/03/2018.
 */

/**
 * Circle class used as a base of the balloon object
 */
public final class Circle implements Parcelable {

    public int x;
    public int y;
    private Point center;
    public int radius;

    /**
     * Circle object constructor
     * @param x - X coordinate
     * @param y - Y coordinate
     * @param radius - radius of the circle
     */
    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        setCenter();
    }

    /**
     * Method used for setting the circle variables
     * @param c - circle object
     */
    public void set(Circle c) {
        this.x = c.x;
        this.y = c.y;
        this.radius = c.radius;
        setCenter();
    }

    /**
     * Method used for setting the center point of the circle
     */
    private void setCenter() {
        center = new Point(x, y);
    }

    /**
     * Getter method for the Circle X coordinate
     * @return Circle X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter method for the Circle Y coordinate
     * @return Circle Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Getter method for the Circle radius
     * @return circle radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Getter method for the Circle center
     * @return center of the circle object
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Circle constructor for the use with parcels
     * @param in - parcel container
     */
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
