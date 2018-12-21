package com.nyzc.gdm.currencyratio.Bean;

public class EthAccount {


    /**
     * code : 0
     * msg : ok
     * data : {"eth_address":"0xC98ae7687B542cBc538776CDA5Ab53636e616939","seer_account_id":"1.2.17299","seer_account_account":"bibiguojia12345"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * eth_address : 0xC98ae7687B542cBc538776CDA5Ab53636e616939
         * seer_account_id : 1.2.17299
         * seer_account_account : bibiguojia12345
         */

        private String eth_address;
        private String seer_account_id;
        private String seer_account_account;

        public String getEth_address() {
            return eth_address;
        }

        public void setEth_address(String eth_address) {
            this.eth_address = eth_address;
        }

        public String getSeer_account_id() {
            return seer_account_id;
        }

        public void setSeer_account_id(String seer_account_id) {
            this.seer_account_id = seer_account_id;
        }

        public String getSeer_account_account() {
            return seer_account_account;
        }

        public void setSeer_account_account(String seer_account_account) {
            this.seer_account_account = seer_account_account;
        }
    }
}
