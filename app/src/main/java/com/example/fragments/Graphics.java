package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suat.financialasistant.R;


public class Graphics  extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_graphics,container,false);
        Toast.makeText(v.getContext(), "Graphics Fragmenti calısıyor", Toast.LENGTH_SHORT).show();

        return v;
    }
}
