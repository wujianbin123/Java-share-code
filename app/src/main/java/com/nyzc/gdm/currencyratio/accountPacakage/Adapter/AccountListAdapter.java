package com.nyzc.gdm.currencyratio.accountPacakage.Adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.R;

public class AccountListAdapter extends BaseQuickAdapter<WaitAccountBean, BaseViewHolder> {

    public AccountListAdapter() {
        super(R.layout.item_asset_list_layout, null);
    }

    ConstraintLayout constraintLayout;

    @Override
    protected void convert(BaseViewHolder helper, WaitAccountBean item) {
//        constraintLayout = helper.getView(R.id.asset_list_item_background);
//        constraintLayout.setBackgroundDrawable(getSetBackground(MyApp.context.getResources().getColor(R.color.comment_titlebar)));
//        helper.setText(R.id.tv_seer_account_num, item.getTitle());

    }

    private Drawable getSetBackground(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(15);
        drawable.setStroke(1,color);
        drawable.setColor(color);
        return drawable;
    }

}
