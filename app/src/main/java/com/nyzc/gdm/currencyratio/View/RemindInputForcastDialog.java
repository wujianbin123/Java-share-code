package com.nyzc.gdm.currencyratio.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.accountPacakage.RechargeActivity;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;


public class RemindInputForcastDialog extends DialogFragment {

    //点击发表，内容不为空时的回调
    public SendBackListener sendBackListener;

    public interface SendBackListener {
        void sendBack(String inputText);
    }

    private ProgressDialog progressDialog;
    private String texthint;

    private Dialog dialog;
    private EditText inputDlg, edi_input_forcast_money;
    private int numconut = 300;
    private String tag = null;
    private TextView tv_service_bee;
    private TextView tv_pwd_service_bee;
    private ImageView iv_back, iv_back_forcast_amount;
    private TextView tv_cofirm_forcast_amount, tv_pwd_input_remind, tv_pwd_remind_money, tv_foracat_limit, tv_how_get_seer;
    TextView user_select;
    private ConstraintLayout constrain_check_forcast_amount, constrain_check_brainkey_pwd;
    String amount;
    String mix, max;

    public RemindInputForcastDialog() {
    }


    @SuppressLint("ValidFragment")
    public RemindInputForcastDialog(String texthint, String mix, String max, SendBackListener sendBackListener) {//提示文字
        this.texthint = texthint;
        this.sendBackListener = sendBackListener;
        this.mix = mix;
        this.max = max;
        getData();
    }

    account_object AccountObject = null;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentview = View.inflate(getActivity(), R.layout.forcast_dialog_layout, null);
        dialog.setContentView(contentview);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        inputDlg = contentview.findViewById(R.id.edi_input_brainkey_pwd);
        final TextView tv_cofirm_forcast_amount = contentview.findViewById(R.id.tv_cofirm_forcast_amount);
        final TextView tv_send = contentview.findViewById(R.id.tv_cofirm_brainkey);
        tv_service_bee = contentview.findViewById(R.id.tv_service_bee);
        tv_pwd_service_bee = contentview.findViewById(R.id.tv_pwd_service_bee);
        user_select = contentview.findViewById(R.id.user_select);
        iv_back = contentview.findViewById(R.id.iv_back);
        tv_foracat_limit = contentview.findViewById(R.id.tv_foracat_limit);
        tv_pwd_remind_money = contentview.findViewById(R.id.tv_pwd_remind_money);
        iv_back_forcast_amount = contentview.findViewById(R.id.iv_back_forcast_amount);
        edi_input_forcast_money = contentview.findViewById(R.id.edi_input_forcast_money);
        constrain_check_forcast_amount = contentview.findViewById(R.id.constrain_check_forcast_amount);
        constrain_check_brainkey_pwd = contentview.findViewById(R.id.constrain_check_brainkey_pwd);
        tv_pwd_input_remind = contentview.findViewById(R.id.tv_pwd_input_remind);
        tv_how_get_seer = contentview.findViewById(R.id.tv_how_get_seer);
        user_select.setText(texthint);
        tv_foracat_limit.setText("预测范围: " + mix + "-" + max + "SEER");
        tv_pwd_remind_money.setText(MyApp.localWalletUser.getAmoutMoney() + "SEER");
        tv_how_get_seer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyApp.context, RechargeActivity.class));
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputDlg.getText().toString())) {
                    Toast.makeText(getActivity(), "输入内容为空", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (MyApp.localWalletUser.getLocalPwd().equals(inputDlg.getText().toString())) {
//                        getShowsDialog();
                        sendBackListener.sendBack(amount);
                    } else {
                        tv_pwd_input_remind.setText("您输入的密码有误");
                        tv_pwd_input_remind.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tv_cofirm_forcast_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edi_input_forcast_money.getText().toString())) {
                    Toast.makeText(getActivity(), "输入内容为空", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    amount = edi_input_forcast_money.getText().toString();
                    if (Double.parseDouble(amount) >= Double.parseDouble(mix) && Double.parseDouble(amount) <= Double.parseDouble(max)) {
                        if (Double.parseDouble(amount) + 5 > Double.parseDouble(MyApp.localWalletUser.getAmoutMoney())) {
                            tv_service_bee.setText("余额不足");
                            tv_service_bee.setTextColor(MyApp.context.getResources().getColor(R.color.pwd_weak));
                            return;
                        }
//                        sendBackListener.sendBack(inputDlg.getText().toString());
                        tv_service_bee.setText("手续费");
                        tv_service_bee.setTextColor(MyApp.context.getResources().getColor(R.color.divider));
                        tv_pwd_service_bee.setVisibility(View.VISIBLE);
                        tv_service_bee.setVisibility(View.VISIBLE);
                        constrain_check_forcast_amount.setVisibility(View.GONE);
                        constrain_check_brainkey_pwd.setVisibility(View.VISIBLE);
                    } else {
                        tv_service_bee.setText("不在预测范围");
                        tv_service_bee.setTextColor(MyApp.context.getResources().getColor(R.color.pwd_weak));
                        tv_pwd_service_bee.setVisibility(View.GONE);
                        tv_service_bee.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        iv_back_forcast_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        inputDlg.setFocusable(true);
        inputDlg.setFocusableInTouchMode(true);
        inputDlg.requestFocus();
        final Handler hanler = new Handler();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public InputMethodManager mInputMethodManager;

            @Override
            public void onDismiss(DialogInterface dialog) {
                hanler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftkeyboard();
                    }
                }, 200);

            }
        });
        return dialog;
    }

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


    public void hideProgressdialog() {
        progressDialog.cancel();
    }


    public void hideSoftkeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }


}
