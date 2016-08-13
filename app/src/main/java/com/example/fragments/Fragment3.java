package com.example.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    Thread t1;
    Thread t2;
    public static int babayaro;
    public static int anangil;
    List<MoneyItem> listMoneyItem;
    View v;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){

        v=inflater.inflate(R.layout.fragment3_layout,container,false);
        listView= (ListView) v.findViewById(R.id.money_list_view);
        final Activity a=getActivity();



        listMoneyItem=new ArrayList<>();
        listMoneyItem.add(new MoneyItem("Bekle","Bekle","Bekle"));

        MyListViewAdapter myListViewAdapter=new MyListViewAdapter(
                v.getContext().getApplicationContext(), R.layout.custom_row, listMoneyItem);
        listView.setAdapter(myListViewAdapter);

        t1=new Thread(){
            @Override
            public void run() {
                try{
                    while (true)
                    {
                        Thread.sleep(2000);
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listMoneyItem.get(0).setMoneyType("USD-EUR");
                                listMoneyItem.get(0).setMoneyLow(""+babayaro);
                                listMoneyItem.get(0).setMoneyHigh(""+anangil);
                                babayaro= babayaro + 1;
                                anangil= anangil+5;
                            }
                        });
                    }

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
         t2=new Thread(){
            @Override
            public void run() {
                try{
                    while (true)
                    {
                        Thread.sleep(2000);
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyListViewAdapter myListViewAdapter=new MyListViewAdapter(
                                        v.getContext().getApplicationContext(), R.layout.custom_row, listMoneyItem);
                                listView.setAdapter(myListViewAdapter);
                            }
                        });
                    }

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };


        t1.start();
        t2.start();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), listMoneyItem.get(position).getMoneyType() + " Bastin", Toast.LENGTH_SHORT).show();
            }
        });
            return v;
        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        t1.interrupt();
        t2.interrupt();
    }
}






