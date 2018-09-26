package code.file;

import java.io.*;

/**
 * @创建人 高梦婷
 * @创建时间 2018/9/23
 * @描述
 */
public class FileConvert {

    /**
     * 将对象转换成文件格式
     * @param obj 要转换的对象
     * @param file 要存储的文件
     */
    public static void writeObjectToFile(Object obj, File file)
    {
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取对象
     * @param file 要读取的文件
     * @return 返回读取到的对象
     */
    public static Object readObjectFromFile(File file)
    {
        Object temp=null;
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
