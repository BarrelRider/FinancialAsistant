package com.example.adapters;

import com.example.models.CommodityItem;
import com.example.models.StockItem;
import com.example.suat.financialasistant.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import org.w3c.dom.Text;

import java.util.List;

public class MyCommodityAdapter extends ArrayAdapter<CommodityItem> {

    Context context;
    int resLayout;
    List<CommodityItem> listCommodityItem;



    public MyCommodityAdapter(Context context, int resLayout, List<CommodityItem> listCommodityItem) {
        super(context, resLayout, listCommodityItem);

        this.context=context;
        this.resLayout=resLayout;
        this.listCommodityItem=listCommodityItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=View.inflate(context,resLayout,null);

        ImageView imgComLogo=(ImageView) v.findViewById(R.id.commodityIcon);
        TextView tvComName=(TextView) v.findViewById(R.id.commodityName);
        TextView tvComDate=(TextView) v.findViewById(R.id.commodityDate);
        TextView tvComBuy=(TextView) v.findViewById(R.id.buyCommodity);
        TextView tvComSell=(TextView) v.findViewById(R.id.sellCommodity);
        TextView tvComLow=(TextView) v.findViewById(R.id.lowCommodity);
        TextView tvComHigh=(TextView) v.findViewById(R.id.highCommodity);

        CommodityItem commodityItem=listCommodityItem.get(position);

        imgComLogo.setImageResource(commodityItem.getResCompIcon());
        tvComName.setText(commodityItem.getCommodityName());
        tvComDate.setText(commodityItem.getCommodityDate());
        tvComBuy.setText(commodityItem.getBuy());
        tvComSell.setText(commodityItem.getSell());
        tvComLow.setText(commodityItem.getLowCommodity());
        tvComHigh.setText(commodityItem.getHighCommodity());


        return v;

    }
}
