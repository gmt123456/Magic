package code.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/16
 * @描述 圆
 */

public class Circle extends Geometric{

    private Point leftTop;
    private int diameter;

    public Circle(Point leftTop, int diameter, Paint paint, double lineWidth){
        super(paint, lineWidth);
        this.leftTop = leftTop;
        this.diameter = diameter;
    }

    @Override
    public String toString() {
        return "[Circle]<"+leftTop.toString()+","+diameter+"><"+color.toString()+","+lineWidth+">";
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public int getDiameter() { return diameter; }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    @Override
    public boolean isPointInLine(int x, int y) {
        Point center = new Point(leftTop.getX() + diameter / 2, leftTop.getY() + diameter / 2);
        double distance = center.getDistance(x, y);
        if (Math.abs(distance - (double) diameter / 2) < 2) return true;
        else return false;
    }

    @Override
    public void show(GraphicsContext graphicsContext){
        graphicsContext.save();
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setStroke(color.getPaint());
        graphicsContext.strokeOval(leftTop.getX(), leftTop.getY(), diameter, diameter);
        graphicsContext.restore();
    }

}
