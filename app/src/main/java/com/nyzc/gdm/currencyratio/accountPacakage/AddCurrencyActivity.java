package com.nyzc.gdm.currencyratio.accountPacakage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nyzc.gdm.currencyratio.accountPacakage.Adapter.Add_Currency_Adapter;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.homePacakage.Adapter.ForeCastNewsAdapter;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddCurrencyActivity extends BaseActivity {

    Add_Currency_Adapter mAdapter;
    List<WaitAccountBean> list = new ArrayList<>();
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.custom_title)
    CustomTitleBar customTitleBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_currency;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AddCurrencyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void init() {
        customTitleBar.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        for (int i = 0; i < 15; i++) {
            WaitAccountBean readUnderStand = new WaitAccountBean();
            list.add(readUnderStand);
        }
        mAdapter = new Add_Currency_Adapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mAdapter);
        setData(true, list);
    }

    private void setData(boolean isRefreshs, List data) {
        final int size = data == null ? 0 : data.size();
        if (isRefreshs) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
    }
}
