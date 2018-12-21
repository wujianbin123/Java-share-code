package com.nyzc.gdm.currencyratio.Base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.nyzc.gdm.currencyratio.Bean.AccountGraphenejBean;
import com.nyzc.gdm.currencyratio.Bean.BlockRoomHeader;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.Bean.GetHouseBean;
import com.nyzc.gdm.currencyratio.Bean.ImportBrainkeyAccountBean;
import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.Utils.CacheUtils;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;
import com.nyzc.gdm.currencyratio.Utils.HttpUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import cy.agorise.graphenej.api.android.NodeConnection;
import cy.agorise.graphenej.interfaces.NodeErrorListener;
import cy.agorise.graphenej.models.BaseResponse;
import cy.agorise.graphenej.test.NaiveSSLContext;

public class MyApp extends MultiDexApplication {
    MyApp app;
    public static Context context;
    public static AppLocalWalletUser localWalletUser = null;
    public static AccountGraphenejBean accountGraphenejBean=null;
    public static ImportBrainkeyAccountBean importAccountBean = null;
    NodeConnection nodeConnection;
    public static WebSocketFactory factory;
    SSLContext sslContext;
    private final int TIMEOUT = 5000;
    public static List<String> listFinishRoom = new ArrayList<>();
    public static List<String> listRoom = new ArrayList<>();
    public static int isChangeOrder=0;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static List<GetHouseBean> getHouseBean;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = this;
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        BitsharesWalletWraper.getInstance().build_connect();
        ConnectSEER();
        factory = new WebSocketFactory().setConnectionTimeout(TIMEOUT);
        sslContext = null;
        try {
            sslContext = NaiveSSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        factory.setSSLContext(sslContext);
        initOkhttp();
        List<String> list = new ArrayList<>();
        list.add("1.14.3");
        list.add("1.14.4");
        list.add("1.14.5");
        if (BitsharesWalletWraper.getInstance().build_connect() == 0) {
            try {
                getHouseBean = BitsharesWalletWraper.getInstance().getHouse(list);
                for (int i = 0; i < getHouseBean.size(); i++) {
                    for (int j = 0; j < getHouseBean.get(i).getRooms().size(); j++) {
                        listRoom.add(getHouseBean.get(i).getRooms().get(j));
                    }

                    for (int j = 0; j < getHouseBean.get(i).getFinished_rooms().size(); j++) {
                        listFinishRoom.add(getHouseBean.get(i).getFinished_rooms().get(j));
                    }
                }
            } catch (NetworkStatusException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }


    /**
     * 链接SEER
     */
    private void ConnectSEER() {
        nodeConnection = new NodeConnection();
        nodeConnection.addNodeUrl(HttpUrl.Base_Url);
        nodeConnection.connect("", "", true, new NodeErrorListener() {
            @Override
            public void onError(BaseResponse.Error error) {
                Log.e("error", error.message);
            }
        });
    }

    private void initOkhttp() {
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

}
