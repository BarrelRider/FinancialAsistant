package com.example.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.models.NavItem;
import com.example.suat.financialasistant.R;

import java.util.List;

public class MyNavListAdapter extends ArrayAdapter<NavItem> {

    Context context;
    int resLayout;
    List<NavItem> listNavItems;



    public MyNavListAdapter(Context context, int resLayout, List<NavItem> listNavItems) {
        super(context, resLayout, listNavItems);

        this.context=context;
        this.resLayout=resLayout;
        this.listNavItems=listNavItems;
    }


    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {

        View v= View.inflate(context, resLayout, null);

        TextView tvTitle= (TextView) v.findViewById(R.id.title);
        TextView tvSubtitle=(TextView) v.findViewById(R.id.subtitle);
        ImageView navIcon= (ImageView)v.findViewById(R.id.nav_icon);

        NavItem navItem = listNavItems.get(position);

        tvTitle.setText(navItem.getTitle());
        tvSubtitle.setText(navItem.getSubTitle());
        navIcon.setImageResource(navItem.getResIcon());

        return v;
    }
}
