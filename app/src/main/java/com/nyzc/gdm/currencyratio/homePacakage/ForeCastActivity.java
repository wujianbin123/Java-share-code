package com.nyzc.gdm.currencyratio.homePacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.ErrorMessage;
import com.nyzc.gdm.currencyratio.Bean.RoomBean;
import com.nyzc.gdm.currencyratio.Bean.Rooms;
import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.chain.signed_transaction;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.accountPacakage.TransferAccountActivity;
import com.nyzc.gdm.currencyratio.homePacakage.Adapter.ForecastProgressAdapter;
import com.nyzc.gdm.currencyratio.homePacakage.Adapter.ForecastbottomAdapter;
import com.nyzc.gdm.currencyratio.homePacakage.Adapter.TradeHistoryAdapter;
import com.nyzc.gdm.currencyratio.homePacakage.interFace.ForcaseListen;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.uipacakage.CheckBrainkeyActivity;
import com.nyzc.gdm.currencyratio.uipacakage.ForcastResultActivity;
import com.nyzc.gdm.currencyratio.uipacakage.SaveBrainKeyActivity;
import com.nyzc.gdm.currencyratio.Utils.DoubleUtil;
import com.nyzc.gdm.currencyratio.Utils.KeyBoardUtils;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;
import com.nyzc.gdm.currencyratio.View.MyListView;
import com.nyzc.gdm.currencyratio.View.RemindInputBrainkeyDialog;
import com.nyzc.gdm.currencyratio.View.RemindInputForcastDialog;
import com.nyzc.gdm.currencyratio.View.SpringProgressView;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


public class ForeCastActivity extends BaseActivity implements ForcaseListen {

