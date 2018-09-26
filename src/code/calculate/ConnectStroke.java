package code.calculate;

import code.shape.Point;
import code.shape.Stroke;

import java.util.ArrayList;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/22
 * @描述  连接多笔画成一条笔划
 */
public class ConnectStroke {

    private Stroke[] strokes;
    private ArrayList<Point> pointsBegin;
    private ArrayList<Point> pointsEnd;
    private ArrayList<Point> newStroke;

    public ConnectStroke(Stroke[] strokes){
        this.strokes = strokes;
        pointsEnd = new ArrayList<>();
        pointsBegin = new ArrayList<>();
        newStroke = new ArrayList<>();
        for (Stroke s : strokes){
            pointsBegin.add(s.getPoint(0));
            pointsEnd.add(s.getPoint(s.getAmount() - 1));
        }
    }

    /**
     * 寻找每一条笔划端点相邻的点并进行连接
     * @return 连接完成之后的一条线条
     */
    public Stroke connect(){
        ArrayList<Integer> recordBegin = new ArrayList<>();//记录已经经过的线的起始点
        ArrayList<Integer> recordEnd = new ArrayList<>();//记录已经经过的线的结束点
        boolean isBegin = true;
        for (int i = 0;;){
            Stroke stroke = strokes[i];
            if (isBegin) addStroke(stroke);
            else addReverseStroke(stroke);
            Point p;
            if (isBegin){
                p = stroke.getPoint(stroke.getAmount() - 1);  //最后一个点
                recordEnd.add(i);
            }else {
                p = stroke.getPoint(0);
                recordBegin.add(i);
            }

            double minDistance = Double.MAX_VALUE;
            Point p0 = p;   //找出距离最小的其它线条的起始点

            for (int t = 0; t < pointsBegin.size(); t++){
                if (recordBegin.contains(t)) continue;  //不与已经被选中的点相连
                double distance = p.getDistance(pointsBegin.get(t).getX(), pointsBegin.get(t).getY());
                if (distance < minDistance){
                    isBegin = true;
                    minDistance = distance;
                    p0 = pointsBegin.get(t);
                    i = t;
                }
            }

            for (int t = 0; t < pointsEnd.size(); t++){
                if (recordEnd.contains(t)) continue;  //不与已经被选中的点相连
                double distance = p.getDistance(pointsEnd.get(t).getX(), pointsEnd.get(t).getY());
                if (distance < minDistance){
                    isBegin = false;
                    minDistance = distance;
                    p0 = pointsEnd.get(t);
                    i = t;
                }
            }

            if (isBegin) recordBegin.add(i);
            else recordEnd.add(i);
            addStrokeFromPoints(p, p0);//在两点之间进行连接
            if (recordBegin.size() == pointsBegin.size() && recordEnd.size() == pointsEnd.size()) {
                //添加一些初始的点
                Stroke stroke1 = strokes[0];
                for (int j = 0;j <= 5;j++){
                    newStroke.add(stroke1.getPoint(j));
                }
                break;
            }
        }

        return new Stroke(newStroke, strokes[0].getColor(), strokes[0].getLineWidth());
    }

    private void addStroke(Stroke stroke){
        for (int j = 0; j < stroke.getAmount(); j++){
            newStroke.add(stroke.getPoint(j));
        }
    }

    private void addReverseStroke(Stroke stroke){
        for (int j = stroke.getAmount() - 1; j >= 0; j--){
            newStroke.add(stroke.getPoint(j));
        }
    }

    /**
     * 用于在两点间添加直线上的点，顺序为从起始点开始以固定斜率逼近结束点
     * @param pointBegin 起始点
     * @param pointEnd 结束点
     */
    private void addStrokeFromPoints(Point pointBegin, Point pointEnd){
        double k = Math.abs((pointEnd.getY() - pointBegin.getY()) / (double)(pointEnd.getX() - pointBegin.getX()));//两点间直线的斜率
        for (int x = pointBegin.getX() + 1;;){
            if (pointEnd.getX() >= pointBegin.getX()){
                if (x >= pointEnd.getX()) break;
            }
            else {
                if (x <= pointEnd.getX()) break;
            }
            //计算新点y坐标值
            int y = (int)(Math.abs(pointBegin.getX() - x) * k);
            if (pointEnd.getY() < pointBegin.getY()) y = -y;
            y += pointBegin.getY();
            //判断x变化的方向
            if (pointEnd.getX() > pointBegin.getX()) x++;
            else x--;
            Point p0 = new Point(x, y);

            newStroke.add(p0);
        }

    }

}
