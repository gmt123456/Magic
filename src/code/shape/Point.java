package code.shape;

import java.io.Serializable;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述
 */

public class Point implements Serializable{

    private int x;
    private int y;

    public Point(int a, int b){
        x = a;
        y = b;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPointBetween(Point p2, int x0, int y0){
        int x2 = p2.getX();
        int y2 = p2.getY();
        int minx = Math.min(x, x2);
        int maxx = Math.max(x, x2);
        int miny = Math.min(y, y2);
        int maxy = Math.max(y, y2);
        if (minx <= x0 && x0 <= maxx && miny <= y0 && y0 <= maxy) return true;
        else return false;
    }

    public double getDistance(int x0, int y0){
        return Math.sqrt((y - y0)*(y - y0) + (x - x0)*(x - x0));
    }
}
