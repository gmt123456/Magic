package code;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import code.draw.Draw;
import code.draw.DrawEraser;
import code.draw.DrawFrame;
import code.draw.DrawStroke;
import code.file.FileChooserController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述 负责一切与canvas绘画有关的职责，包括接收用户绘画数据、对各类图形数据进行显示的指责
 */

public class CanvasController implements Initializable{

    @FXML
    private RadioButton oneLine;

    @FXML
    private Slider widthSlider;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Canvas canvasStroke;

    @FXML
    private Canvas canvasBlock;

    @FXML
    private Text position;

    private GraphicsContext gcStroke;
    private GraphicsContext gcFrame;
    private Draw draw;
    private CanvasRecord record;
    private FileChooserController fileChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        gcStroke = canvasStroke.getGraphicsContext2D();
        gcFrame = canvasBlock.getGraphicsContext2D();
        draw = new Draw(new DrawStroke(gcStroke));
        gcStroke.setLineCap(StrokeLineCap.ROUND);
        gcStroke.setLineJoin(StrokeLineJoin.ROUND);
        record = CanvasRecord.getInstance();
        record.init(gcStroke);
        fileChooser = FileChooserController.getInstance();
        position.setText("x: 0  y: 0");
        colorPicker.setValue(Color.BLACK);
        widthSlider.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {
            gcStroke.setLineWidth(new_val.doubleValue());
        });
    }

    public void saveGraphs(){ fileChooser.saveFile(); }

    public void importGraphs(){ fileChooser.importFile(); }

    public void chooseOneLine() { record.oneLine(oneLine.isSelected()); }

    public void last() { record.last(); }

    public void next() { record.next(); }

    public void clearAll() { record.clear(); }

    public void chooseStroke(){
        draw = new Draw(new DrawStroke(gcStroke));
    }

    public void chooseFrame(){
        draw = new Draw(new DrawFrame(gcFrame));
    }

    public void chooseEraser(){ draw = new Draw(new DrawEraser()); }

    public void chooseColor(){
        gcStroke.setStroke(colorPicker.getValue());
    }

    public void onMousePress(MouseEvent event){
        draw.onMousePress(event);
    }

    public void onMouseDrag(MouseEvent event){
        draw.onMouseDrag(event);
        setPosition((int)event.getX(), (int)event.getY());
    }

    public void onMouseRelease(){
        draw.onMouseRelease();
    }

    public void onMouseMove(MouseEvent event){
        setPosition((int)event.getX(), (int)event.getY());
    }

    public void onMouseExit(){
        draw.onMouseExit();
        setPosition(0, 0);
    }

    private void setPosition(int x, int y){
        position.setText("x: " + x + "  y: " + y);
    }

}
