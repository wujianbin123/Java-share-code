package com.nyzc.gdm.currencyratio.accountPacakage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nyzc.gdm.currencyratio.Bean.WaitAccountBean;
import com.nyzc.gdm.currencyratio.R;
import com.nyzc.gdm.currencyratio.Utils.DateUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranstionRecorderAdapters extends BaseAdapter {


    Context context;
    WaitAccountBean list;
    String amount;
    String fee;

    public TranstionRecorderAdapters(Context context, WaitAccountBean waitAccountBean) {
        this.context = context;
        this.list = waitAccountBean;
    }


    @Override

    public int getCount() {
        return list.getListData().size();
    }

    @Override
    public Object getItem(int i) {
        return list.getListData().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_transtion_recorder_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (list.getListData().size() != 0) {
            for (int g = 0; g < list.getListData().size(); g++) {
                viewHolder.transTime.setText(DateUtil.addDateMinut(list.getListData().get(i).replace("T", " "), 8));
                viewHolder.transAccount.setText(list.getListUserTo().get(i).getName());
                amount = String.valueOf(list.getTransationBeanList().get(i).amount.amount);
                fee = String.valueOf(list.getTransationBeanList().get(i).fee.amount);
                viewHolder.transFee.setText("手续费: " + getBigDecimal(fee));
                if (list.getListUserTo().get(i).isOneSelf()) {
                    viewHolder.transMoney.setTextColor(context.getResources().getColor(R.color.pwd_strong));
                    viewHolder.transFee.setTextColor(context.getResources().getColor(R.color.pwd_strong));
                    viewHolder.trans_money_top.setTextColor(context.getResources().getColor(R.color.pwd_strong));
                    viewHolder.transMoney.setText("交易金额: " + "+ "+getBigDecimal(amount));
                    viewHolder.transFee.setText("手续费: " + getBigDecimal(fee));
                    viewHolder.trans_money_top.setText("+ "+getBigDecimal(amount));
                }else{
                    viewHolder.transMoney.setTextColor(context.getResources().getColor(R.color.pwd_weak));
                    viewHolder.transFee.setTextColor(context.getResources().getColor(R.color.pwd_weak));
                    viewHolder.trans_money_top.setTextColor(context.getResources().getColor(R.color.pwd_weak));
                    viewHolder.transMoney.setText("交易金额: " + "- "+getBigDecimal(amount));

                    viewHolder.trans_money_top.setText("- "+getBigDecimal(amount));
                }


            }
        }

        return view;
    }

    private String getBigDecimal(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal("100000")).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    static class ViewHolder {
        @BindView(R.id.trans_time)
        TextView transTime;
        @BindView(R.id.trans_type)
        TextView transType;
        @BindView(R.id.trans_account)
        TextView transAccount;
        @BindView(R.id.trans_money)
        TextView transMoney;
        @BindView(R.id.trans_fee)
        TextView transFee;
        @BindView(R.id.trans_money_top)
        TextView trans_money_top;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