    @BindView(R.id.tv_stop_time)
    TextView tvStopTime;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_pfc)
    TextView tvPfc;
    @BindView(R.id.tv_tongji)
    TextView tvTongji;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.forecast_list_lv)
    MyListView forecastListLv;
    @BindView(R.id.lv_forecast_bottom)
    MyListView lvForecastBottom;
    @BindView(R.id.custom_title)
    CustomTitleBar customTitleBar;
    @BindView(R.id.lv_trade_list)
    MyListView lv_trade_list;
    ForecastProgressAdapter mAdapter;
    ForecastbottomAdapter mForeCastAdapter;
    TradeHistoryAdapter tradeHistoryAdapter;
    RoomObject roomBean;
    @BindView(R.id.tv_trade_recoder)
    TextView tv_trade_recoder;
    @BindView(R.id.tv_forecast_content)
    TextView tv_forecast_content;

    String roomId, endRoomId;
    String miuNum;
    String maxNum;

    //
    public static void start(Context context, RoomObject roomBeans) {
        Intent starter = new Intent(context, ForeCastActivity.class);
        starter.putExtra("RoomBean", roomBeans);
        context.startActivity(starter);
    }

    public static void resultStart(Context context, String endRoomId) {
        Intent starter = new Intent(context, ForeCastActivity.class);
        starter.putExtra("endRoomId", endRoomId);
        context.startActivity(starter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_fore_cast;
    }

    RemindInputForcastDialog remindInputForcastDialog;
    SimpleDateFormat sdf;

    @Override
    protected void init() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customTitleBar.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        endRoomId = getIntent().getStringExtra("endRoomId");
        if (endRoomId == null) {
//            roomId = getIntent().getStringExtra("RoomId");
            roomBean = (RoomObject) getIntent().getSerializableExtra("RoomBean");
            roomId = roomBean.getId().get_userId();
        } else {
            try {
                roomBean = BitsharesWalletWraper.getInstance().getSeerRoom(endRoomId);
                roomId = roomBean.getId().get_userId();
            } catch (NetworkStatusException e) {
                e.printStackTrace();
            }
        }

        miuNum = getBigDecimal(String.valueOf(roomBean.getOption().getMinimum()));
        maxNum = getBigDecimal(String.valueOf(roomBean.getOption().getMaximum()));
        mAdapter = new ForecastProgressAdapter(ForeCastActivity.this);
        forecastListLv.setAdapter(mAdapter);
        mForeCastAdapter = new ForecastbottomAdapter(ForeCastActivity.this);
        lvForecastBottom.setAdapter(mForeCastAdapter);
//        tradeHistoryAdapter = new TradeHistoryAdapter(ForeCastActivity.this);
//        lv_trade_list.setAdapter(tradeHistoryAdapter);
        setData();

    }


    private String getBigDecimal(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    String stopTime;
    boolean isForcast = true;
    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    private void setData() {
        if (roomBean.getOption().getStop() != null) {
            stopTime = DateUtil.addDateMinut(roomBean.getOption().getStop().replace("T", " "), 8);
            tvStopTime.setText("截至时间: " + stopTime);
            try {
                Date d = new Date();
                Date dt1 = sdf.parse(stopTime);
                if (d.getTime() > dt1.getTime()) {
                    isForcast = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
//        if (!isForcast) {
//            tvStatus.setText("已截止");
//        }
        if(!isForcast){
            tvStatus.setText("已截止");
        }else{
            if (roomBean.getStatus().equals("opening")) {
                tvStatus.setText("预测中");
            } else {
                tvStatus.setText("已结束");
            }
        }

        if (roomBean.getDescription() != null) {
            tv_forecast_content.setText(roomBean.getDescription());
        }
        if (roomBean.getRunning_option().getTotal_shares() != -1) {
            tvPfc.setText(getBigDecimal(String.valueOf(roomBean.getRunning_option().getTotal_shares())));
        }
        if (roomBean.getRunning_option().getTotal_player_count() != -1) {
            tvNum.setText(decimalFormat.format(roomBean.getRunning_option().getTotal_player_count())+"");
        }
        if (roomBean.getOption().getResult_owner_percent() != -1) {
            double resultOwnerPercent = roomBean.getOption().getResult_owner_percent();
            double ownerPercent = resultOwnerPercent / 100;
            tvTongji.setText( decimalFormat.format(ownerPercent) + "%");
        }

//        int trade_count = 0;
//        for (int i = 0; i < roomBean.getRunning_option().getParticipators().size(); i++) {
//            trade_count += roomBean.getRunning_option().getParticipators().get(i).size();
//        }
//        tv_trade_recoder.setText("(" + trade_count + ")");
        if (roomBean != null) {
            mAdapter.setData(roomBean);
            mForeCastAdapter.setData(roomBean, isForcast);
//            tradeHistoryAdapter.setData(roomBean);
        }
    }


    String amountSeer;

    @Override
    public void forcastListen(final int position) {

        remindInputForcastDialog = new RemindInputForcastDialog(roomBean.getRunning_option().getSelection_description().get(position) + "", miuNum, maxNum, new RemindInputForcastDialog.SendBackListener() {
            @Override
            public void sendBack(final String inputText) {
                if (inputText.toString() != null) {
                    showLoadingDialog();
                    int nRet = BitsharesWalletWraper.getInstance().build_connect();
                    if (nRet == 0) {
                        try {
                            if (MyApp.localWalletUser != null) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        signed_transaction signedTransaction = null;
                                        try {
                                            signedTransaction = BitsharesWalletWraper.getInstance().forecastTransfer(MyApp.localWalletUser.getLocalName(), 0, roomId, inputText.toString(), position);
                                        } catch (NetworkStatusException e) {
                                            e.printStackTrace();
                                        }
                                        if (signedTransaction != null) {
                                            ForcastResultActivity.start(ForeCastActivity.this, roomBean, roomBean.getRunning_option().getSelection_description().get(position), inputText.toString(), roomBean.getOption().getStop());
                                            remindInputForcastDialog.dismiss();
                                            dismissLoadingDialog();
                                        }
                                    }
                                }, 2000);
                            }
                        } catch (Exception e) {
                            Toast.makeText(ForeCastActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("NetworkStatusException", e.getMessage().toString());
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(ForeCastActivity.this, "webSocket地址链接没有成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        remindInputForcastDialog.show(getSupportFragmentManager(), "dialog");
    }
}
