package com.yl.knn;

/**
 * Created by lei.yang on 17/1/4.
 */
public class TrainTuple implements Comparable{

    private int index;   //训练集中的索引
    private double  distance;   //和测试数据的距离
    private String  classify;   //类别

    public TrainTuple(int index,double distance,String classify) {
        this.classify = classify;
        this.distance = distance;
        this.index = index;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    /**
     * 距离越小，优先级越高
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if(o instanceof TrainTuple){
            TrainTuple obj = (TrainTuple)o;
            if(this.distance >= obj.getDistance()){
                return 0;
            }else{
                return 1;
            }
        }else{
           throw new RuntimeException("不能进行compare");
        }
    }
}
