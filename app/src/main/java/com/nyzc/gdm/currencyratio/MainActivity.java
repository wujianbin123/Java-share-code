package com.nyzc.gdm.currencyratio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.MinePacakege.MineFragment;
import com.nyzc.gdm.currencyratio.OrderPacakage.OrderFragment;
import com.nyzc.gdm.currencyratio.Utils.ActivityUtil;
import com.nyzc.gdm.currencyratio.View.BottomBar;
import com.nyzc.gdm.currencyratio.View.BottomBarTab;
import com.nyzc.gdm.currencyratio.accountPacakage.AccountFragment;
import com.nyzc.gdm.currencyratio.homePacakage.HomeFragment;
import com.nyzc.gdm.currencyratio.uipacakage.StartMainActvity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    FragmentManager fm;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_dingdan)
    ImageView ivDingdan;
    @BindView(R.id.tv_dingdan)
    TextView tvDingdan;
    @BindView(R.id.ll_dingdan)
    LinearLayout llDingdan;
    @BindView(R.id.iv_seer_account)
    ImageView ivSeerAccount;
    @BindView(R.id.tv_seer_accoun)
    TextView tvSeerAccoun;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.ll_my)
    LinearLayout llMy;
    private List<Fragment> fragments = new ArrayList<>();
    Fragment homeFragment, orderFragment, accountFragment, myFragment;
    private Fragment mContent;
    private String[] tags = new String[]{"HomeFragment", "OrderFragment", "AccountFragment", "MyFragment"};
    FrameLayout main_activity_contentContainer;
    int lanchMode;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        lanchMode = intent.getIntExtra("lanchMode", -1);
        switch (lanchMode) {
            case 0:
                getSelectTab(0);
                switchFragment(mContent, fragments.get(0), 0);
                break;
            case 1:
                getSelectTab(3);
                switchFragment(mContent, fragments.get(3), 3);
                break;
            case 2:
                getSelectTab(1);
                switchFragment(mContent, fragments.get(1), 1);
                break;
            default:
                getSelectTab(0);
                switchFragment(mContent, fragments.get(0), 0);
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        llHome.setOnClickListener(this);
        llDingdan.setOnClickListener(this);
        llAccount.setOnClickListener(this);
        llMy.setOnClickListener(this);
        MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(MainActivity.this, "localwallet");
//        EventBus.getDefault().register(this);
        main_activity_contentContainer = findViewById(R.id.main_activity_frameLayout);
        initFragment();

    }

    private void selectTab(int position) {
        if (position == 0) {
            switchFragment(mContent, fragments.get(position), position);
        } else {
            MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(MainActivity.this, "localwallet");
            if (MyApp.localWalletUser == null) {
                StartMainActvity.start(MainActivity.this);
            } else {
                switchFragment(mContent, fragments.get(position), position);
            }
        }
    }

    public void switchFragment(Fragment from, Fragment to, int position) {

        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.main_activity_frameLayout, to, tags[position]).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        homeFragment = HomeFragment.newInstance();
        orderFragment = OrderFragment.newInstance();
        accountFragment = AccountFragment.newInstance();
        myFragment = MineFragment.newInstance();
        fragments.add(0, homeFragment);
        fragments.add(1, orderFragment);
        fragments.add(2, accountFragment);
        fragments.add(3, myFragment);
        mContent = homeFragment;
        getSelectTab(0);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_activity_frameLayout, mContent);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
        if (!netWorkState) {
            Toast.makeText(this, "无网", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "有网", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    // 设置返回按钮的监听事件
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                getSelectTab(0);
                selectTab(0);
                break;
            case R.id.ll_dingdan:
                MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(MainActivity.this, "localwallet");
                if (MyApp.localWalletUser != null) {
                    getSelectTab(1);
                }
                selectTab(1);
                break;
            case R.id.ll_account:
                MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(MainActivity.this, "localwallet");
                if (MyApp.localWalletUser != null) {
                    getSelectTab(2);
                }
                selectTab(2);
                break;
            case R.id.ll_my:
                MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(MainActivity.this, "localwallet");
                if (MyApp.localWalletUser != null) {
                    getSelectTab(3);
                }
                selectTab(3);
                break;
        }
    }

    private void getSelectTab(int tab) {
        switch (tab) {
            case 0:
                ivHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.comment_titlebar));
                ivDingdan.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivSeerAccount.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivMy.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                break;
            case 1:
                ivHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivDingdan.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.comment_titlebar));
                ivSeerAccount.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivMy.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                break;
            case 2:
                ivHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivDingdan.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivSeerAccount.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.comment_titlebar));
                ivMy.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                break;
            case 3:
                ivHome.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivDingdan.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivSeerAccount.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tab_unselect));
                ivMy.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.comment_titlebar));
                break;
        }

    }
}
