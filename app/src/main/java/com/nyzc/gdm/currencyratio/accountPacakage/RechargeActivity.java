package com.nyzc.gdm.currencyratio.accountPacakage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;

import butterknife.BindView;

public class RechargeActivity extends BaseActivity {
    @BindView(R.id.custom_title)
    CustomTitleBar custom_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void init() {
        custom_title.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
    }
}
