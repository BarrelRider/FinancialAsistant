package com.example.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import com.example.suat.financialasistant.R;
import com.example.models.MoneyItem;
import android.content.Context;
import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<MoneyItem> {

    Context context;
    int resLayout;
    List<MoneyItem> listMoneyItem;

    public MyListViewAdapter(Context context,int resLayout,List<MoneyItem> listMoneyItem)
    {
        super(context, resLayout, listMoneyItem);
        this.context=context;
        this.resLayout=resLayout;
        this.listMoneyItem=listMoneyItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(context,resLayout,null);

        TextView tvMoneyType=(TextView) v.findViewById(R.id.moneyType);
        TextView tvMoneyLow=(TextView) v.findViewById(R.id.moneyLow);
        TextView tvMoneyHigh=(TextView) v.findViewById(R.id.moneyHigh);

        MoneyItem moneyItem= listMoneyItem.get(position);

        tvMoneyType.setText(moneyItem.getMoneyType());
        tvMoneyLow.setText(moneyItem.getMoneyLow());
        tvMoneyHigh.setText(moneyItem.getMoneyHigh());

        return v;
    }
}
