package com.nyzc.gdm.currencyratio.uipacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Bean.AccountError;
import com.nyzc.gdm.currencyratio.Bean.AccountGraphenejBean;
import com.nyzc.gdm.currencyratio.Bean.EthAccount;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.Bean.RegistError;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.HttpUrl;
import com.nyzc.gdm.currencyratio.uipacakage.Adapter.BrainkeyAdapter;
import com.nyzc.gdm.currencyratio.uipacakage.Adapter.BrainkeyAnotherAdapter;
import com.nyzc.gdm.currencyratio.uipacakage.interFace.BrainkeyDownListen;
import com.nyzc.gdm.currencyratio.uipacakage.interFace.BrainkeyUpListen;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;
import com.nyzc.gdm.currencyratio.View.ReminderBrainkeyDailog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class CheckBrainkeyActivity extends BaseActivity implements BrainkeyUpListen, BrainkeyDownListen {


    @BindView(R.id.custom_title)
    CustomTitleBar customTitle;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_save_reminding)
    TextView tvSaveReminding;
    @BindView(R.id.gridview_brainkey)
    GridView gridviewBrainkey;
    @BindView(R.id.gridview_another_brainkey)
    GridView gridviewAnotherBrainkey;
    @BindView(R.id.tv_brainkey)
    TextView tv_brainkey;
    List<String> list = new ArrayList<>();
    BrainkeyAdapter brainkeyAdapter;
    BrainkeyAnotherAdapter brainkeyAnotherAdapter;
    List<String> listUpBrainkey = new ArrayList<>();
    List<String> listDownBrainkey = new ArrayList<>();
    BrainkeyDownListen brainkeyDownListen;
    BrainkeyUpListen brainkeyUpListen;
    ReminderBrainkeyDailog reminderBrainkeyDailog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_brainkey;
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, CheckBrainkeyActivity.class);
        context.startActivity(starter);
    }

    StringBuffer stringBuffer;


    @Override
    protected void init() {
        brainkeyDownListen = this;
        brainkeyUpListen = this;
        reminderBrainkeyDailog = new ReminderBrainkeyDailog("请勿截屏", "泄露助记词将导致财产丢失,请认真抄写并放在安全的地方，请勿截屏", CheckBrainkeyActivity.this);
        reminderBrainkeyDailog.show(getSupportFragmentManager(), "dialog");
        customTitle.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
            }
        });
        list = BrainkeyToList(MyApp.localWalletUser.getLocalBrainKey());

        brainkeyAdapter = new BrainkeyAdapter(list, CheckBrainkeyActivity.this);

        gridviewBrainkey.setAdapter(brainkeyAdapter);

        tv_brainkey.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (listUpBrainkey.size() != 16) {
                    if (tv_brainkey.getText().toString().equals("下一步")) {
                        tvSaveReminding.setText("请根据你记下的助记词，按顺序点击，验证你备份的助记词正确无误");
                        brainkeyAdapter.setData(listUpBrainkey);
                        listDownBrainkey = list;
                        Collections.shuffle(listDownBrainkey);
                        brainkeyAnotherAdapter = new BrainkeyAnotherAdapter(listDownBrainkey, CheckBrainkeyActivity.this);
                        gridviewAnotherBrainkey.setAdapter(brainkeyAnotherAdapter);
                        gridviewAnotherBrainkey.setVisibility(View.VISIBLE);
                        tv_brainkey.setText("确定");
                    } else {
                        Toast.makeText(CheckBrainkeyActivity.this, "请检查您的助记词", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    stringBuffer = new StringBuffer();
                    for (int i = 0; i < listUpBrainkey.size(); i++) {
                        if (stringBuffer.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                            stringBuffer.append(" ");
                        }
                        stringBuffer.append(listUpBrainkey.get(i));
                    }
                    Log.e("ssssssss", stringBuffer + ",,," + MyApp.localWalletUser.getLocalBrainKey());
                    if (String.valueOf(stringBuffer).equals(MyApp.localWalletUser.getLocalBrainKey())) {
                        showLoadingDialog();
//                        上链
                        CreateGraphenej();
                    } else {
                        reminderBrainkeyDailog = new ReminderBrainkeyDailog("备份失败", "请检查您的助记词", CheckBrainkeyActivity.this);
                        reminderBrainkeyDailog.show(getSupportFragmentManager(), "dialog");
                    }

                }

            }
        });
        gridviewBrainkey.setClickable(false);
        gridviewBrainkey.setPressed(false);
        gridviewBrainkey.setEnabled(false);
        gridviewBrainkey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isChange = true;
                if (brainkeyUpListen != null) {
                    brainkeyUpListen.changeUpBrainkey(listUpBrainkey.get(i), i);
                }
            }
        });
        gridviewAnotherBrainkey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridviewBrainkey.setClickable(true);
                gridviewBrainkey.setPressed(true);
                gridviewBrainkey.setEnabled(true);
                if (brainkeyDownListen != null) {
                    brainkeyDownListen.changeDownBrainkey(listDownBrainkey.get(i), i);
                }
            }
        });
    }

    private List<String> BrainkeyToList(String strs) {
        String str[] = strs.split(" ");
        for (int i = 0; i < str.length; i++) {
            list.add(str[i]);
        }
        return list;

    }

    boolean isChange = false;


    @Override
    public void changeDownBrainkey(String brainkey, int i) {

        if (isChange) {
            listDownBrainkey.add(brainkey);
            brainkeyAnotherAdapter.setData(listDownBrainkey);
        } else {
            listDownBrainkey.remove(i);
            brainkeyAnotherAdapter.setData(listDownBrainkey);
            brainkeyUpListen.changeUpBrainkey(brainkey, i);
            if (listDownBrainkey.size() == 0) {
                isChange = true;
            }
        }

    }

    @Override
    public void changeUpBrainkey(String brainkey, int i) {

        if (isChange) {
            String get_brainkey = listUpBrainkey.get(i);
            listUpBrainkey.remove(i);
            brainkeyAdapter.setData(listUpBrainkey);
            brainkeyDownListen.changeDownBrainkey(get_brainkey, i);
            isChange = false;
        } else {

            listUpBrainkey.add(brainkey);
            brainkeyAdapter.setData(listUpBrainkey);
        }
    }

    String result;
    String amount;
    BigDecimal loaclAmount;
    account_object accountObject;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismissLoadingDialog();
            switch (msg.arg1) {
                case 0:
                    FileSave.write(MyApp.context, MyApp.localWalletUser, "localwallet");
                    MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(MyApp.context, "localwallet");
                    EventBus.getDefault().post("name");
                    Intent intent = new Intent(CheckBrainkeyActivity.this, MainActivity.class);
                    intent.putExtra("lanchMode", 1);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(CheckBrainkeyActivity.this, "五分钟之内只允许注册一位用户,请稍等会注册", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(CheckBrainkeyActivity.this, "用户已存在,请重新注册", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(CheckBrainkeyActivity.this, "绑定ETH用户已存在", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(CheckBrainkeyActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    /**
     * 链接区块链
     */
    private void CreateGraphenej() {
        final Message message = new Message();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String json = new Gson().toJson(MyApp.accountGraphenejBean);
                    HttpPost httpPost = new HttpPost(HttpUrl.connect_Graphenej);
                    httpPost.setHeader("Content-Type", "application/json");
                    httpPost.setEntity(new StringEntity(json));
                    HttpResponse resp = new DefaultHttpClient().execute(httpPost);
                    HttpEntity entity = resp.getEntity();
                    result = EntityUtils.toString(entity, "UTF-8");
                    Log.e("resulettrttttt", result.toString());
                    int status = resp.getStatusLine().getStatusCode();
                    switch (status) {
                        case 500:
                            List<asset> list = null;
                            try {
                                accountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
                                list = BitsharesWalletWraper.getInstance().list_account_balance(accountObject.id, false);
                            } catch (NetworkStatusException e) {
                                e.printStackTrace();
                            }
                            amount = String.valueOf(list.get(0).amount);
                            loaclAmount = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
                            MyApp.localWalletUser.setAmoutMoney(loaclAmount.toPlainString());
                            BindEth(accountObject.getId().get_userId(), accountObject.getName());
                            break;
                        default:
                            RegistError error = new Gson().fromJson(result, RegistError.class);
                            if (error.getError().getRemote_ip() != null) {
                                message.arg1 = 1;
                                handler.sendMessage(message);
                            } else {
                                if (error.getError().getBase() != null) {
                                    message.arg1 = 2;
                                    handler.sendMessage(message);
                                } else {
                                    message.arg1 = -1;
                                    handler.sendMessage(message);
                                }
                            }
                            break;
                    }

                } catch (IOException e) {
                    Log.e("errorrr", e.getMessage() + "");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void BindEth(String id, String name) {
        final Message msg = new Message();
        OkHttpUtils.post().url(HttpUrl.bindEth)
                .addParams("seer_account_id", id)
                .addParams("seer_account_name", name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("onError", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        result = response;
                        EthAccount ethAccount = new Gson().fromJson(response.toString(), EthAccount.class);
                        switch (ethAccount.getCode()) {
                            case 0:
                                msg.arg1 = 0;
                                handler.sendMessage(msg);
//                                reminderBrainkeyDailog = new ReminderBrainkeyDailog("备份成功", "备份的助记词顺序验证正确，币比将移除该助记词", CheckBrainkeyActivity.this);
                                break;
                            case 2002:
                                msg.arg1 = 3;
                                handler.sendMessage(msg);
                                break;
                            default:
                                msg.arg1 = -1;
                                handler.sendMessage(msg);
                                break;
                        }


                    }
                });
    }


}
