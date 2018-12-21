package com.nyzc.gdm.currencyratio.OrderPacakage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.nyzc.gdm.currencyratio.Base.BaseFragment;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.BlockRoomHeader;
import com.nyzc.gdm.currencyratio.Bean.GetHouseBean;
import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.OrderPacakage.Adapter.WaitAccountAdapter;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.CacheUtils;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.View.MyListView;
import com.nyzc.gdm.currencyratio.homePacakage.ForeCastActivity;

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

public class AlreadyEndFragment extends BaseFragment {

    account_object accountObject;
    BlockRoomHeader blockHeader;
    List<UserOrderBean> userOrderBeanList = new ArrayList<>();
    static List<String> listFinishRoom = new ArrayList<>();
    RoomObject roomObject, roomObjectSelect;
    WaitAccountAdapter waitAccountAdapter;
    @BindView(R.id.recycle)
    ListView recycle;
    long raward;
    Bundle bundle;
    List<String> listRarward = new ArrayList<>();
    @BindView(R.id.iv_order_no_data)
    RelativeLayout icon_order_recorder;

    public static AlreadyEndFragment newInstance(ArrayList<String> listFinishRooms) {
        Bundle args = new Bundle();
        args.putStringArrayList("Already", listFinishRooms);
        AlreadyEndFragment fragment = new AlreadyEndFragment();
        fragment.setArguments(args);
        return fragment;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (userOrderBeanList.size() != 0) {
                icon_order_recorder.setVisibility(View.INVISIBLE);
                recycle.setVisibility(View.VISIBLE);
                waitAccountAdapter = new WaitAccountAdapter(userOrderBeanList, mActivity);
                recycle.setAdapter(waitAccountAdapter);
            } else {
                icon_order_recorder.setVisibility(View.VISIBLE);
                recycle.setVisibility(View.INVISIBLE);
            }

//            dismissLoadingDialog();

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_working_layout;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        try {
            recycle.setFocusable(false);

            bundle = getArguments();
            listFinishRoom = bundle.getStringArrayList("Already");
            recycle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ForeCastActivity.resultStart(mActivity, userOrderBeanList.get(i).getId());
                }
            });
        } catch (Exception e) {
            Log.e("Expeds", e.getMessage());
            e.printStackTrace();
        }
    }

    //
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            Toast.makeText(mActivity, "111", Toast.LENGTH_SHORT).show();
//            showLoadingDialog();
            if (userOrderBeanList.size() != 0) {
                userOrderBeanList.clear();
                waitAccountAdapter.notifyDataSetChanged();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String getUserList = CacheUtils.getString(mActivity, "userAlreadyLists", "");
                    if (!getUserList.equals("")) {
                        userOrderBeanList = new Gson().fromJson(getUserList, new TypeToken<ArrayList<UserOrderBean>>() {
                        }.getType());
                        Message msg = new Message();
                        handler.sendMessage(msg);
                    }

                }
            }).start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void initData() {
//        showLoadingDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getUserList = CacheUtils.getString(mActivity, "userAlreadyLists", "");
                if (!getUserList.equals("")) {
                    userOrderBeanList = new Gson().fromJson(getUserList, new TypeToken<ArrayList<UserOrderBean>>() {
                    }.getType());
                    Message msg = new Message();
                    handler.sendMessage(msg);
                }

            }
        }).start();

    }
}
