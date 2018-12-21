package com.nyzc.gdm.currencyratio.accountPacakage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.R;

public class AddressManagerAdapter extends BaseQuickAdapter<WaitAccountBean, BaseViewHolder> {

    public AddressManagerAdapter() {
        super(R.layout.item_address_manager_layout, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, WaitAccountBean item) {
        helper.setText(R.id.user_address, "用户地址");
    }
}
