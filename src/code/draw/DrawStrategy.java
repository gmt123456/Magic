package code.draw;

import javafx.scene.input.MouseEvent;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述
 */

public interface DrawStrategy {

    void onMousePress(MouseEvent event);
    void onMouseDrag(MouseEvent event);
    void onMouseRelease();
    void onMouseExit();

}
