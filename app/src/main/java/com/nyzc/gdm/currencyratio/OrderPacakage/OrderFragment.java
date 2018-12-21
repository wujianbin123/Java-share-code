package com.nyzc.gdm.currencyratio.OrderPacakage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.BaseFragment;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.BlockRoomHeader;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.Bean.GetHouseBean;
import com.nyzc.gdm.currencyratio.Bean.RoomInfo;
import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.chain.block_header;
import com.nyzc.gdm.currencyratio.BiBiPacakage.chain.signed_transaction;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.BiBiPacakage.operations;
import com.nyzc.gdm.currencyratio.Utils.CacheUtils;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.homePacakage.HomeFragment;
import com.nyzc.gdm.currencyratio.OrderPacakage.Adapter.ViewPagerFragmentAdapter;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.uipacakage.ForcastResultActivity;
import com.subgraph.orchid.crypto.TorNTorKeyAgreement;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.main_activity_frameLayout)
    FrameLayout main_activity_frameLayout;
    private List<String> mTitleDataList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    @BindView(R.id.order_working)
    LinearLayout order_working;
    @BindView(R.id.order_wait)
    LinearLayout order_wait;
    @BindView(R.id.order_finised)
    LinearLayout order_finised;
    @BindView(R.id.view_order_working)
    View view_order_working;
    @BindView(R.id.view_order_wait)
    View view_order_wait;
    @BindView(R.id.view_order_finised)
    View view_order_finised;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.tv_win)
    TextView tv_win;
    @BindView(R.id.tv_winRate)
    TextView tv_winRate;
    @BindView(R.id.tv_total_order_income)
    TextView tv_total_order_income;
    public static List<Double> listRarward = new ArrayList<>();
    long raward;
    account_object accountObject;
    BlockRoomHeader blockHeader;
    List<UserOrderBean> userOrderBeanList = new ArrayList<>();

    RoomObject roomObject, roomObjectSelect;
    FragmentManager fm;
    private List<Fragment> fragments = new ArrayList<>();
    Fragment workingFragment, alreadyEndFragment, waitAccountFragment;
    private Fragment mContent;
    private String[] tags = new String[]{"WorkingFragment", "WaitAccountFragment", "AlreadyEndFragment"};

    public static OrderFragment newInstance() {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Double totalNum = 0.0;
    DecimalFormat df = new DecimalFormat("#.00");
    int orderNum;
    //df.format(d);
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < listRarward.size(); i++) {
                totalNum += listRarward.get(i).doubleValue() / 100000;
            }
            if (totalNum > 0) {
                tv_total_order_income.setText(df.format(totalNum) + " SEER");
            } else {
                tv_total_order_income.setText("0" + " SEER");
            }

            orderNum = workNum + finishRoom;
            tv_order.setText(String.valueOf(orderNum));
            tv_win.setText(String.valueOf(winNum));
            if (orderNum != 0) {
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(winNum)).divide(new BigDecimal(String.valueOf(orderNum)), 2, BigDecimal.ROUND_DOWN).stripTrailingZeros();
                BigDecimal winRate = new BigDecimal(String.valueOf(bigDecimal.toPlainString())).multiply(new BigDecimal("100")).stripTrailingZeros();
                tv_winRate.setText(winRate.toPlainString() + "%");
            } else {
                tv_winRate.setText("0" + "%");
            }
            listRarward.clear();
            workNum = -1;
            totalNum = 0.0;
            finishRoom = -1;
            winNum = -1;
            dismissLoadingDialog();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.order_fragment_layout;
    }

    String CurryName;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        showLoadingDialog();
        setFragment();
        EventBus.getDefault().register(this);
        order_working.setOnClickListener(this);
        order_wait.setOnClickListener(this);
        order_finised.setOnClickListener(this);
        MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(context, "localwallet");
        if (MyApp.localWalletUser != null) {
            CurryName = MyApp.localWalletUser.getLocalName();
            getAcountObject();
        }
    }

    private void getAcountObject() {
        try {
            accountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
        } catch (NetworkStatusException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        {
            if (!hidden) {
                if (!CurryName.equals(MyApp.localWalletUser.getLocalName())) {
                    CurryName = MyApp.localWalletUser.getLocalName();
                    getAcountObject();
                    EventBus.getDefault().post("datachange");
                    userOrderBeanList.clear();
                    CacheUtils.putString(context, "userAlreadyLists", "");
                    showLoadingDialog();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getFinish();
                        }
                    }).start();
                } else {
                    String getUserList = CacheUtils.getString(mActivity, MyApp.localWalletUser.getLocalName(), "");
                    if (!getUserList.equals("")) {
                        EventBus.getDefault().post("datachange");
                        if (MyApp.isChangeOrder == 1) {
                            String orderNum = tv_order.getText().toString();
                            int num = Integer.parseInt(orderNum);
                            num++;
                            tv_order.setText(String.valueOf(num));
                            MyApp.isChangeOrder = 0;
                        }

                    }
                }

            }
        }
    }


    private void setFragment() {

        fm = getChildFragmentManager();
        workingFragment = WorkingFragment.newInstance((ArrayList<String>) MyApp.listRoom);
        waitAccountFragment = WaitAccountFragment.newInstance();
        alreadyEndFragment = AlreadyEndFragment.newInstance((ArrayList<String>) MyApp.listFinishRoom);
        fragmentList.add(workingFragment);
        fragmentList.add(waitAccountFragment);
        fragmentList.add(alreadyEndFragment);
        fragments.add(0, workingFragment);
        fragments.add(1, waitAccountFragment);
        fragments.add(2, alreadyEndFragment);
        mContent = workingFragment;
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_activity_frameLayout, mContent);
        ft.commitAllowingStateLoss();
    }

    List<String> listFinishRoom = new ArrayList<>();
    List<String> listRoom = new ArrayList<>();

    @Override
    protected void initData() {
        listFinishRoom = MyApp.listFinishRoom;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getFinish();
            }
        }).start();

    }


    List<RoomObject> roomObjectList = new ArrayList<>();
