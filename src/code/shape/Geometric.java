package code.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.io.Serializable;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/16
 * @描述 几何图形，所有图形包括笔划共同的父类，所有的图形都有自己的颜色
 */

public abstract class Geometric implements Serializable{

    protected Color color;
    protected double lineWidth;

    protected Geometric(Paint color, double lineWidth){
        this.color = new Color(color);
        this.lineWidth = lineWidth;
    }

    @Override
    public String toString() {
        return "[Geometric]";
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Paint getColor() {
        return color.getPaint();
    }

    public void setColor(Paint color) {
        this.color.setPaint(color);
    }

    public void show(GraphicsContext graphicsContext){}

    public boolean isPointInLine(int x, int y) { return false; }

    public boolean isInFrame(int x1, int y1, int x2, int y2) { return false; }

}
