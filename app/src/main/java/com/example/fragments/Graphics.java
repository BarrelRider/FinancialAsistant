package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suat.financialasistant.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Graphics  extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_graphics,container,false);
        Toast.makeText(getContext(), "Daha iyi görebilmek için ekranı yan yatırın", Toast.LENGTH_SHORT).show();

        LineChart lineChart = (LineChart) v.findViewById(R.id.lineChart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f,0));
        entries.add(new Entry(8f,1));
        entries.add(new Entry(6f,2));
        entries.add(new Entry(12f,3));
        entries.add(new Entry(18f,4));
        entries.add(new Entry(9f,5));
        entries.add(new Entry(3f,6));
        entries.add(new Entry(2f,7));
        entries.add(new Entry(6f,8));
        entries.add(new Entry(25f,9));
        entries.add(new Entry(36f,10));
        entries.add(new Entry(30f,11));
        LineDataSet dataset = new LineDataSet(entries,"papa");

        ArrayList<String> months = new ArrayList<>();
        months.add("J");
        months.add("F");
        months.add("M");
        months.add("A");
        months.add("M");
        months.add("J");
        months.add("J");
        months.add("A");
        months.add("S");
        months.add("O");
        months.add("N");
        months.add("D");

        LineData data = new LineData(months, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        lineChart.setData(data);
        lineChart.animateY(2500);

        return v;
    }


}
