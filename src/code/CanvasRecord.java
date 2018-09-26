package code;

import javafx.scene.canvas.GraphicsContext;

import code.calculate.CalculateController;
import code.calculate.CalculateInterface;
import code.shape.Geometric;
import code.shape.Graphs;
import code.shape.Point;
import code.shape.Stroke;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.ArrayList;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/19
 * @描述 记录画布上图形的信息，负责缓存历史图形，存储线条等
 */
public class CanvasRecord {

    private static CanvasRecord instance = new CanvasRecord();
    private GraphicsContext graphicsContext;
    private ArrayList<Geometric> graphs = new ArrayList<>();
    private ArrayList<Geometric[]> cache = new ArrayList<>();
    private boolean oneLine = false;
    private int cacheIndex = -1;
    private int cacheMaxSize = 10;

    private CanvasRecord(){}

    public static CanvasRecord getInstance(){
        return instance;
    }

    public void importGraphs(Graphs graphs){
        cache.clear();
        this.graphs = graphs.getGeometrics();
        cacheIndex = -1;
        pushCache();
        showCacheIndex();
    }

    public Graphs getGraphs(){
        Graphs g = new Graphs(graphs);
        return g;
    }

    public void init(GraphicsContext gc){
        graphicsContext = gc;
        pushCache();
    }

    public void oneLine(boolean open){
        oneLine = open;
    }

    public void frame(Point startPoint, Point endPoint){
        ArrayList<Geometric> geometrics = new ArrayList<>();
        for (Geometric geometric : graphs){
            if (geometric.isInFrame(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY())) geometrics.add(geometric);
        }
        if (!geometrics.isEmpty()) {
            changeGraphs(geometrics);
        }
    }

    public void eraser(int x, int y){
        for (Geometric geometric : graphs){
            if (geometric.isPointInLine(x, y)){
                graphs.remove(geometric);
                pushCache();
                showCacheIndex();
                break;
            }
        }
    }

    public void clear(){
        graphs.clear();
        pushCache();
        showCacheIndex();
    }

    public void store(Geometric geometric){
        graphs.add(geometric);
        pushCache();
        if (oneLine) {
            ArrayList<Geometric> geometrics = new ArrayList<>();
            geometrics.add(geometric);
            changeGraphs(geometrics);
        }
    }

    public void last(){
        if (cacheIndex > 0){
            cacheIndex--;
            showCacheIndex();
        }
    }

    public void next(){
        if (cacheIndex < cache.size() - 1){
            cacheIndex++;
            showCacheIndex();
        }
    }

    private void showCacheIndex(){
        Geometric[] geometrics = cache.get(cacheIndex);
        graphs.clear();
        graphicsContext.clearRect(0,0,graphicsContext.getCanvas().getWidth(),graphicsContext.getCanvas().getHeight());
        for (Geometric g : geometrics){
            g.show(graphicsContext);
            graphs.add(g);
        }
    }

    /**
     * 删除将被覆盖的存储，并将此时的画布数据存储起来
     */
    private void pushCache(){
        if (cache.size() > cacheIndex + 1){
            int size = cache.size();
            for (int i = size - 1; i >= cacheIndex + 1; i--){
                cache.remove(i);
            }
        }
        Geometric[] storeGraphs = new Geometric[graphs.size()];
        for (int i = 0; i < graphs.size(); i++){
            storeGraphs[i] = graphs.get(i);
        }
        if (cache.size() >= cacheMaxSize) {
            cache.remove(0);
            cacheIndex--;
        }
        cache.add(storeGraphs);
        cacheIndex++;
    }

    private void changeGraphs(ArrayList<Geometric> geometrics) {
        showAudio();
        ArrayList<Stroke> strokelist = new ArrayList<>();
        for (Geometric geometric : geometrics) {
            strokelist.add((Stroke) geometric);
        }
        Stroke[] strokes = strokelist.toArray(new Stroke[strokelist.size()]);
        CalculateInterface calculate = new CalculateController(strokes);
        for (Geometric geometric : geometrics) {
            graphs.remove(geometric);
        }
        graphs.add(calculate.getGeometric());
        pushCache();
        showCacheIndex();
    }

    private void showAudio(){
        try {
            AudioClip plonkSound = new AudioClip(this.getClass().getResource("/audio/success.wav").toString());
            plonkSound.play();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
