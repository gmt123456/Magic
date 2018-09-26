package code.draw;

import javafx.scene.input.MouseEvent;
import code.CanvasRecord;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述
 */

public class DrawEraser implements DrawStrategy{

    private boolean erasering;
    private CanvasRecord record;

    public DrawEraser(){
        record = CanvasRecord.getInstance();
        erasering = false;
    }

    @Override
    public void onMousePress(MouseEvent event){
        erasering = true;
    }

    @Override
    public void onMouseDrag(MouseEvent event){
        if (erasering) {
            record.eraser((int)event.getX(), (int)event.getY());
        }
    }

    @Override
    public void onMouseRelease(){
        erasering = false;
    }

    @Override
    public void onMouseExit(){
        erasering = false;
    }
}
