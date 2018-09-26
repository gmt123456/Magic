package code.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/16
 * @描述 三角形
 */

public class Triangle extends Geometric{

    private Point pointA;
    private Point pointB;
    private Point pointC;

    public Triangle(Point a, Point b, Point c, Paint paint, double lineWidth){
        super(paint, lineWidth);
        pointA = a;
        pointB = b;
        pointC = c;
    }

    @Override
    public String toString() {
        return "[Triangle]<"+pointA.toString()+","+pointB.toString()+","+pointC.toString()+"><"+color.toString()+","+lineWidth+">";
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public Point getPointC() {
        return pointC;
    }

    @Override
    public boolean isPointInLine(int x, int y) {
        int[] scope = getScope();
        int minX = scope[0];
        int maxX = scope[1];
        int minY = scope[2];
        int maxY = scope[3];
        if (minX < x && x < maxX && minY < y && y < maxY) {
            if (pointA.isPointBetween(pointB, x, y) || pointB.isPointBetween(pointC, x, y) || pointC.isPointBetween(pointA, x, y)) return true;
            else return false;
        }else return false;
    }

    @Override
    public void show(GraphicsContext graphicsContext){
        double[] x = {pointA.getX(), pointB.getX(), pointC.getX()};
        double[] y = {pointA.getY(), pointB.getY(), pointC.getY()};
        graphicsContext.save();
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setStroke(color.getPaint());
        graphicsContext.strokePolygon(x, y, 3);
        graphicsContext.restore();
    }

    private int[] getScope(){
        int[] scope = new int[4];
        int maxX = pointA.getX();
        int minX = maxX;
        int maxY = pointA.getY();
        int minY = maxY;
        int x = pointB.getX();
        int y = pointB.getY();
        if (x < minX) minX = x;
        if (x > maxX) maxX = x;
        if (y < minY) minY = y;
        if (y > maxY) maxY = y;
        x = pointC.getX();
        y = pointC.getY();
        if (x < minX) minX = x;
        if (x > maxX) maxX = x;
        if (y < minY) minY = y;
        if (y > maxY) maxY = y;
        scope[0] = minX;
        scope[1] = maxX;
        scope[2] = minY;
        scope[3] = maxY;
        return scope;
    }
}
