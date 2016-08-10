package com.example.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.adapters.MySettingListAdapter;
import com.example.suat.financialasistant.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Graphics extends Fragment{

    View v;
    ListView graphlist;
    ArrayList<String> listGraphics;
    List<Fragment> listFragmentsForGraphs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.graphics_listview,container,false);

        graphlist= (ListView) v.findViewById(R.id.lstGraph);
        listGraphics=new ArrayList<>();
        listGraphics.add("LineStyleGraphic");
        listGraphics.add("BarStyleGraphic");

        MySettingListAdapter adapter=new MySettingListAdapter(getContext(),R.layout.bold_listview,listGraphics);

        graphlist.setAdapter(adapter);

        listFragmentsForGraphs = new ArrayList<>();
        listFragmentsForGraphs.add(new LineFragment());
        listFragmentsForGraphs.add(new BarFragment());

        graphlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content,listFragmentsForGraphs.get(position)).commit();

                getActivity().setTitle(listGraphics.get(position));
            }
        });

        return v;
    }
}