package com.nyzc.gdm.currencyratio.uipacakage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nyzc.gdm.currencyratio.Bean.RoomBean;
import com.nyzc.gdm.currencyratio.Bean.Rooms;
import com.nyzc.gdm.currencyratio.BiBiPacakage.RoomObject;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForestListAdapter extends BaseAdapter {

    private Context context;
    private List<RoomObject> list = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    public ForestListAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<RoomObject> lists) {
        this.list = lists;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_forecast_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvIfnos.setText(list.get(i).getDescription());
        String stopTime = DateUtil.addDateMinut(list.get(i).getOption().getStop().replace("T", " "), 8);
        viewHolder.tvTime.setText("截止时间 :" + stopTime);
        if (list.get(i).getRunning_option().getSelection_description().size() != 0) {
            int des_num = list.get(i).getRunning_option().getSelection_description().size();
            switch (des_num) {
                case 1:
                    viewHolder.tv_selection_one.setText(list.get(i).getRunning_option().getSelection_description().get(0));
                    viewHolder.tv_selection_two.setVisibility(View.GONE);
                    viewHolder.tv_selection_three.setVisibility(View.GONE);
                    break;
                case 2:
                    viewHolder.tv_selection_one.setText(list.get(i).getRunning_option().getSelection_description().get(0));
                    viewHolder.tv_selection_two.setText(list.get(i).getRunning_option().getSelection_description().get(1));
                    viewHolder.tv_selection_three.setVisibility(View.GONE);
                    viewHolder.tv_selection_two.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    viewHolder.tv_selection_one.setText(list.get(i).getRunning_option().getSelection_description().get(0));
                    viewHolder.tv_selection_two.setText(list.get(i).getRunning_option().getSelection_description().get(1));
                    viewHolder.tv_selection_three.setText(list.get(i).getRunning_option().getSelection_description().get(2));
                    viewHolder.tv_selection_two.setVisibility(View.VISIBLE);
                    viewHolder.tv_selection_three.setVisibility(View.VISIBLE);
                    break;
                default:
                    viewHolder.tv_selection_one.setText(list.get(i).getRunning_option().getSelection_description().get(0));
                    viewHolder.tv_selection_two.setText(list.get(i).getRunning_option().getSelection_description().get(1));
                    viewHolder.tv_selection_three.setText("更多");
                    viewHolder.tv_selection_two.setVisibility(View.VISIBLE);
                    viewHolder.tv_selection_three.setVisibility(View.VISIBLE);
                    break;
            }
        }
        BigDecimal total = new BigDecimal(String.valueOf(list.get(i).getRunning_option().getTotal_shares())).divide(new BigDecimal(String.valueOf(100000)), 0, BigDecimal.ROUND_DOWN).stripTrailingZeros();
        viewHolder.tvPfc.setText(total.toPlainString());
        viewHolder.tvNum.setText(decimalFormat.format(list.get(i).getRunning_option().getTotal_player_count()) + "");
        if (list.get(i).getOption().getResult_owner_percent() != 0) {
            double resultOwnerPercent = list.get(i).getOption().getResult_owner_percent();
            double ownerPercent = resultOwnerPercent / 100;
            viewHolder.tvTongji.setText(decimalFormat.format(ownerPercent) + "%");
        }
        return view;

    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rela_tv_time)
        RelativeLayout relaTvTime;
        @BindView(R.id.tv_ifnos)
        TextView tvIfnos;
        @BindView(R.id.ll_weight)
        LinearLayout llWeight;
        @BindView(R.id.tv_pfc)
        TextView tvPfc;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_tongji)
        TextView tvTongji;
        @BindView(R.id.rela_time)
        RelativeLayout relaTime;
        @BindView(R.id.iv_collect)
        ImageView iv_collect;
        @BindView(R.id.tv_selection_one)
        TextView tv_selection_one;
        @BindView(R.id.tv_selection_two)
        TextView tv_selection_two;
        @BindView(R.id.tv_selection_three)
        TextView tv_selection_three;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
