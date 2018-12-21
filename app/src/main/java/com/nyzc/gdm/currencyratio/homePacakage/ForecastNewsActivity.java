package com.nyzc.gdm.currencyratio.homePacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.GetHouseBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.accountPacakage.Adapter.AccountListAdapter;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.homePacakage.Adapter.ForeCastNewsAdapter;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;
import com.nyzc.gdm.currencyratio.uipacakage.Adapter.ForestListAdapter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ForecastNewsActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    ListView mRecyclerView;
    @BindView(R.id.custom_title)
    CustomTitleBar customTitleBar;
    ForestListAdapter forestListAdapter;
    String roomid;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (roomObjectList.size() != 0) {
                forestListAdapter.setData(roomObjectList);
                forestListAdapter.notifyDataSetChanged();
            }
            dismissLoadingDialog();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_forecast_news;
    }

    public static void start(Context context, String roomId) {
        Intent starter = new Intent(context, ForecastNewsActivity.class);
        starter.putExtra("roomIndex", roomId);
        context.startActivity(starter);
    }

    List<GetHouseBean> getHouseBean;
    RoomObject roomObject;
    List<RoomObject> roomObjectList = new ArrayList<>();
    List<TimeSort> timeList = new ArrayList<>();
    List<String> list = new ArrayList<>();
    String Time;
    public List<String> listRoom = new ArrayList<>();
    double totalShares;

    @Override
    protected void init() {
        showLoadingDialog();
        roomid = getIntent().getStringExtra("roomIndex");
        customTitleBar.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        forestListAdapter = new ForestListAdapter(ForecastNewsActivity.this);
        mRecyclerView.setAdapter(forestListAdapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (roomObjectList.get(i).getRoom_type() == 1) {
                    ForeCastActivity.start(ForecastNewsActivity.this, roomObjectList.get(i));
                } else {
                    Toast.makeText(ForecastNewsActivity.this, "当前房间暂不开放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        switch (roomid) {
            case "1":
                customTitleBar.setTitle_text("最新预测");
                list.add("1.14.5");
                list.add("1.14.4");
                break;
            case "2":
                customTitleBar.setTitle_text("热门预测");
                break;
            case "3":
                customTitleBar.setTitle_text("币圈专题");
                list.add("1.14.5");
                break;
            case "4":
                customTitleBar.setTitle_text("热门事件");
                list.add("1.14.4");
                break;
            case "5":
                customTitleBar.setTitle_text("体育赛事");
                list.add("1.14.3");
                break;

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (BitsharesWalletWraper.getInstance().build_connect() == 0) {
                        if (roomid.equals("1") || roomid.equals("2")) {
                            getHouseBean = MyApp.getHouseBean;
                            listRoom = MyApp.listRoom;
                        } else {
                            getHouseBean = BitsharesWalletWraper.getInstance().getHouse(list);
                            listRoom = getHouseBean.get(0).getRooms();
                        }
                        for (int j = 0; j < listRoom.size(); j++) {
                            String roomId = listRoom.get(j);
                            roomObject = BitsharesWalletWraper.getInstance().getSeerRoom(listRoom.get(j));
                            if (roomid.equals("1")) {
                                Time = DateUtil.addDateMinut(roomObject.getOption().getStart().replace("T", " "), 8);
                                TimeSort timeSort = new TimeSort();
                                timeSort.setTime(Time);
                                timeSort.setRoomId(roomId);
                                timeList.add(timeSort);
                            } else if (roomid.equals("2")) {
                                totalShares = roomObject.getRunning_option().getTotal_shares();
                                BigDecimal total = new BigDecimal(String.valueOf(totalShares)).divide(new BigDecimal(String.valueOf(100000)), 2, BigDecimal.ROUND_DOWN).stripTrailingZeros();
                                TimeSort timeSort = new TimeSort();
                                timeSort.setTotalShares(total.doubleValue());
                                timeSort.setRoomId(roomId);
                                timeList.add(timeSort);
                            } else {
                                Time = DateUtil.addDateMinut(roomObject.getOption().getStop().replace("T", " "), 8);
                                TimeSort timeSort = new TimeSort();
                                timeSort.setTime(Time);
                                timeSort.setRoomId(roomId);
                                timeList.add(timeSort);
                            }
                        }
                        if (roomid.equals("1")) {
                            ListTimeSort(timeList);
                        } else if (roomid.equals("2")) {
                            ListTotalSort(timeList);
                        } else {
                            ListTimeSort(timeList);
                        }

                        for (int g = 0; g < timeList.size(); g++) {
                            roomObject = BitsharesWalletWraper.getInstance().getSeerRoom(timeList.get(g).getRoomId());
                            if(roomObject.getRoom_type()==1){
                                roomObjectList.add(roomObject);
                            }

                        }
                        handler.sendMessage(new Message());
                    }
                } catch (NetworkStatusException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static class TimeSort {
        String time;
        String roomId;
        Double totalShares;

        public Double getTotalShares() {
            return totalShares;
        }

        public void setTotalShares(Double totalShares) {
            this.totalShares = totalShares;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        @Override
        public String toString() {
            return "TimeSort{" +
                    "time='" + time + '\'' +
                    ", roomId='" + roomId + '\'' +
                    ", totalShares=" + totalShares +
                    '}';
        }
    }

    private static void ListTimeSort(List<TimeSort> list) {
        Collections.sort(list, new Comparator<TimeSort>() {
            @Override
            public int compare(TimeSort s, TimeSort t1) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(s.getTime());
                    Date dt2 = format.parse(t1.getTime());
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

    private static void ListTotalSort(List<TimeSort> list) {
        Collections.sort(list, new Comparator<TimeSort>() {
            @Override
            public int compare(TimeSort s, TimeSort t1) {
                try {
                    if (s.getTotalShares() < t1.getTotalShares()) {
                        return 1;
                    } else if (s.getTotalShares() > t1.getTotalShares()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

        });
    }
}
