package com.nyzc.gdm.currencyratio.homePacakage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.nyzc.gdm.currencyratio.Base.BaseFragment;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.GetHouseBean;
import com.nyzc.gdm.currencyratio.Bean.RoomBean;
import com.nyzc.gdm.currencyratio.Bean.Rooms;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.uipacakage.Adapter.ForestListAdapter;
import com.nyzc.gdm.currencyratio.Utils.HttpUrl;
import com.nyzc.gdm.currencyratio.View.MyListView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cy.agorise.graphenej.Bean.RoomIDBean;
import cy.agorise.graphenej.api.GetHouseList;
import cy.agorise.graphenej.api.GetHousePlatform;
import cy.agorise.graphenej.interfaces.HouseListJson;
import cy.agorise.graphenej.interfaces.ResultJson;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    ForestListAdapter forestListAdapter;
    private static List<Bitmap> listImg = new ArrayList<>();
    @BindView(R.id.recommend_forecast_list)
    MyListView recommend_forecast_list;
    @BindView(R.id.home_banner_view)
    MZBannerView mzBannerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_lastest_forecast)
    TextView tv_lastest_forecast;
    @BindView(R.id.tv_hot_forecast)
    TextView tv_hot_forecast;
    @BindView(R.id.tv_topic)
    TextView tv_topic;
    @BindView(R.id.tv_hot)
    TextView tv_hot;
    @BindView(R.id.tv_sport)
    TextView tv_sport;
    RoomObject roomObject;
    static List<RoomObject> roomObjectList = new ArrayList<>();
    static List<RoomObject> roomObjectListBanner = new ArrayList<>();
    static List<RoomObject> roomObjectList1 = new ArrayList<>();
    static List<RoomObject> roomObjectList2 = new ArrayList<>();
    static List<RoomObject> roomObjectList3 = new ArrayList<>();
    List<String> listRoomId = new ArrayList<>();


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private List<Bitmap> initImageList() {
        Bitmap bitmap = null;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.onelunbo);
                    break;
                case 1:
                    bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.two_lunbo);
                    break;

                case 2:
                    bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.three_lunbo);
                    break;
            }
            listImg.add(bitmap);
        }

        return listImg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment_layout;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

//        showLoadingDialog();
        tv_lastest_forecast.setOnClickListener(this);
        tv_hot_forecast.setOnClickListener(this);
        tv_hot.setOnClickListener(this);
        tv_sport.setOnClickListener(this);
        tv_topic.setOnClickListener(this);
        refreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recommend_forecast_list.setFocusable(false);
        forestListAdapter = new ForestListAdapter(mActivity);
        recommend_forecast_list.setAdapter(forestListAdapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
        recommend_forecast_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (roomObjectList.get(i).getRoom_type() == 1) {
                    ForeCastActivity.start(mActivity, roomObjectList.get(i));
                } else {
                    Toast.makeText(mActivity, "当前房间暂不开放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mzBannerView.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                if (roomObjectListBanner.get(i).getRoom_type() == 1) {
                    ForeCastActivity.start(mActivity, roomObjectListBanner.get(i));
                } else {
                    Toast.makeText(mActivity, "当前房间暂不开放", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    List<GetHouseBean> getHouseBean;

    String Time;

    @Override
    protected void initData() {
        final List<String> list1 = new ArrayList<>();
        final List<String> list2 = new ArrayList<>();
        final List<String> list3 = new ArrayList<>();
        list1.add("1.14.3");
        list2.add("1.14.4");
        list3.add("1.14.5");
        getHomeList(list1);
        getHomeList(list2);
        getHomeList(list3);
        if (roomObjectList1.get(0) != null) {
            roomObjectList.add(roomObjectList1.get(0));
        }
        if (roomObjectList2.get(0) != null) {
            roomObjectList.add(roomObjectList2.get(0));
        }
        if (roomObjectList3.get(0) != null) {
            roomObjectList.add(roomObjectList3.get(0));
        }
        if (roomObjectList.size() != 0) {
            forestListAdapter.setData(roomObjectList);
            forestListAdapter.notifyDataSetChanged();
        }
        if (roomObjectList1.get(1) != null) {
            roomObjectListBanner.add(roomObjectList1.get(1));
        }
        if (roomObjectList2.get(1) != null) {
            roomObjectListBanner.add(roomObjectList2.get(1));
        }
        if (roomObjectList3.get(1) != null) {
            roomObjectListBanner.add(roomObjectList3.get(1));
        }
        setBanner();
    }

    private void getHomeList(List<String> houseList) {

        if (BitsharesWalletWraper.getInstance().build_connect() == 0) {
            getList(houseList);

        }

    }

    private void getList(List<String> houseList) {
        switch (houseList.get(0)) {
            case "1.14.3":
                getHousess(houseList);
                break;
            case "1.14.4":
                getHousess(houseList);
                break;
            case "1.14.5":
                getHousess(houseList);
                break;

        }

    }

    private void getHousess(List<String> listRoomId) {
        try {
            getHouseBean = BitsharesWalletWraper.getInstance().getHouse(listRoomId);
        } catch (NetworkStatusException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < getHouseBean.size(); i++) {
            for (int j = 0; j < getHouseBean.get(i).getRooms().size(); j++) {
                String roomId = getHouseBean.get(i).getRooms().get(j);
                try {
                    roomObject = BitsharesWalletWraper.getInstance().getSeerRoom(roomId);
                } catch (NetworkStatusException e) {
                    e.printStackTrace();
                }
                if (roomObject.getRoom_type() == 1) {
                    if (listRoomId.get(0).equals("1.14.3")) {
                        if (roomObject != null) {
                            roomObjectList1.add(roomObject);
                        }
                    } else if (listRoomId.get(0).equals("1.14.4")) {
                        if (roomObject != null) {
                            roomObjectList2.add(roomObject);
                        }
                    } else {
                        if (roomObject != null) {
                            roomObjectList3.add(roomObject);
                        }
                    }
                }
            }
        }

    }

    private void setBanner() {
        listImg = initImageList();
        mzBannerView.setPages(listImg, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mzBannerView.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sport:
                ForecastNewsActivity.start(mActivity, "5");
                break;
            case R.id.tv_hot:
                ForecastNewsActivity.start(mActivity, "4");
                break;
            case R.id.tv_topic:
                ForecastNewsActivity.start(mActivity, "3");
                break;
            case R.id.tv_hot_forecast:
                ForecastNewsActivity.start(mActivity, "2");
                break;
            case R.id.tv_lastest_forecast:
                ForecastNewsActivity.start(mActivity, "1");
                break;

        }
    }

    public static class BannerViewHolder implements MZViewHolder<Bitmap> {
        private ImageView mImageView;
        private RelativeLayout rel;
        private TextView tv_des;
        private TextView tv_stop_time;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = view.findViewById(R.id.iv_banner);
            rel = view.findViewById(R.id.rel);
            tv_des = view.findViewById(R.id.tv_des);
            tv_stop_time = view.findViewById(R.id.tv_stop_time);
            return view;
        }

        @Override
        public void onBind(Context context, int i, Bitmap url) {
            Glide.with(context).load(url).into(mImageView);
            tv_des.setText(roomObjectListBanner.get(i).getDescription());
            tv_stop_time.setText("截止时间 : "+DateUtil.addDateMinut(roomObjectListBanner.get(i).getOption().getStop().replace("T", " "), 8));

        }

    }

}
