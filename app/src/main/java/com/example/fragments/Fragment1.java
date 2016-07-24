package com.example.fragments;

import com.example.suat.financialasistant.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){

        View v=inflater.inflate(R.layout.fragment1_layout,container,false);
        return v;
    }



}
