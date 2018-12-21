package com.nyzc.gdm.currencyratio.OrderPacakage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.BaseFragment;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.BlockRoomHeader;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.Bean.GetHouseBean;
import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.OrderPacakage.Adapter.WaitAccountAdapter;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.CacheUtils;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.View.MyListView;
import com.nyzc.gdm.currencyratio.homePacakage.ForeCastActivity;
import com.nyzc.gdm.currencyratio.homePacakage.ForecastNewsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class WorkingFragment extends BaseFragment {
    account_object accountObject;
    BlockRoomHeader blockHeader;
    List<UserOrderBean> userOrderBeanList = new ArrayList<>();
    static List<String> listRoom = new ArrayList<>();
    RoomObject roomObject, roomObjectSelect;
    WaitAccountAdapter waitAccountAdapter;
    @BindView(R.id.recycle)
    MyListView recycle;
    long raward;
    Bundle bundle;
    @BindView(R.id.iv_order_no_data)
    RelativeLayout icon_order_recorder;
    boolean isRefresh;

    public static WorkingFragment newInstance(ArrayList<String> listRooms) {
        Bundle args = new Bundle();
        args.putStringArrayList("working", listRooms);
        WorkingFragment fragment = new WorkingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissLoadingDialog();
            if (userOrderBeanList != null) {
                if (userOrderBeanList.size() != 0) {
                    icon_order_recorder.setVisibility(View.INVISIBLE);
                    recycle.setVisibility(View.VISIBLE);
                } else {
                    icon_order_recorder.setVisibility(View.VISIBLE);
                    recycle.setVisibility(View.INVISIBLE);
                }
                waitAccountAdapter = new WaitAccountAdapter(userOrderBeanList, mActivity);
                recycle.setAdapter(waitAccountAdapter);
            }


        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_working_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        recycle.setFocusable(false);
        showLoadingDialog();
        bundle = getArguments();
        listRoom = bundle.getStringArrayList("working");
        recycle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ForeCastActivity.resultStart(mActivity, userOrderBeanList.get(i).getId());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        isRefresh = true;
        MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(mActivity, "localwallet");
        if(MyApp.localWalletUser!=null){
            getWorkingRoom();
        }
    }

    @Subscribe
    public void getEventWork(String ss) {
        if (ss.equals("datachange")) {
//            showLoadingDialog();
            if (userOrderBeanList != null) {
                if (userOrderBeanList.size() != 0) {
                    userOrderBeanList.clear();
                }
                if (waitAccountAdapter != null) {
                    waitAccountAdapter.notifyDataSetChanged();
                }

            }
            MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(mActivity, "localwallet");
            if(MyApp.localWalletUser!=null){
                getWorkingRoom();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

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

    String getUserList;

    private void getWorkingRoom() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    accountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
                } catch (NetworkStatusException e) {
                    e.printStackTrace();
                }
                if (isRefresh) {
                    CacheUtils.putString(mActivity, MyApp.localWalletUser.getLocalName(), "");
                    isRefresh = false;
                    getUserList = CacheUtils.getString(mActivity, MyApp.localWalletUser.getLocalName(), "");
                } else {
                    getUserList = CacheUtils.getString(mActivity, MyApp.localWalletUser.getLocalName(), "");
                }
                if (getUserList.equals("")) {
                    for (int i = 0; i < listRoom.size(); i++) {
                        try {
                            roomObject = BitsharesWalletWraper.getInstance().getSeerRoom(listRoom.get(i));
                             for (int g = 0; g < roomObject.getRunning_option().getParticipators().size(); g++) {
                                if (roomObject.getRunning_option().getParticipators().get(g).size() != 0) {
                                    for (int j = 0; j < roomObject.getRunning_option().getParticipators().get(g).size(); j++) {
                                        if (roomObject.getRunning_option().getParticipators().get(g).get(j).getPlayer().equals(accountObject.getId().get_userId())) {
                                            try {
                                                blockHeader = BitsharesWalletWraper.getInstance().get_block(roomObject.getRunning_option().getParticipators().get(g).get(j).getBlock_num() + 1);
                                            } catch (NetworkStatusException e1) {
                                                e1.printStackTrace();
                                            }
                                            if (blockHeader.getTransactions() != null) {
                                                Double oprationType = (Double) blockHeader.getTransactions().get(0).getOperations().get(0).get(0);
                                                if (oprationType == 50) {
                                                    String joinTime = DateUtil.addDateMinut(blockHeader.getTransactions().get(0).getExpiration().replace("T", " "), 8);
                                                    LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) blockHeader.getTransactions().get(0).getOperations().get(0).get(1);
                                                    LinkedTreeMap<String, Object> fee = (LinkedTreeMap<String, Object>) map.get("fee");
                                                    double feeAmount = (double) fee.get("amount");
                                                    double amount = (double) map.get("amount");
                                                    String roomId = (String) map.get("room");
                                                    List<Object> selectRoomSelect = (List<Object>) map.get("input");
                                                    double selectRoomIndex = (double) selectRoomSelect.get(0);
                                                    try {
                                                        roomObjectSelect = BitsharesWalletWraper.getInstance().getSeerRoom(roomId);
                                                    } catch (NetworkStatusException e) {
                                                        e.printStackTrace();
                                                    }
                                                    String selection_description = roomObjectSelect.getRunning_option().getSelection_description().get((int) selectRoomIndex);
                                                    String roomTitle = roomObjectSelect.getDescription();
                                                    UserOrderBean userOrderBean = new UserOrderBean();
                                                    userOrderBean.setId(roomId);
                                                    userOrderBean.setJoinRoomTime(joinTime);
                                                    userOrderBean.setRoomSelect(selection_description);
                                                    userOrderBean.setRoomTitle(roomTitle);
                                                    userOrderBean.setRoomSelectAmount(String.valueOf(amount));
                                                    userOrderBeanList.add(userOrderBean);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (NetworkStatusException e1) {
                            e1.printStackTrace();
                        }

                    }
                    ListTimeSort(userOrderBeanList);
                    String userList = new Gson().toJson(userOrderBeanList);
                    CacheUtils.putString(mActivity, MyApp.localWalletUser.getLocalName(), userList);
                } else {
                    if (!getUserList.equals("[]")) {
                        userOrderBeanList = new Gson().fromJson(getUserList, new TypeToken<ArrayList<UserOrderBean>>() {
                        }.getType());
                        ListTimeSort(userOrderBeanList);
                    }
                }
                if (userOrderBeanList != null) {
                    EventBus.getDefault().post(userOrderBeanList.size());
                } else {
                    EventBus.getDefault().post(0);
                }

                Message msg = new Message();
                handler.sendMessage(msg);

            }
        }).start();
    }

}
