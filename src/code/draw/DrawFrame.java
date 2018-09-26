package code.draw;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import code.CanvasRecord;
import code.shape.Point;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/12
 * @描述 策略模式中用来画识别框的画笔
 */
public class DrawFrame implements DrawStrategy{

    private GraphicsContext gcFrame;
    private double canvasHeight;
    private double canvasWidth;
    private Point startPoint;
    private Point endPoint;
    private boolean drawing;
    private CanvasRecord record;

    //初始化变量，初始化笔触
    public DrawFrame(GraphicsContext gcFrame){
        this.gcFrame = gcFrame;
        startPoint = new Point(0, 0);
        endPoint = new Point(0, 0);
        gcFrame.setStroke(Color.rgb(131, 131, 231));
        gcFrame.setFill(Color.rgb(131, 131, 231, 0.1));
        canvasHeight = gcFrame.getCanvas().getHeight();
        canvasWidth = gcFrame.getCanvas().getWidth();
        record = CanvasRecord.getInstance();
    }

    @Override
    public void onMousePress(MouseEvent event){
        drawing = true;
        startPoint.setX((int)event.getX());
        startPoint.setY((int)event.getY());
    }

    @Override
    public void onMouseDrag(MouseEvent event){
        if (drawing){
            endPoint.setX((int)event.getX());
            endPoint.setY((int)event.getY());
            gcFrame.clearRect(0, 0, canvasWidth, canvasHeight);
            gcFrame.beginPath();
            gcFrame.rect(rectX((int)event.getX()), rectY((int)event.getY()), rectW((int)event.getX()), rectH((int)event.getY()));
            gcFrame.stroke();
            gcFrame.fill();
            gcFrame.closePath();
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

    private void endDraw(){
        gcFrame.clearRect(0, 0, canvasWidth, canvasHeight);
        drawing = false;
        record.frame(startPoint, endPoint);
    }

    private int rectX(int x){
        return Math.min(startPoint.getX(), x);
    }

    private int rectY(int y){
        return Math.min(startPoint.getY(), y);
    }

    private int rectW(int x){
        return Math.abs(startPoint.getX() - x);
    }

    private int rectH(int y){
        return Math.abs(startPoint.getY() - y);
    }

}
