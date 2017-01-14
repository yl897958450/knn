package com.yl.knn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lei.yang on 17/1/4.
 */
public class KNN {

    public String knn(List<List<Double>> datas, List<Double> testData, int k) {
        MinHeapPriorityQueue queue = new MinHeapPriorityQueue(k);
        for (int i = 0; i < datas.size(); i++) {
            List<Double> t = datas.get(i);
            double distance = calDistance(t, testData);
            queue.insert(new TrainTuple(i, distance, t.get(t.size() - 1).toString()));
        }
        return getMostClass(queue);
    }

    /**
     * 计算测试数据和训练数据元组的距离
     *
     * @param trainData
     * @param testData
     * @return
     */
    private double calDistance(List<Double> trainData, List<Double> testData) {
        double sum = 0d;
        double distance = 0d;
        for (int i = 0; i < trainData.size() - 1 ; i++) {
            sum += (trainData.get(i) - testData.get(i)) * (trainData.get(i) - testData.get(i));
        }
        distance = Math.sqrt(sum);
        return distance;
    }

    /**
     * 获取所得到的k个最近邻元组的多数类别
     *
     * @param queue
     * @return 多数类别名称
     */
    private String getMostClass(MinHeapPriorityQueue queue) {
        Map<String, Integer> classCountMap = new HashMap<>();
        List<TrainTuple> arrayList = queue.getList();
        for (int i = 0; i < arrayList.size(); i++) {
            TrainTuple tuple = arrayList.get(i);
            String classify = tuple.getClassify();
            if(classCountMap.containsKey(classify)){
                classCountMap.put(tuple.getClassify(),classCountMap.get(classify) + 1);
            }else{
                classCountMap.put(classify,1);
            }
        }
        int maxIndex = -1;
        int maxCount = 0;
        Object[] classes = classCountMap.keySet().toArray();
        for (int i = 0; i < classes.length; i++) {
            if (classCountMap.get(classes[i]) > maxCount) {
                maxIndex = i;
                maxCount = classCountMap.get(classes[i]);
            }
        }
        return classes[maxIndex].toString();
    }

}
