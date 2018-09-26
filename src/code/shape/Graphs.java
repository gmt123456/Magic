package code.shape;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/23
 * @描述
 */
public class Graphs implements Serializable{

    private ArrayList<Geometric> geometrics;

    public Graphs(ArrayList<Geometric> geometrics){ this.geometrics = geometrics; }

    @Override
    public String toString(){
        String str = "";
        for (Geometric geometric : geometrics){
            str += geometric.toString()+"\n";
        }
        return str;
    }

    public ArrayList<Geometric> getGeometrics() {
        return geometrics;
    }
}
