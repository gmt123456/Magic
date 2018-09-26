package code.draw;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import code.CanvasRecord;
import code.shape.Point;
import code.shape.Stroke;

import java.util.ArrayList;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述 策略模式实现接口的实体类，允许用户画出跟随鼠标轨迹的曲线，并存储笔划stroke。
 */

public class DrawStroke implements DrawStrategy{

    private GraphicsContext gcStroke;
    private ArrayList<Point> drawingLine;
    private CanvasRecord record;
    private boolean drawing;

    public DrawStroke(GraphicsContext gcStroke){
        this.gcStroke = gcStroke;
        record = CanvasRecord.getInstance();
        drawingLine = new ArrayList<>();
    }

    @Override
    public void onMousePress(MouseEvent event){
        drawing = true;
        gcStroke.moveTo(event.getX(), event.getY());
        gcStroke.beginPath();
        drawingLine.add(new Point((int)event.getX(), (int)event.getY()));
    }

    @Override
    public void onMouseDrag(MouseEvent event){
        if(drawing){
            gcStroke.lineTo(event.getX(), event.getY());
            gcStroke.stroke();
            gcStroke.moveTo(event.getX(), event.getY());
            drawingLine.add(new Point((int)event.getX(), (int)event.getY()));
        }
    }

    @Override
    public void onMouseRelease(){
        if (drawing){
            endDraw();
        }
    }

    @Override
    public void onMouseExit(){
        if (drawing){
            endDraw();
        }
    }

    //结束绘制、闭合路径，若不为空路径，则存储
    private void endDraw(){
        drawing = false;
        gcStroke.closePath();
        if (drawingLine.size() > 10){
            Stroke stroke = new Stroke(drawingLine, gcStroke.getStroke(), gcStroke.getLineWidth());
            drawingLine.clear();
            record.store(stroke);
        }
    }

}
