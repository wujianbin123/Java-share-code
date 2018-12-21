package com.nyzc.gdm.currencyratio.Bean;

import java.io.Serializable;

public class UserOrderBean implements Serializable{
    private String id;
    private String roomTitle;
    private String roomSelect;
    private String roomSelectAmount;
    private String joinRoomTime;
    private String reward;
    private double selectFinalResult;

    public double getSelectFinalResult() {
        return selectFinalResult;
    }

    public void setSelectFinalResult(double selectFinalResult) {
        this.selectFinalResult = selectFinalResult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomSelect() {
        return roomSelect;
    }

    public void setRoomSelect(String roomSelect) {
        this.roomSelect = roomSelect;
    }

    public String getRoomSelectAmount() {
        return roomSelectAmount;
    }

    public void setRoomSelectAmount(String roomSelectAmount) {
        this.roomSelectAmount = roomSelectAmount;
    }

    public String getJoinRoomTime() {
        return joinRoomTime;
    }

    public void setJoinRoomTime(String joinRoomTime) {
        this.joinRoomTime = joinRoomTime;
    }

    @Override
    public String toString() {
        return "UserOrderBean{" +
                "roomTitle='" + roomTitle + '\'' +
                ", roomSelect='" + roomSelect + '\'' +
                ", roomSelectAmount='" + roomSelectAmount + '\'' +
                ", joinRoomTime='" + joinRoomTime + '\'' +
                ", reward='" + reward + '\'' +
                '}';
    }
}
