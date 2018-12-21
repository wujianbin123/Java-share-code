package com.nyzc.gdm.currencyratio.Bean;

import java.util.List;

public class RoomInfo {


    /**
     * fee : {"amount":500000,"asset_id":"1.3.0"}
     * issuer : 1.2.16371
     * room : 1.15.560
     * type : 0
     * input : [0]
     * input1 : []
     * input2 : []
     * amount : 10000000
     */

    private FeeBean fee;
    private String issuer;
    private String room;
    private int type;
    private int amount;
    private List<Integer> input;
    private List<?> input1;
    private List<?> input2;

    public FeeBean getFee() {
        return fee;
    }

    public void setFee(FeeBean fee) {
        this.fee = fee;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Integer> getInput() {
        return input;
    }

    public void setInput(List<Integer> input) {
        this.input = input;
    }

    public List<?> getInput1() {
        return input1;
    }

    public void setInput1(List<?> input1) {
        this.input1 = input1;
    }

    public List<?> getInput2() {
        return input2;
    }

    public void setInput2(List<?> input2) {
        this.input2 = input2;
    }

    public static class FeeBean {
        /**
         * amount : 500000
         * asset_id : 1.3.0
         */

        private int amount;
        private String asset_id;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getAsset_id() {
            return asset_id;
        }

        public void setAsset_id(String asset_id) {
            this.asset_id = asset_id;
        }
    }
}
