package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import com.example.suat.financialasistant.R;

public class Fragment2 extends Fragment {
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2_layout, container, false);

        TextView txt= (TextView) v.findViewById(R.id.tv_result);
        Button bt=(Button) v.findViewById(R.id.btnCall);





        return v;
    }

}


