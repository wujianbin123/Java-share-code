package com.nyzc.gdm.currencyratio.BiBiPacakage;

import java.io.Serializable;
import java.util.Date;

public class limit_order_object implements Serializable{
    public object_id<limit_order_object> id;
    public Date expiration;
    public object_id<account_object> seller;
    public long for_sale; ///< asset id is sell_price.base.asset_id
//    public price sell_price;
    public long deferred_fee;

//    public asset amount_for_sale() {
//        return new asset( for_sale, sell_price.base.asset_id );
//    }
//
//    public asset amount_to_receive() {
//        return amount_for_sale().multipy(sell_price);
//    }
}
