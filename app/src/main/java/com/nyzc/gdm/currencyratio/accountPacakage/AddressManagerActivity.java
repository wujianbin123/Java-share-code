package com.nyzc.gdm.currencyratio.accountPacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nyzc.gdm.currencyratio.accountPacakage.Adapter.Add_Currency_Adapter;
import com.nyzc.gdm.currencyratio.accountPacakage.Adapter.AddressManagerAdapter;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressManagerActivity extends BaseActivity {


    @BindView(R.id.custom_title)
    CustomTitleBar customTitle;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    AddressManagerAdapter mAdapter;
    List<WaitAccountBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AddressManagerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void init() {
        customTitle.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        for (int i = 0; i < 15; i++) {
            WaitAccountBean readUnderStand = new WaitAccountBean();
            list.add(readUnderStand);
        }
        mAdapter = new AddressManagerAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recycle.setAdapter(mAdapter);
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
