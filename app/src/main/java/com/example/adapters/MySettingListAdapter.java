package com.example.adapters;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.suat.financialasistant.R;

import java.util.List;



public class MySettingListAdapter extends ArrayAdapter<String> {
    Context context;
    int resLayout;
    List<String> listSettingsItem;


    public MySettingListAdapter(Context context, int resLayout, List<String> listSettingsItem) {
        super(context, resLayout, listSettingsItem);
        this.context=context;
        this.resLayout=resLayout;
        this.listSettingsItem=listSettingsItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(context,resLayout,null);
        TextView tvClear=(TextView) v.findViewById(R.id.txtClearHistory);

        String settingsItem=listSettingsItem.get(position);

        tvClear.setText(settingsItem);


        return v;
    }
}
