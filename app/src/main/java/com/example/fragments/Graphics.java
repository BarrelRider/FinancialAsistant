package com.example.fragments;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.suat.financialasistant.R;


public class Graphics extends Fragment implements OnClickListener{

    View v;
    Fragment linefrag;
    Fragment barfrag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.graphics_listview,container,false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);



        Button btLine = (Button) v.findViewById(R.id.linebtn);
        Button btBar = (Button) v.findViewById(R.id.barbtn);

        linefrag=new LineFragment();
        barfrag=new BarFragment();

        btLine.setOnClickListener(this);
        btBar.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linebtn:getFragmentManager().beginTransaction().replace(R.id.main_content,linefrag).commit();break;
            case R.id.barbtn:getFragmentManager().beginTransaction().replace(R.id.main_content,barfrag).commit();break;

        }

    }
}



/*
    ListView graphlist;
    ArrayList<String> listGraphics;
    List<Fragment> listFragmentsForGraphs;


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
*/