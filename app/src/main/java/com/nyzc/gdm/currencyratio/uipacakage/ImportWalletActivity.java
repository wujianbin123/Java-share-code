package com.nyzc.gdm.currencyratio.uipacakage;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Base.BaseActivity;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.Bean.ImportBrainkeyAccountBean;
import com.nyzc.gdm.currencyratio.Bean.Result;
import com.nyzc.gdm.currencyratio.Bean.ResultBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.HttpUrl;
import com.nyzc.gdm.currencyratio.View.CustomTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import cy.agorise.graphenej.Address;
import cy.agorise.graphenej.BrainKey;
import cy.agorise.graphenej.PublicKey;
import cy.agorise.graphenej.UserAccount;
import cy.agorise.graphenej.api.GetKeyReferences;
import cy.agorise.graphenej.interfaces.WitnessResponseListener;
import cy.agorise.graphenej.models.BaseResponse;
import cy.agorise.graphenej.models.WitnessResponse;

public class ImportWalletActivity extends BaseActivity implements WitnessResponseListener {


    @BindView(R.id.custom_title)
    CustomTitleBar customTitle;
    @BindView(R.id.rela_input_brainkey)
    RelativeLayout relaInputBrainkey;
    @BindView(R.id.tv_create_wallet_pwd)
    TextView tvCreateWalletPwd;
    @BindView(R.id.edit_input_wallet_pwd)
    EditText editInputWalletPwd;
    @BindView(R.id.edi_input_brainkey)
    EditText edi_input_brainkey;
    @BindView(R.id.rela_wallet_pwd)
    RelativeLayout relaWalletPwd;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_create_wallet_pwd_again)
    TextView tvCreateWalletPwdAgain;
    @BindView(R.id.edit_input_wallet_pwd_again)
    EditText editInputWalletPwdAgain;
    @BindView(R.id.rela_wallet_pwd_again)
    RelativeLayout relaWalletPwdAgain;
    @BindView(R.id.rela_import_wallet)
    RelativeLayout relaImportWallet;
    @BindView(R.id.iv_agree_service)
    ImageView ivAgreeService;
    @BindView(R.id.tv_service_agreement)
    TextView tvServiceAgreement;
    @BindView(R.id.ll_read_service)
    LinearLayout llReadService;
    @BindView(R.id.tv_import_wallet)
    TextView tvImportWallet;


    @Override
    public int getLayoutId() {
        return R.layout.activity_import_wallet;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ImportWalletActivity.class);
        context.startActivity(starter);
    }

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
        ivAgreeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivAgreeService.isSelected()) {
                    ivAgreeService.setSelected(false);
                } else {
                    ivAgreeService.setSelected(true);
                }
            }
        });
        tvImportWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCheck();

            }
        });
        tvServiceAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceActivity.start(ImportWalletActivity.this,1);
            }
        });
    }

    BrainKey mBrainKey;
    Address address;
    GetKeyReferences getKeyReferences;
    WebSocket mWebSocket;
    String brainkey;
    String pwd;
    String pwd_again;
    private String privateOwnerKeys, publicOwnerKeys;
    public static final int DEFAULT_SEQUENCE_NUMBER_0 = 0;
    public static final int DEFAULT_SEQUENCE_NUMBER_1 = 1;
    private PublicKey publicAddress;

    private void getCheck() {
        pwd = editInputWalletPwd.getText().toString();
        pwd_again = editInputWalletPwdAgain.getText().toString();
        brainkey = edi_input_brainkey.getText().toString();
        if (TextUtils.isEmpty(brainkey.toString())) {
            Toast.makeText(ImportWalletActivity.this, "请输入您的助记词", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (pwd.length() < 8) {
                Toast.makeText(this, "密码请输入不少于8位字符", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (TextUtils.isEmpty(pwd_again)) {
            Toast.makeText(ImportWalletActivity.this, "请再次输入您的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd_again.equals(pwd)) {
            Toast.makeText(ImportWalletActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (MyApp.importAccountBean != null) {
                if (!pwd.equals(MyApp.importAccountBean.getImport_account_pwd())) {
                    Toast.makeText(this, "输入的密码与上次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        if (!ivAgreeService.isSelected()) {
            Toast.makeText(ImportWalletActivity.this, "请勾选协议", Toast.LENGTH_SHORT).show();
            return;
        }
        mBrainKey = new BrainKey(brainkey, BrainKey.DEFAULT_SEQUENCE_NUMBER);
        CreateBrainkey(brainkey, DEFAULT_SEQUENCE_NUMBER_0);
//        CreateBrainkey(brainkey,DEFAULT_SEQUENCE_NUMBER_1);
        getKeyReferences = new GetKeyReferences(address, true, this);
        try {
            mWebSocket = MyApp.factory.createSocket(HttpUrl.Base_Url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mWebSocket.addListener(getKeyReferences);
                    mWebSocket.connect();
                } catch (WebSocketException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void CreateBrainkey(String brainkey, int DEFAULT_SEQUENCE_NUMBER) {
        mBrainKey = new BrainKey(brainkey, DEFAULT_SEQUENCE_NUMBER);
        address = mBrainKey.getPublicAddress(Address.BITSHARES_PREFIX);
        publicAddress = address.getPublicKey();
//        //0代表账户权限  1代表资金权限
        if (DEFAULT_SEQUENCE_NUMBER == 0) {
            publicActiveKey = publicAddress.getAddress();
            privateActiveKey = mBrainKey.getWalletImportFormat();
        } else {
            publicOwnerKeys = publicAddress.getAddress();
            privateOwnerKeys = mBrainKey.getWalletImportFormat();
        }

    }

    String privateActiveKey, publicActiveKey;
    ResultBean resultBean;
    BigDecimal loaclAmount;

    @Override
    public void onSuccess(WitnessResponse response) {
        WitnessResponse<List<List<UserAccount>>> witnessResponse = response;
        if (witnessResponse.result != null) {
//            Toast.makeText(ImportWalletActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
            String result = witnessResponse.result.get(0).get(0).toJsonString();
            resultBean = new Gson().fromJson(result, ResultBean.class);
            try {
                account_object accountObject = BitsharesWalletWraper.getInstance().get_account_object(resultBean.getId());
                try {
                    List<asset> list = BitsharesWalletWraper.getInstance().list_account_balance(accountObject.id, true);
                    String amount = String.valueOf(list.get(0).amount);
                    loaclAmount = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
                } catch (NetworkStatusException e) {
                    e.printStackTrace();
                }
                getSaveImportAccount(accountObject.name, loaclAmount.toPlainString());
            } catch (NetworkStatusException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(ImportWalletActivity.this, MainActivity.class);
            intent.putExtra("lanchMode", 1);
            startActivity(intent);
            finish();
        }
    }

    AppLocalWalletUser localWalletUser;

    /**
     * 保存通过导入助记词的用户
     */
    private void getSaveImportAccount(String name, String amount) {
        localWalletUser = new AppLocalWalletUser();
        localWalletUser.setLocalName(name);
        localWalletUser.setLocalPwd(pwd);
        localWalletUser.setLocalBrainKey(brainkey);
        localWalletUser.setLocalOwnerPrivateKey(null);
        localWalletUser.setLocalOwnerPublicKey(null);
        localWalletUser.setLocalActivePrivateKey(privateActiveKey);
        localWalletUser.setLocalActivePublicKey(publicActiveKey);
        localWalletUser.setAmoutMoney(amount);
        MyApp.localWalletUser = localWalletUser;
        FileSave.write(ImportWalletActivity.this, localWalletUser, "localwallet");
        MyApp.localWalletUser = (AppLocalWalletUser) FileSave.read(ImportWalletActivity.this, "localwallet");


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(ImportWalletActivity.this, "请检查您的助记词", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onError(BaseResponse.Error error) {
        handler.sendMessage(new Message());

    }
}
