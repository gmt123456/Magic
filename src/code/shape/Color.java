package code.shape;

import javafx.scene.paint.Paint;

import java.io.Serializable;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/23
 * @描述
 */
public class Color implements Serializable{

    private String color;

    public Color(Paint color){
        this.color = color.toString();
    }

    @Override
    public String toString(){
        return color;
    }

    public Paint getPaint(){
        Paint paint = javafx.scene.paint.Color.valueOf(color);
        return paint;
    }

    public void setPaint(Paint paint){
        color = paint.toString();
    }
}
