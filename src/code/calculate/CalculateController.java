package code.calculate;

import code.shape.*;

import java.util.ArrayList;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/17
 * @描述 计算控制器，安排线条的处理，提取形状特征
 */
public class CalculateController implements CalculateInterface{

    private int[] pointsX;
    private int[] pointsY;
    private int length;
    private Stroke[] strokes;
    private Geometric geometric;

    public CalculateController(Stroke[] strokes){
        this.strokes = strokes;
        ConnectStroke connectStroke = new ConnectStroke(strokes);
        Stroke newStroke = connectStroke.connect();

        pointsX = new int[newStroke.getAmount()];
        pointsY = new int[newStroke.getAmount()];
        for (int i = 0; i < newStroke.getAmount(); i++){
            Point point = newStroke.getPoint(i);
            pointsX[i] = point.getX();
            pointsY[i] = point.getY();
        }
        length = pointsX.length;
        start();
    }

    public Geometric getGeometric() {
        return geometric;
    }

    private void start(){
        CalculateSuitDegrees suitDegrees = new CalculateSuitDegrees(pointsX, pointsY);
        CalculateGradient gradient = new CalculateGradient(suitDegrees.getSuitDegrees(), pointsX, pointsY);
        ArrayList<Integer> resultI = select(gradient.getGradients());
        if (suitDegrees.isRectangle()) {
            int[] x = selectXOrY(pointsX);
            int minX = x[0];
            int maxX = x[1];
            int[] y = selectXOrY(pointsY);
            int minY = y[0];
            int maxY = y[1];
            Point leftTop = new Point(minX, minY);
            Rectangle rectangle = new Rectangle(leftTop, maxX-minX, maxY-minY, strokes[0].getColor(), strokes[0].getLineWidth());
            geometric = rectangle;
        }
        else {
            if (gradient.isCircle() || resultI.size() < 3) {
                int[] x = selectXOrY(pointsX);
                int minX = x[0];
                int maxX = x[1];
                int[] y = selectXOrY(pointsY);
                int minY = y[0];
                Point leftTop = new Point(minX, minY);
                Circle circle = new Circle(leftTop, maxX-minX, strokes[0].getColor(), strokes[0].getLineWidth());
                geometric = circle;
            }else {
                resultI = selectMax(resultI, 3, gradient.getGradients());
                Point a = new Point(pointsX[resultI.get(0)], pointsY[resultI.get(0)]);
                Point b = new Point(pointsX[resultI.get(1)], pointsY[resultI.get(1)]);
                Point c = new Point(pointsX[resultI.get(2)], pointsY[resultI.get(2)]);
                Triangle triangle = new Triangle(a,b,c,strokes[0].getColor(),strokes[0].getLineWidth());
                geometric = triangle;
            }

        }
    }

    private int[] selectXOrY(int[] pointsXY){
        int[] result = new int[2];
        int minX = Integer.MAX_VALUE;
        int maxX = -1;
        for (int x : pointsXY){
            if (x < minX) minX = x;
            else if (x > maxX) maxX = x;
        }
        result[0] = minX;
        result[1] = maxX;
        return result;
    }

    private ArrayList<Integer> selectMax(ArrayList<Integer> resultI, int num, double[] gradients){
        ArrayList<Integer> maxResultI = new ArrayList<>();
        double maxGradients = -1;
        int maxJ = 0;
        int size = resultI.size();
        for (int t = 0;t < num;t++){
            for (int j = 0;j < size;j++){
                int i = resultI.get(j);
                if (gradients[i] > maxGradients){
                    maxGradients = gradients[i];
                    maxJ = j;
                }
            }
            maxGradients = -1;
            maxResultI.add(resultI.get(maxJ));
            resultI.remove(maxJ);
            size--;
        }
        return maxResultI;
    }

    /**
     * 用均值滤波法对各点曲率进行处理，综合选取极值
     */
    private ArrayList<Integer> select(double[] gradients){
        double coefficientG = 5;    //对曲率的滤波比例系数
        long numbersG = 0;  //曲率总和
        for (int i = 0; i < length; i++){
            numbersG += gradients[i];
        }
        double averageG = numbersG / (double)length;    //曲率平均数
        ArrayList<Integer> specialG = new ArrayList<>();    //曲率特征点的索引
        for (int i = 0; i < length; i++){
            if (gradients[i] > averageG * coefficientG){
                specialG.add(i);
            }
        }

        //对相近的特征点进行选取其中曲率最大的特征点
        if (specialG.isEmpty()) return new ArrayList<Integer>();
        int tempI = specialG.get(0);
        ArrayList<Integer> similarI = new ArrayList<>();
        ArrayList<Integer> resultI = new ArrayList<>();
        for (int i = 0; i < specialG.size(); i++){
            int I = specialG.get(i);    //各个特征点的索引
            if (Math.abs(I - tempI) < 5){
                similarI.add(I);
            }
            if (Math.abs(I - tempI) >= 10 || i == specialG.size() - 1){
                if (!similarI.isEmpty()){
                    double maxGradients = -1;
                    int maxI = 0;
                    for (int j : similarI){
                        if (gradients[j] > maxGradients){
                            maxGradients = gradients[j];
                            maxI = j;
                        }
                    }
                    resultI.add(maxI);
                    similarI.clear();
                    similarI.add(I);
                }else {
                    resultI.add(I);
                }
            }
            tempI = I;
        }
        return resultI;
    }
}
