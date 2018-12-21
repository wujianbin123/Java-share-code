package com.nyzc.gdm.currencyratio.accountPacakage.Adapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.R;

public class Add_Currency_Adapter extends BaseQuickAdapter<WaitAccountBean, BaseViewHolder> {

    public Add_Currency_Adapter() {
        super(R.layout.item_add_currency, null);
    }

    ConstraintLayout constraintLayout;

    @Override
    protected void convert(BaseViewHolder helper, WaitAccountBean item) {
        helper.setText(R.id.seer_infos, "文字介绍");

    }



}
