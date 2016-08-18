package com.example.customwidgets;

import android.content.Context;

import com.example.fragments.BarGraph;
import com.example.fragments.LineGraph;
import com.example.suat.financialasistant.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import android.widget.TextView;


/**
 * Created by Suat on 18.08.2016.
 */
public class CustomMarkerView extends MarkerView {

    TextView tvMarker;
    TextView tvMarkerDate;
    String ekranaBas="";

    public CustomMarkerView(Context context,int layoutResource){
        super(context,layoutResource);
        tvMarker=(TextView) findViewById(R.id.custom_marker);
        tvMarkerDate=(TextView) findViewById(R.id.custom_date);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvMarker.setText("" + e.getVal());
        try {
            ekranaBas= LineGraph.listTarih.get(e.getXIndex()).toString();
        }
        catch (Exception e1){
            ekranaBas= BarGraph.listTarih.get(e.getXIndex()).toString();
            e1.printStackTrace();
        }
        tvMarkerDate.setText(ekranaBas);
    }



    @Override
    public int getXOffset(float xpos) {
        return -(getWidth()/2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
