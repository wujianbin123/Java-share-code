package com.nyzc.gdm.currencyratio.Base;

import java.io.Serializable;

public class AppLocalWalletUser implements Serializable{

    private String LocalName;
    private String LocalPwd;
    private String LocalBrainKey;
    private String LocalOwnerPublicKey;
    private String LocalOwnerPrivateKey;
    private String LocalActivePublicKey;
    private String LocalActivePrivateKey;
    private String amoutMoney;

    public String getAmoutMoney() {
        return amoutMoney;
    }

    public void setAmoutMoney(String amoutMoney) {
        this.amoutMoney = amoutMoney;
    }

    public String getLocalName() {
        return LocalName;
    }

    public void setLocalName(String localName) {
        LocalName = localName;
    }

    public String getLocalPwd() {
        return LocalPwd;
    }

    public void setLocalPwd(String localPwd) {
        LocalPwd = localPwd;
    }

    public String getLocalBrainKey() {
        return LocalBrainKey;
    }

    public void setLocalBrainKey(String localBrainKey) {
        LocalBrainKey = localBrainKey;
    }

    public String getLocalOwnerPublicKey() {
        return LocalOwnerPublicKey;
    }

    public void setLocalOwnerPublicKey(String localOwnerPublicKey) {
        LocalOwnerPublicKey = localOwnerPublicKey;
    }

    public String getLocalOwnerPrivateKey() {
        return LocalOwnerPrivateKey;
    }

    public void setLocalOwnerPrivateKey(String localOwnerPrivateKey) {
        LocalOwnerPrivateKey = localOwnerPrivateKey;
    }

    public String getLocalActivePublicKey() {
        return LocalActivePublicKey;
    }

    public void setLocalActivePublicKey(String localActivePublicKey) {
        LocalActivePublicKey = localActivePublicKey;
    }

    public String getLocalActivePrivateKey() {
        return LocalActivePrivateKey;
    }

    public void setLocalActivePrivateKey(String localActivePrivateKey) {
        LocalActivePrivateKey = localActivePrivateKey;
    }

    @Override
    public String toString() {
        return "AppLocalWalletUser{" +
                "LocalName='" + LocalName + '\'' +
                ", LocalPwd='" + LocalPwd + '\'' +
                ", LocalBrainKey='" + LocalBrainKey + '\'' +
                ", LocalOwnerPublicKey='" + LocalOwnerPublicKey + '\'' +
                ", LocalOwnerPrivateKey='" + LocalOwnerPrivateKey + '\'' +
                ", LocalActivePublicKey='" + LocalActivePublicKey + '\'' +
                ", LocalActivePrivateKey='" + LocalActivePrivateKey + '\'' +
                '}';
    }
}
