package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import code.file.FileChooserController;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/11
 * @描述
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FileChooserController controller = FileChooserController.getInstance();
        controller.init(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("canvas.fxml"));
        primaryStage.setTitle("Magic");
        primaryStage.setScene(new Scene(root, 1150, 750));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("image/magic.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
