package com.nyzc.gdm.currencyratio.accountPacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.chain.signed_transaction;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;

public class TransferAccountActivity extends BaseActivity {


    @BindView(R.id.rela_add_account_addres)
    RelativeLayout rela_add_account_addres;
    @BindView(R.id.custom_title)
    CustomTitleBar custom_title;
    @BindView(R.id.tv_remind_money)
    TextView tv_remind_money;
    @BindView(R.id.edit_input_transfer_address_user)
    EditText edit_input_transfer_address_user;
    @BindView(R.id.edit_input_wallet_ammount)
    EditText edit_input_wallet_ammount;
    @BindView(R.id.tv_trans_wallet)
    TextView tv_trans_wallet;
    String user_name, user_ammount;


    public static void start(Context context) {
        Intent starter = new Intent(context, TransferAccountActivity.class);
        context.startActivity(starter);
    }

    account_object AccountObject = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_account;
    }


    @Override
    protected void init() {


//        showLoadingDialog();
        custom_title.setOnTitleClickListener(new CustomTitleBar.TitleOnClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });

        tv_trans_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user_name = edit_input_transfer_address_user.getText().toString().trim();
                user_ammount = edit_input_wallet_ammount.getText().toString().trim();
                if (user_name == null || user_name.equals("")) {
                    Toast.makeText(TransferAccountActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user_ammount == null || user_ammount.equals("")) {
                    Toast.makeText(TransferAccountActivity.this, "请检查转账金额", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (Double.parseDouble(user_ammount) <= 0) {
                        Toast.makeText(TransferAccountActivity.this, "转账金额必须大于0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (Double.parseDouble(user_ammount) > amountRemind || Double.parseDouble(user_ammount) + 2 > amountRemind) {
                    Toast.makeText(TransferAccountActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoadingDialog();
                if (MyApp.localWalletUser != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            account_object accountObject = null;
                            try {
                                accountObject = BitsharesWalletWraper.getInstance().get_account_object(user_name);
                            } catch (NetworkStatusException e) {
                                Toast.makeText(TransferAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            if (accountObject != null) {
                                signed_transaction signedTransaction = null;
                                try {
                                    signedTransaction = BitsharesWalletWraper.getInstance().transfer(MyApp.localWalletUser.getLocalName(), user_name, String.valueOf(user_ammount), "SEER", "");
                                } catch (NetworkStatusException e) {
                                    Toast.makeText(TransferAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                if (signedTransaction != null) {
                                    getData();
                                    dismissLoadingDialog();
                                    Toast.makeText(TransferAccountActivity.this, "转账成功", Toast.LENGTH_SHORT).show();
//                                    TransSuccess.start(TransferAccountActivity.this);
//                                    finish();
                                }
                            } else {
                                Toast.makeText(TransferAccountActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                                dismissLoadingDialog();
                            }
                        }
                    }, 2500);
                }
            }
        });

    }

    private void getData() {
        if (MyApp.localWalletUser != null) {
            try {
                AccountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
                List<asset> list = BitsharesWalletWraper.getInstance().list_account_balance(AccountObject.id, true);
                String amount = String.valueOf(list.get(0).amount);
                BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
                MyApp.localWalletUser.setAmoutMoney(bigDecimal.toPlainString());
                tv_remind_money.setText(bigDecimal.toPlainString() + "SEER");
            } catch (NetworkStatusException e) {
                e.printStackTrace();
            }
        }
    }

    Double amountRemind;

    @Override
    protected void onResume() {
        super.onResume();
        if (BitsharesWalletWraper.getInstance().build_connect() == 0) {
            if (MyApp.localWalletUser != null) {
                try {
                    AccountObject = BitsharesWalletWraper.getInstance().get_account_object(MyApp.localWalletUser.getLocalName());
                    List<asset> list = BitsharesWalletWraper.getInstance().list_account_balance(AccountObject.id, true);
                    String amount = String.valueOf(list.get(0).amount);
                    BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
                    amountRemind = bigDecimal.doubleValue();
                    tv_remind_money.setText(bigDecimal.toPlainString() + "SEER");
                } catch (NetworkStatusException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }
}
