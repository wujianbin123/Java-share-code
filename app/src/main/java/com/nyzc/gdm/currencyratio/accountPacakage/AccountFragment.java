package com.nyzc.gdm.currencyratio.accountPacakage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.accountPacakage.Adapter.AccountListAdapter;
import com.nyzc.gdm.currencyratio.Base.BaseFragment;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.homePacakage.HomeFragment;
import com.nyzc.gdm.currencyratio.OrderPacakage.Adapter.WaitAccountAdapter;
import com.nyzc.gdm.currencyratio.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AccountFragment extends BaseFragment {

    TextView tv_seer_account_asset;
    ConstraintLayout asset_list_item_background;

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    account_object AccountObject = null;

    //        @Override
//    protected int getLayoutId() {
//        return R.layout.account_fragment_layout;
//    }
    @Override
    protected int getLayoutId() {
        return R.layout.item_asset_list_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        asset_list_item_background = view.findViewById(R.id.asset_list_item_background);
        tv_seer_account_asset = view.findViewById(R.id.tv_seer_account_asset);

        ;
        asset_list_item_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, TranstionRecorderActivity.class);
                startActivityForResult(intent, 1);
//                TranstionRecorderActivity.start(mActivity);
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            if (MyApp.localWalletUser != null) {
                getData();
            }

        }
    }

    private void getData() {

        try {
            AccountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
            List<asset> list = BitsharesWalletWraper.getInstance().list_account_balance(AccountObject.id, true);
            String amount = String.valueOf(list.get(0).amount);
            BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
            tv_seer_account_asset.setText(bigDecimal.toPlainString());
            MyApp.localWalletUser.setAmoutMoney(bigDecimal.toPlainString());
        } catch (NetworkStatusException e) {
            e.printStackTrace();
        }

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            getData();
        }
    }

    @Override
    protected void initData() {
        getData();
    }
}
