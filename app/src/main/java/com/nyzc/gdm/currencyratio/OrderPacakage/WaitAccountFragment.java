package com.nyzc.gdm.currencyratio.OrderPacakage;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nyzc.gdm.currencyratio.Base.BaseFragment;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.OrderPacakage.Adapter.WaitAccountAdapter;
import com.nyzc.gdm.currencyratio.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WaitAccountFragment extends BaseFragment {

    @BindView(R.id.iv_order_no_data)
    RelativeLayout icon_order_recorder;

    public static WaitAccountFragment newInstance() {
        Bundle args = new Bundle();
        WaitAccountFragment fragment = new WaitAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_working_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        icon_order_recorder.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }


}
