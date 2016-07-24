package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapters.MyListViewAdapter;
import com.example.models.MoneyItem;
import com.example.suat.financialasistant.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment {

    ListView listView;
    public String veri;
    List<MoneyItem> listMoneyItem;
    View v;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){

        v=inflater.inflate(R.layout.fragment3_layout,container,false);
        listView= (ListView) v.findViewById(R.id.money_list_view);

        listMoneyItem=new ArrayList<>();
        listMoneyItem.add(new MoneyItem("EUR-USD","1.111","1.123"));
        listMoneyItem.add(new MoneyItem("GBP-USD","1.041","1.333"));
        listMoneyItem.add(new MoneyItem("TRY-USD", "1.101", "2.834"));
        listMoneyItem.add(new MoneyItem("CHF-USD", "1.134", "1.533"));


        MyListViewAdapter myListViewAdapter=new MyListViewAdapter(
                v.getContext().getApplicationContext(),R.layout.custom_row,listMoneyItem);

        listView.setAdapter(myListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Toast.makeText(getContext(), listMoneyItem.get(position).getMoneyType() + " Bastin", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }
    }






