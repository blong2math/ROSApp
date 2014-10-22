package app.ros.com.rosapp.control.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Huxley on 2014/10/22.
 */
public class TravelInfoBean implements Serializable{

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "startTime")
    private String startTime;

    @JSONField(name = "arrivalTime")
    private String arrivalTime;

    @JSONField(name = "narrowState")
    private String narrowState;

    public TravelInfoBean(String s, String a, String n){
        startTime = s;
        arrivalTime = a;
        narrowState = n;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNarrowState() {
        return narrowState;
    }

    public void setNarrowState(String narrowState) {
        this.narrowState = narrowState;
    }
}
