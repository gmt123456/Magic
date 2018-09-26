package code.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述 一条笔划，由用户画得，由许多点组成。包含线条宽度和颜色信息。
 */

public class Stroke extends Geometric{

    private ArrayList<Point> points;

    public Stroke(ArrayList<Point> points, Paint paint, double lineWidth){
        super(paint, lineWidth);
        this.points = new ArrayList<>(points);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0;i < points.size();i++){
            str += points.get(i).toString();
            if (i != points.size() - 1) str += ",";
        }
        return "[Stroke]<"+str+"><"+color.toString()+","+lineWidth+">";
    }

    public Point getPoint(int index){
        return points.get(index);
    }

    public int getAmount() {
        return points.size();
    }

    @Override
    public boolean isInFrame(int x1, int y1, int x2, int y2){
        int[] scope = getScope();
        int minX = scope[0];
        int maxX = scope[1];
        int minY = scope[2];
        int maxY = scope[3];
        if (minX >= x1 && minY >= y1 && maxX <= x2 && maxY <= y2) return true;
        else return false;
    }

    @Override
    public boolean isPointInLine(int x, int y) {
        int[] scope = getScope();
        int minX = scope[0];
        int maxX = scope[1];
        int minY = scope[2];
        int maxY = scope[3];
        if (minX <= x && x <= maxX && minY <= y && y <= maxY) {
            for (int i = 0; i < points.size() - 1; i++) {
                if (points.get(i).isPointBetween(points.get(i + 1), x, y)) return true;
            }
            return false;
        }else
            return false;
    }

    @Override
    public void show(GraphicsContext graphicsContext){
        Point point = points.get(0);
        graphicsContext.save();
        graphicsContext.setStroke(color.getPaint());
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.moveTo(point.getX(), point.getY());
        graphicsContext.beginPath();
        for (int i = 1; i < points.size(); i++){
            point = points.get(i);
            graphicsContext.lineTo(point.getX(), point.getY());
        }
        graphicsContext.stroke();
        graphicsContext.closePath();
        graphicsContext.restore();
    }

    private int[] getScope(){
        int[] scope = new int[4];
        int minX = Integer.MAX_VALUE;
        int maxX = -1;
        int minY = Integer.MAX_VALUE;
        int maxY = -1;
        for (Point p : points) {
            int x = p.getX();
            int y = p.getY();
            if (x < minX){
                minX = x;
            }
            else if (x > maxX){
                maxX = x;
            }
            if (y < minY){
                minY = y;
            }
            else if (y > maxY){
                maxY = y;
            }
        }
        scope[0] = minX;
        scope[1] = maxX;
        scope[2] = minY;
        scope[3] = maxY;
        return scope;
    }
}
