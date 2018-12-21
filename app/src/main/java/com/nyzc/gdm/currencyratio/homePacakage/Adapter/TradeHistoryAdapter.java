package com.nyzc.gdm.currencyratio.homePacakage.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nyzc.gdm.currencyratio.Bean.RoomBean;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TradeHistoryAdapter extends BaseAdapter {

    private Context context;
    private RoomObject roomBean;
    List<RoomBean.ResultBean.RunningOptionBean.ParticipatorsBean> list = new ArrayList<>();
    List<String> listSelect = new ArrayList<>();

    public TradeHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(RoomObject roomBean) {
        this.roomBean = roomBean;
        listSelect = roomBean.getRunning_option().getSelection_description();
        for (int i = 0; i < roomBean.getRunning_option().getParticipators().size(); i++) {
            if (roomBean.getRunning_option().getParticipators().get(i).size() != 0) {
                for (int g = 0; g < roomBean.getRunning_option().getParticipators().get(i).size(); g++) {
//                    list.add(roomBean.getRunning_option().getParticipators().get(i).get(g));
//                    Log.e("tradehistory",list.get(i).get)
                }
            }
        }

    }

    @Override
    public int getCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_trade_history_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTradeUser.setText(list.get(i).getPlayer());
        viewHolder.tvSendPrize.setText(list.get(i).getPaid()+"");
        viewHolder.tvTradeSelect.setText(list.get(i).getSequence()+"");
        viewHolder.tvTradeNum.setText(list.get(i).getAmount()+"");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_trade_user)
        TextView tvTradeUser;
        @BindView(R.id.tv_trade_select)
        TextView tvTradeSelect;
        @BindView(R.id.tv_trade_num)
        TextView tvTradeNum;
        @BindView(R.id.tv_send_prize)
        TextView tvSendPrize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
