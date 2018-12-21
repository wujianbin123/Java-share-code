package com.nyzc.gdm.currencyratio.Bean;

import java.io.Serializable;

public class ResultBean implements Serializable {

    /**
     * lifetimeReferrerFeePercentage : 0
     * membershipExpirationDate : 0
     * networkFeePercentage : 0
     * referrerRewardsPercentage : 0
     * id : 1.2.13008
     */

    private int lifetimeReferrerFeePercentage;
    private int membershipExpirationDate;
    private int networkFeePercentage;
    private int referrerRewardsPercentage;
    private String id;

    public int getLifetimeReferrerFeePercentage() {
        return lifetimeReferrerFeePercentage;
    }

    public void setLifetimeReferrerFeePercentage(int lifetimeReferrerFeePercentage) {
        this.lifetimeReferrerFeePercentage = lifetimeReferrerFeePercentage;
    }

    public int getMembershipExpirationDate() {
        return membershipExpirationDate;
    }

    public void setMembershipExpirationDate(int membershipExpirationDate) {
        this.membershipExpirationDate = membershipExpirationDate;
    }

    public int getNetworkFeePercentage() {
        return networkFeePercentage;
    }

    public void setNetworkFeePercentage(int networkFeePercentage) {
        this.networkFeePercentage = networkFeePercentage;
    }

    public int getReferrerRewardsPercentage() {
        return referrerRewardsPercentage;
    }

    public void setReferrerRewardsPercentage(int referrerRewardsPercentage) {
        this.referrerRewardsPercentage = referrerRewardsPercentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "lifetimeReferrerFeePercentage=" + lifetimeReferrerFeePercentage +
                ", membershipExpirationDate=" + membershipExpirationDate +
                ", networkFeePercentage=" + networkFeePercentage +
                ", referrerRewardsPercentage=" + referrerRewardsPercentage +
                ", id='" + id + '\'' +
                '}';
    }
}
