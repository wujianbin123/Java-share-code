package com.nyzc.gdm.currencyratio.homePacakage.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyzc.gdm.currencyratio.Base.AppLocalWalletUser;
import com.nyzc.gdm.currencyratio.Base.MyApp;
import com.nyzc.gdm.currencyratio.Bean.FileSave;
import com.nyzc.gdm.currencyratio.Bean.OddsBean;
import com.nyzc.gdm.currencyratio.Bean.RoomBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.BitsharesWalletWraper;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.BiBiPacakage.account_object;
import com.nyzc.gdm.currencyratio.BiBiPacakage.asset;
import com.nyzc.gdm.currencyratio.BiBiPacakage.exception.NetworkStatusException;
import com.nyzc.gdm.currencyratio.MainActivity;
import com.nyzc.gdm.currencyratio.homePacakage.interFace.ForcaseListen;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.uipacakage.StartMainActvity;
import com.nyzc.gdm.currencyratio.Utils.DoubleUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastbottomAdapter extends BaseAdapter {

    private Context context;
    private List<OddsBean> list = new ArrayList<>();
    private List<String> listOdds = new ArrayList<>();
    private RoomObject roomBean;
    OddsBean oddsBean;
    ForcaseListen forcaseListen;
    account_object AccountObject = null;
    boolean isForcast;

    public ForecastbottomAdapter(Context context) {
        this.context = context;
        forcaseListen = (ForcaseListen) context;
    }

    public void setData(RoomObject roomBean, boolean isForacast) {
        this.isForcast = isForacast;
        oddsBean = new OddsBean();
        this.roomBean = roomBean;
        this.listOdds = roomBean.getRunning_option().getSelection_description();
        for (int i = 0; i < listOdds.size(); i++) {
            oddsBean.setOdds(listOdds.get(i));
            oddsBean.setForeCast(false);
            oddsBean.setSelect(false);
            list.add(oddsBean);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_forcast_bottom_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        double total_participate = 0;
        if (roomBean.getRunning_option().getPvp_running() != null) {
            total_participate = roomBean.getRunning_option().getPvp_running().getTotal_participate().get(i);
        } else {
//            total_participate = roomBean.getRunning_option().getAdvanced_running().getTotal_participate().get(i).get(0);
        }
        double total_shares = roomBean.getRunning_option().getTotal_shares();
        String Odds;
        if (total_participate != 0) {
            double progress = total_shares / total_participate;
            Odds = DoubleUtil.savedouble(progress);
            viewHolder.tv_odds_name.setText(listOdds.get(i));
            viewHolder.tvOdds.setText("(x" + Odds + ")");
        } else {
            viewHolder.tv_odds_name.setText(listOdds.get(i));
            viewHolder.tvOdds.setText("(x" + 0 + ")");
        }
        if (roomBean.getStatus().equals("finished")) {
            viewHolder.tvOddsContent.setText("此赔率是最终派奖的赔率");
            if (i == roomBean.getFinal_result().get(0)) {
                viewHolder.tv_end.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_end.setVisibility(View.INVISIBLE);
            }
            viewHolder.tv_forecast.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tv_end.setVisibility(View.INVISIBLE);
            if (isForcast) {
                viewHolder.tv_forecast.setText("预测");
                viewHolder.tv_forecast.setEnabled(true);
                viewHolder.tv_forecast.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_forecast.setText("已截止");
                viewHolder.tv_forecast.setEnabled(false);
                viewHolder.tv_forecast.setVisibility(View.VISIBLE);
            }

        }
        viewHolder.ivOdds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = i;
                if (list.get(position).isSelect()) {
                    viewHolder.tvOddsContent.setVisibility(View.INVISIBLE);
                    list.get(position).setSelect(false);
                } else {
                    viewHolder.tvOddsContent.setVisibility(View.VISIBLE);
                    list.get(position).setSelect(true);
                }
            }
        });
        viewHolder.tv_forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_object AccountObject = null;
                int position = i;
                MyApp.localWalletUser= (AppLocalWalletUser) FileSave.read(context, "localwallet");
                if (MyApp.localWalletUser == null) {
                    StartMainActvity.start(context);
                } else {
                    if (forcaseListen != null) {
                        forcaseListen.forcastListen(position);
                    }
                }


            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_odds)
        TextView tvOdds;
        @BindView(R.id.iv_odds)
        ImageView ivOdds;
        @BindView(R.id.tv_odds_content)
        TextView tvOddsContent;
        @BindView(R.id.tv_forecast)
        TextView tv_forecast;
        @BindView(R.id.tv_end)
        TextView tv_end;
        @BindView(R.id.tv_odds_name)
        TextView tv_odds_name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
