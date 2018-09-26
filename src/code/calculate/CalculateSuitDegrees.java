package code.calculate;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/21
 * @描述  根据各点的坐标计算各点的切线角度
 */
public class CalculateSuitDegrees {

    private int[] pointsX;  //笔划每点的x坐标集合
    private int[] pointsY;  //笔划每点的y坐标集合
    private int length;     //笔划点个数
    private int[] suitDegrees;  //计算出的每个点的切线角度变化的集合

    public CalculateSuitDegrees(int[] pointsX, int[] pointsY){
        this.pointsX = pointsX;
        this.pointsY = pointsY;
        length = pointsX.length;
        suitDegrees = new int[length];
        calculate();
    }

    public int[] getSuitDegrees(){ return suitDegrees; }

    /**
     * 根据每点的角度判断是否为矩形
     * @return 是否为矩形
     */
    public boolean isRectangle(){
        int nums90 = 0;
        int nums0 = 0;
        for (int degree : suitDegrees){
            if (Math.abs(degree - 90) < 30) nums90++;
            else if (Math.abs(degree - 0) < 30) nums0++;
        }
        if ((nums0 + nums90) / (double)length > 0.8) return true;   //如果90度和0度的点占比很大的话就是矩形
        else return false;
    }

    private void calculate(){
        int precisionDegree = 3;    //度数精度，3代表角度以3度的单位逐步递增
        int precisionPoints = 5;    //切线角度计算精度，5代表以每点左右分别5个点计算该点的切线角度
        int minDegree = 0;          //度数以0度开始
        int maxDegree = 180;        //度数以180度截止（不等于180度）
        for (int i = 0; i < length; i++){
            int xi = pointsX[i];    //每点的x坐标
            int yi = pointsY[i];    //每点的y坐标
            double minDistance = Double.MAX_VALUE;
            int suitableDegree = 0;
            for (int degree = minDegree; degree < maxDegree; degree += precisionDegree){    //遍历每个度数，找出何时点左右5点到直线的距离和是最小的
                double k = Math.tan(Math.toRadians(degree));    //tan度数，即为切线斜率
                double A = k;   //切线直线方程系数a
                double B = -1;  //切线直线方程系数b
                double C = yi - k * xi; //切线直线方程系数c
                double distanceAll = 0; //各点到切线直线的距离累计和
                for (int j = i - precisionPoints; j <= i + precisionPoints; j++){   //对该点左右附近的点（附点）进行遍历计算到直线的距离
                    int x0; //附点的x坐标
                    int y0; //附点的y坐标
                    if(j < 0){  //第一个点之前的点取与第一个点相同值
                        x0 = pointsX[0];
                        y0 = pointsY[0];
                    }else if (j >= length){ //最后一个点之后的点取与最后一个点相同值
                        x0 = pointsX[length - 1];
                        y0 = pointsY[length - 1];
                    }else {
                        x0 = pointsX[j];
                        y0 = pointsY[j];
                    }
                    double distance =  Math.abs(A*x0+B*y0+C) / Math.sqrt(A*A+B*B);  //计算出该附点到切线直线的距离
                    distanceAll += distance;
                }
                if (distanceAll < minDistance){ //找到最小的距离，记录此时的切线角度
                    minDistance = distanceAll;
                    suitableDegree = degree;
                }
            }
            suitDegrees[i] = suitableDegree;
        }
    }

}
