package com.nyzc.gdm.currencyratio.uipacakage;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;
import com.nyzc.gdm.currencyratio.View.RemindInputBrainkeyDialog;
import com.nyzc.gdm.currencyratio.View.ReminderDeleteWalletDailog;

import butterknife.BindView;

public class WalletManagerActivity extends BaseActivity {


    @BindView(R.id.custom_title)
    CustomTitleBar customTitle;
    @BindView(R.id.tv_set_pwd)
    TextView tvSetPwd;
    @BindView(R.id.tv_set_delete)
    TextView tvSetDelete;
    ReminderDeleteWalletDailog reminderDeleteWalletDailog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_manager;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WalletManagerActivity.class);
        context.startActivity(starter);
    }

    RemindInputBrainkeyDialog dialog;

    @Override
    protected void init() {
        customTitle.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        tvSetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderDeleteWalletDailog = new ReminderDeleteWalletDailog(WalletManagerActivity.this, new ReminderDeleteWalletDailog.ConfirmWalletListen() {
                    @Override
                    public void delete(boolean isdelete) {
                        if (isdelete) {
                            dialog = new RemindInputBrainkeyDialog("请输入钱包密码", new RemindInputBrainkeyDialog.SendBackListener() {
                                @Override
                                public void sendBack(String inputText) {
                                    boolean isDelete = FileSave.deleteFile(WalletManagerActivity.this, "localwallet");
                                    if (isDelete) {
                                        MyApp.localWalletUser = null;
                                        StartMainActvity.start(WalletManagerActivity.this);
                                        finish();
//                                        Toast.makeText(WalletManagerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        FileSave.deleteFile(WalletManagerActivity.this, "localwallet");
                                    }
                                    dialog.dismiss();
                                }
                            });
                            dialog.show(getSupportFragmentManager(), "dialog");
                        }
                    }
                });
                reminderDeleteWalletDailog.show(getSupportFragmentManager(), "dialog");


            }
        });
        tvSetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetPwdActivity.start(WalletManagerActivity.this);
            }
        });
    }


}
