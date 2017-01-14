package com.yl.knn;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei.yang on 17/1/5.
 */
public class KNNMain {

    private static final String RESULT_DIR = "result";


    public static void main(String[] args) {
        KNNMain t = new KNNMain();
        String datafile = "myfile";
        try {
            List<List<Double>> datas = new ArrayList<List<Double>>();
            t.read(datas, datafile);
            List<List<Double>> testDatas = datas.subList(0,200);
            List<List<Double>> trainDatas = datas.subList(200,datas.size());


            KNN knnObj = new KNN();
            Integer errCount = 0;
            List<Integer>  errorIndex = new ArrayList<Integer>();
            for (int i = 0; i < testDatas.size(); i++) {
                List<Double> test = testDatas.get(i);
                double classify = Double.parseDouble(knnObj.knn(trainDatas, test, 7));
                if(classify != test.get(test.size()-1)){
                    errCount +=1;
                    errorIndex.add(i);
                }
            }
            JSONObject json = new JSONObject();
            json.put("errorCount",errCount);
            json.put("errorRate",errCount.doubleValue()/testDatas.size());
            json.put("errorList",errorIndex.toString());

            DateTime now = new DateTime();
            String fileName = now.toString("yyyyMMddHHmm");
            File file = new File(RESULT_DIR+File.separator+fileName);
            file.createNewFile();

            System.out.println(json.toJSONString());
            FileUtils.writeStringToFile(file,json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据文件中读取数据
     * @param datas 存储数据的集合对象
     * @param path 数据文件的路径
     */
    public void read(List<List<Double>> datas, String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String data = br.readLine();
            while (data != null) {
                String t[] = data.split("\\s+");
                List<Double> l = new ArrayList<>();
                for (int i = 0; i < t.length; i++) {
                    l.add(Double.parseDouble(t[i]));
                }
                datas.add(l);
                data = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
