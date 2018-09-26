package code.draw;

import javafx.scene.input.MouseEvent;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述
 */

public class Draw {

    private DrawStrategy strategy;

    public Draw(DrawStrategy drawStrategy){
        strategy = drawStrategy;
    }

    public void onMousePress(MouseEvent event){
        strategy.onMousePress(event);
    }

    public void onMouseDrag(MouseEvent event){
        strategy.onMouseDrag(event);
    }

    public void onMouseRelease(){
        strategy.onMouseRelease();
    }

    public void onMouseExit(){
        strategy.onMouseExit();
    }

}
