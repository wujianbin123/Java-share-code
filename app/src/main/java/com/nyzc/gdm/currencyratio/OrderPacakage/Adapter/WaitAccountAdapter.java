package com.nyzc.gdm.currencyratio.OrderPacakage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nyzc.gdm.currencyratio.Bean.UserOrderBean;
import com.nyzc.gdm.currencyratio.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaitAccountAdapter extends BaseAdapter {


    private List<UserOrderBean> userOrderBeanList;
    private Context context;

    public WaitAccountAdapter(List<UserOrderBean> userOrderBeanList, Context context) {
        this.userOrderBeanList = userOrderBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (userOrderBeanList.size() == 0) {
            return 0;
        }
        return userOrderBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return userOrderBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_wait_account_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (userOrderBeanList.get(i).getRoomTitle() != null) {
            viewHolder.tvWaitAccountTitle.setText(userOrderBeanList.get(i).getRoomTitle());
        }
        if (userOrderBeanList.get(i).getRoomSelect() != null && userOrderBeanList.get(i).getRoomSelectAmount() != null) {
            String amount = getBigDecimal(userOrderBeanList.get(i).getRoomSelectAmount());
            viewHolder.tvWaitSelectAmount.setText(userOrderBeanList.get(i).getRoomSelect() + "  " + amount + "SEER");
        }
        if (userOrderBeanList.get(i).getJoinRoomTime() != null) {
            viewHolder.tvJoinTime.setText("参与时间: " + userOrderBeanList.get(i).getJoinRoomTime());
        }
        if (userOrderBeanList.get(i).getReward() != null) {
            if (!userOrderBeanList.get(i).getReward().equals("未中奖")) {
                viewHolder.tv_wait_raward_amount.setTextColor(context.getResources().getColor(R.color.pwd_strong));
                viewHolder.tv_wait_raward_amount.setText("+ " + getBigDecimal(userOrderBeanList.get(i).getReward())+ "SEER");
            } else {
                viewHolder.tv_wait_raward_amount.setTextColor(context.getResources().getColor(R.color.pwd_weak));
                viewHolder.tv_wait_raward_amount.setText(userOrderBeanList.get(i).getReward() );
            }
        } else {
            viewHolder.tv_wait_raward_amount.setText("");
        }

        return view;
    }

    private String getBigDecimal(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    static class ViewHolder {
        @BindView(R.id.tv_wait_account_title)
        TextView tvWaitAccountTitle;
        @BindView(R.id.tv_wait_select_amount)
        TextView tvWaitSelectAmount;
        @BindView(R.id.tv_join_time)
        TextView tvJoinTime;
        @BindView(R.id.tv_wait_raward_amount)
        TextView tv_wait_raward_amount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
