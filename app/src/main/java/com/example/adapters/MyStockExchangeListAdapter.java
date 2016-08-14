package com.example.adapters;

import com.example.models.StockItem;
import com.example.suat.financialasistant.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

/**
 * Created by Suat on 14.08.2016.
 */
public class MyStockExchangeListAdapter extends ArrayAdapter<StockItem> {

    Context context;
    int resLayout;
    List<StockItem> listStockItem;



    public MyStockExchangeListAdapter(Context context, int resLayout, List<StockItem> listStockItem) {
        super(context, resLayout, listStockItem);

        this.context=context;
        this.resLayout=resLayout;
        this.listStockItem=listStockItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(context,resLayout,null);

        ImageView imgCompanyLogo=(ImageView) v.findViewById(R.id.stock_icon);
        TextView tvCompanyName=(TextView) v.findViewById(R.id.companyName);
        TextView tvStockDate=(TextView) v.findViewById(R.id.stock_date);
        TextView tvBuy=(TextView) v.findViewById(R.id.buy);
        TextView tvSell=(TextView) v.findViewById(R.id.sell);

        StockItem stockItem=listStockItem.get(position);

        imgCompanyLogo.setImageResource(stockItem.getResCompIcon());
        tvCompanyName.setText(stockItem.getCompanyName());
        tvStockDate.setText(stockItem.getStockDate());
        tvBuy.setText(stockItem.getBuy());
        tvSell.setText(stockItem.getSell());



        return v;

    }
}
