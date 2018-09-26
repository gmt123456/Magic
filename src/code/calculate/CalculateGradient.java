package code.calculate;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/21
 * @描述  根据各点切线角度计算各点的曲率
 */
public class CalculateGradient {

    private int[] pointsX;  //笔划每点的x坐标集合
    private int[] pointsY;  //笔划每点的y坐标集合
    private int length;     //笔划点个数
    private int[] suitDegrees;  //笔划每点的切线角度的集合
    private double[] gradients; //计算出的每点曲率的集合

    public CalculateGradient(int[] suitDegrees, int[] pointsX, int[] pointsY){
        this.suitDegrees = suitDegrees;
        this.pointsX = pointsX;
        this.pointsY = pointsY;
        length = pointsX.length;
        gradients = new double[length];
        calculate();
    }

    /**
     * 根据曲率没有太大极值来确定圆形
     * @return 是否为圆形
     */
    public boolean isCircle(){
        int times = 0;
        for (double g : gradients){
            if (g > 6){
                times++;
            }
        }
        if (times < 6)
        return true;
        else return false;
    }

    public double[] getGradients(){
        return gradients;
    }

    /**
     * 根据每点附近切线角度的变化，除以附近的曲线长度，计算得出每点的曲率
     */
    private void calculate(){
        int precisionGradient = 2;  //曲率精确值，2表明取每个点左右2个点计算曲率
        for (int i = 0; i < length; i++){
            int degreeDiffer = 0;   //每点附近切线角度变化累计
            int distanceDiffer = 0; //每点附近曲线距离累计
            int degreeTemp = -1;    //上一点的切线角度
            int xTemp = -1;         //上一点的x坐标
            int yTemp = -1;         //上一点的y坐标
            for (int j = i - precisionGradient; j <= i + precisionGradient; j++){   //对该点左右附近的点（附点）进行遍历计算曲率
                int degree0;        //附点的切线角度
                int x0;             //附点的x坐标
                int y0;             //附点的y坐标
                if(j < 0){  //第一个点之前的点取与第一个点相同值
                    x0 = pointsX[0];
                    y0 = pointsY[0];
                    degree0 = suitDegrees[0];
                }else if (j >= length){ //最后一个点之后的点取与最后一个点相同值
                    x0 = pointsX[length - 1];
                    y0 = pointsY[length - 1];
                    degree0 = suitDegrees[length - 1];
                }else {
                    x0 = pointsX[j];
                    y0 = pointsY[j];
                    degree0 = suitDegrees[j];
                }
                if (j != i - precisionGradient){    //对除最左边的附点进行与上一个点的距离计算，并记录在此点速度数据之中
                    double distance =  getDistance(xTemp, yTemp, x0, y0);
                    distanceDiffer += distance;
                }
                xTemp = x0;
                yTemp = y0;
                if (degreeTemp != -1){
                    double mindegree = Math.min(degree0, degreeTemp);
                    double maxdegree = Math.max(degree0, degreeTemp);
                    double degree =  Math.min(maxdegree - mindegree, mindegree + 180 - maxdegree);  //计算切线角度差，取模180度
                    degreeDiffer += degree;
                }
                degreeTemp = degree0;
            }
            degreeDiffer = Math.abs(degreeDiffer);
            gradients[i] = degreeDiffer / (double)distanceDiffer;
        }
    }

    private double getDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
    }

}
