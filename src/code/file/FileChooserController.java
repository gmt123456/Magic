package code.file;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import code.CanvasRecord;
import code.shape.Graphs;

import java.io.File;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/23
 * @描述
 */
public class FileChooserController {

    private Stage stage;
    private static FileChooserController instance = new FileChooserController();

    private FileChooserController(){}

    public static FileChooserController getInstance(){ return instance; }

    public void init(Stage stage){ this.stage = stage; }

    public void saveFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        configureFileChooser(fileChooser);
        fileChooser.setInitialFileName("untitled.dat");
        CanvasRecord record = CanvasRecord.getInstance();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            FileConvert.writeObjectToFile(record.getGraphs(), file);
        }
    }

    public void importFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import");
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Graphs graphs = (Graphs) FileConvert.readObjectFromFile(file);
            CanvasRecord record = CanvasRecord.getInstance();
            record.importGraphs(graphs);
        }
    }

    private static void configureFileChooser(
            FileChooser fileChooser) {
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("DAT", "*.dat")
        );
    }

}
