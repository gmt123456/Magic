package code.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/16
 * @描述 矩形
 */

public class Rectangle extends Geometric{

    private Point leftTop;
    private int width;
    private int height;

    public Rectangle(Point p, int w, int h, Paint paint, double lineWidth){
        super(paint, lineWidth);
        leftTop = p;
        width = w;
        height = h;
    }

    @Override
    public String toString() {
        return "[Rectangle]<"+leftTop.toString()+","+width+","+height+"><"+color.toString()+","+lineWidth+">";
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean isPointInLine(int x, int y) {
        int minX = leftTop.getX();
        int minY = leftTop.getY();
        int maxX = minX + width;
        int maxY = minY + height;
        if ((isSame(x, minX) || isSame(x, maxX)) && (minY <= y && y <= maxY)) return true;
        else if ((isSame(y, minY) || isSame(y, maxY)) && (minX <= x && x <= maxX)) return true;
        else return false;
    }

    @Override
    public void show(GraphicsContext graphicsContext){
        graphicsContext.save();
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.setStroke(color.getPaint());
        graphicsContext.strokeRect(leftTop.getX(), leftTop.getY(), width, height);
        graphicsContext.restore();
    }

    private boolean isSame(int a, int b) {
        if (Math.abs(a - b) < 3) return true;
        else return false;
    }
}
