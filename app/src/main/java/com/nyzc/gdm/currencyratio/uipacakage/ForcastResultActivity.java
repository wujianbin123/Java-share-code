package com.nyzc.gdm.currencyratio.uipacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.CacheUtils;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForcastResultActivity extends BaseActivity {

    @BindView(R.id.custom_title)
    CustomTitleBar customTitle;
    @BindView(R.id.tv_forcast_fail)
    TextView tvForcastFail;
    @BindView(R.id.tv_forcast_fail_reason)
    TextView tvForcastFailReason;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.rela_forcast_fail)
    RelativeLayout relaForcastFail;
    @BindView(R.id.tv_forcast_success)
    TextView tvForcastSuccess;
    @BindView(R.id.tv_forcast_detail)
    TextView tvForcastDetail;
    @BindView(R.id.tv_forcast_select)
    TextView tvForcastSelect;
    @BindView(R.id.tv_forcast_amount)
    TextView tvForcastAmount;
    @BindView(R.id.rela_forcast_info)
    RelativeLayout relaForcastInfo;
    @BindView(R.id.tv_forcast_stop_time)
    TextView tvForcastStopTime;
    @BindView(R.id.tv_forcast_success_detail)
    RelativeLayout tvForcastSuccessDetail;
    @BindView(R.id.tv_see_recorder)
    TextView tvSeeRecorder;
    @BindView(R.id.tv_continue_forcast)
    TextView tvContinueForcast;
    @BindView(R.id.rela_forcast_success)
    RelativeLayout relaForcastSuccess;

    @Override
    public int getLayoutId() {
        return R.layout.forcast_result_layout;
    }

    RoomObject roomObject;
    String selectRoom, amountSEER, stopTime, failReason;

    public static void start(Context context, RoomObject roomObject, String selectRoom, String amountSEER, String stopTime) {
        Intent starter = new Intent(context, ForcastResultActivity.class);
        starter.putExtra("roomObject", roomObject);
        starter.putExtra("selectRoom", selectRoom);
        starter.putExtra("amountSEER", amountSEER);
        starter.putExtra("stopTime", stopTime);
        context.startActivity(starter);
    }

    public static void startFail(Context context, String failReason) {
        Intent starter = new Intent(context, ForcastResultActivity.class);
        starter.putExtra("failReason", failReason);
        context.startActivity(starter);
    }

    List<UserOrderBean> userOrderBeanList = new ArrayList<>();
    String getUserList;
    UserOrderBean userOrderBean = new UserOrderBean();
    String forcastStoptime;
    String forcastStartTime;

    @Override
    protected void init() {
        getUserList = CacheUtils.getString(ForcastResultActivity.this, MyApp.localWalletUser.getLocalName(), "");
        if (!getUserList.equals("")) {
            if (!getUserList.equals("[]")) {
                userOrderBeanList = new Gson().fromJson(getUserList, new TypeToken<ArrayList<UserOrderBean>>() {
                }.getType());
            }
        }
        roomObject = (RoomObject) getIntent().getSerializableExtra("roomObject");
        selectRoom = getIntent().getStringExtra("selectRoom");
        amountSEER = getIntent().getStringExtra("amountSEER");
        stopTime = getIntent().getStringExtra("stopTime");
        failReason = getIntent().getStringExtra("failReason");
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        forcastStartTime=df.format(day);
        if (failReason != null) {
            relaForcastFail.setVisibility(View.VISIBLE);
            relaForcastSuccess.setVisibility(View.GONE);
            tvForcastFailReason.setText(failReason);
        } else {
            relaForcastFail.setVisibility(View.GONE);
            relaForcastSuccess.setVisibility(View.VISIBLE);
            tvForcastDetail.setText(roomObject.getDescription());
            tvForcastSelect.setText(selectRoom);
            tvForcastAmount.setText(amountSEER + "SEER");
//            stopTime = DateUtil.addDateMinut(stopTime.replace("T", " "), 8);
            forcastStoptime = DateUtil.addDateMinut(stopTime.replace("T", " "), 8);
            tvForcastStopTime.setText("截至时间: " + forcastStoptime);
        }
        userOrderBean.setRoomTitle(roomObject.getDescription());
        userOrderBean.setRoomSelect(selectRoom);
        userOrderBean.setJoinRoomTime(forcastStartTime);
        userOrderBean.setRoomSelectAmount(String.valueOf(Double.parseDouble(amountSEER) * 100000));
        userOrderBean.setId(roomObject.id.get_userId());
        userOrderBeanList.add(userOrderBean);
        String userList = new Gson().toJson(userOrderBeanList);
        CacheUtils.putString(ForcastResultActivity.this, MyApp.localWalletUser.getLocalName(), userList);
        MyApp.isChangeOrder=1;
        getData();
        customTitle.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        //返回首页
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForcastResultActivity.this, MainActivity.class);
                intent.putExtra("lanchMode", 0);
                startActivity(intent);
                finish();

            }
        });
        //查看记录
        tvSeeRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ForcastResultActivity.this, MainActivity.class);
                intent.putExtra("lanchMode", 2);
                startActivity(intent);
                finish();
            }
        });
        //继续预测
        tvContinueForcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    account_object AccountObject = null;

    private void getData() {
        if (MyApp.localWalletUser != null) {
            try {
                AccountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
                List<asset> list = BitsharesWalletWraper.getInstance().list_account_balance(AccountObject.id, true);
                String amount = String.valueOf(list.get(0).amount);
                BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
                MyApp.localWalletUser.setAmoutMoney(bigDecimal.toPlainString());
            } catch (NetworkStatusException e) {
                e.printStackTrace();
            }
        }
    }

}