//    List<RoomObject> roomObjectWorkingList = new ArrayList<>();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void getFinish() {
        for (int i = 0; i < listFinishRoom.size(); i++) {
            try {
                roomObject = BitsharesWalletWraper.getInstance().getSeerRoom(listFinishRoom.get(i));
            } catch (NetworkStatusException e) {
                e.printStackTrace();
            }
            for (int g = 0; g < roomObject.getRunning_option().getParticipators().size(); g++) {
                if (roomObject.getRunning_option().getParticipators().get(g).size() != 0) {
                    for (int j = 0; j < roomObject.getRunning_option().getParticipators().get(g).size(); j++) {
                        if (roomObject.getRunning_option().getParticipators().get(g).get(j).getPlayer().equals(accountObject.getId().get_userId())) {
                            roomObjectList.add(roomObject);
                            try {
                                blockHeader = BitsharesWalletWraper.getInstance().get_block(roomObject.getRunning_option().getParticipators().get(g).get(j).getBlock_num() + 1);
                                if (blockHeader.getTransactions() != null) {
                                    Double oprationType = (Double) blockHeader.getTransactions().get(0).getOperations().get(0).get(0);
                                    if (oprationType == 50) {
                                        String joinTime = DateUtil.addDateMinut(blockHeader.getTransactions().get(0).getExpiration().replace("T", " "), 8);
                                        LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) blockHeader.getTransactions().get(0).getOperations().get(0).get(1);
                                        LinkedTreeMap<String, Object> fee = (LinkedTreeMap<String, Object>) map.get("fee");
                                        String roomId = (String) map.get("room");
                                        if (roomId != null) {
                                            double feeAmount = (double) fee.get("amount");
                                            Object amount = map.get("amount");
                                            List<Object> selectRoomSelect = (List<Object>) map.get("input");
                                            if (selectRoomSelect == null) {
                                                String roomIds = (String) map.get("room");
                                            }
                                            double selectRoomIndex = (double) selectRoomSelect.get(0);
                                            try {
                                                roomObjectSelect = BitsharesWalletWraper.getInstance().getSeerRoom(roomId);
                                            } catch (NetworkStatusException e) {
                                                e.printStackTrace();
                                            }
                                            for (int h = 0; h < roomObjectSelect.getRunning_option().getParticipators().get((int) selectRoomIndex).size(); h++) {
                                                if (roomObjectSelect.getRunning_option().getParticipators().get((int) selectRoomIndex).get(h).getPlayer().equals(accountObject.getId().get_userId())) {
                                                    raward = (long) roomObjectSelect.getRunning_option().getParticipators().get((int) selectRoomIndex).get(h).getReward();
                                                }
                                            }
                                            String selection_description = roomObjectSelect.getRunning_option().getSelection_description().get((int) selectRoomIndex);
                                            String roomTitle = roomObjectSelect.getDescription();
                                            UserOrderBean userOrderBean = new UserOrderBean();
                                            userOrderBean.setId(roomId);
                                            userOrderBean.setJoinRoomTime(joinTime);
                                            userOrderBean.setRoomSelect(selection_description);
                                            userOrderBean.setRoomTitle(roomTitle);
                                            userOrderBean.setRoomSelectAmount(String.valueOf(amount));
                                            userOrderBean.setSelectFinalResult(roomObject.getFinal_result().get(0));
                                            if (raward > 0) {
                                                userOrderBean.setReward(String.valueOf(raward));
                                                listRarward.add((double) raward);
                                            } else {
                                                userOrderBean.setReward("未中奖");
                                            }
                                            userOrderBeanList.add(userOrderBean);
                                        }
                                    }
                                }
                            } catch (NetworkStatusException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
        winNum = listRarward.size();
        finishRoom = userOrderBeanList.size();
        Log.e("winNum", winNum + ",,,," + finishRoom);
        ListTimeSort(userOrderBeanList);
        String userList = new Gson().toJson(userOrderBeanList);
        CacheUtils.putString(context, "userAlreadyLists", userList);
        Message msg = new Message();
        handler.sendMessage(msg);
    }

    private static void ListTimeSort(List<UserOrderBean> list) {
        Collections.sort(list, new Comparator<UserOrderBean>() {
            @Override
            public int compare(UserOrderBean s, UserOrderBean t1) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(s.getJoinRoomTime());
                    Date dt2 = format.parse(t1.getJoinRoomTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }

        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_working:
                view_order_working.setVisibility(View.VISIBLE);
                view_order_wait.setVisibility(View.INVISIBLE);
                view_order_finised.setVisibility(View.INVISIBLE);
                switchFragment(mContent, fragments.get(0), 0);
                break;
            case R.id.order_wait:
                view_order_working.setVisibility(View.INVISIBLE);
                view_order_wait.setVisibility(View.VISIBLE);
                view_order_finised.setVisibility(View.INVISIBLE);
                switchFragment(mContent, fragments.get(1), 1);
                break;
            case R.id.order_finised:
                view_order_working.setVisibility(View.INVISIBLE);
                view_order_wait.setVisibility(View.INVISIBLE);
                view_order_finised.setVisibility(View.VISIBLE);
                switchFragment(mContent, fragments.get(2), 2);
                break;

        }
    }

    @Subscribe
    public void getEventBus(Integer num) {
        if (num != null) {
            workNum = num;

        }
    }

    int workNum;
    int winNum;
    int finishRoom;


}
