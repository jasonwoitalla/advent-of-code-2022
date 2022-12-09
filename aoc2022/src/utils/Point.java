package utils;

import java.util.Objects;

public class Point {
    private int x;
    private int y;
    private int hashCode;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.hashCode = Objects.hash(x, y);
    }

    public void setX(int x) {
        this.x = x;
        this.hashCode = Objects.hash(x, y);
    }

    public void setY(int y) {
        this.y = y;
        this.hashCode = Objects.hash(x, y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void move(int x, int y) {
        setX(this.x + x);
        setY(this.y + y);
    }

    public boolean isTouching(Point other) {
        if(other.equals(this))
            return true;

        int difX = Math.abs(x - other.x);
        int difY = Math.abs(y - other.y);

        return difX < 2 && difY < 2;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        else if(o == null)
            return false;
        else if(o instanceof Point){
            Point other = (Point)o;
            return x == other.x && y == other.y;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return String.format("Point(%d, %d)", x, y);
    }
}
