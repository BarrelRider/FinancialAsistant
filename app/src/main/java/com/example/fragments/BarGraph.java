package com.example.fragments;

import android.graphics.Color;

import com.example.customwidgets.CustomMarkerView;
import com.example.models.UserInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suat.financialasistant.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BarGraph extends Fragment {

    View v;
    private int moneyId;
    private static final String SOAP_ACTION="http://tempuri.org/DovizKurlariSorgu";
    private static final String METHOD_NAME ="DovizKurlariSorgu";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.bar_layout,container,false);
        BarChart barChart = (BarChart) v.findViewById(R.id.barChart);
        initGraphic(barChart,moneyId);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return v;
    }

    public int getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(int moneyId) {
        this.moneyId = moneyId;
    }

    public void initGraphic(BarChart br , int id ){
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> months = new ArrayList<>();

        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
        String tarih="20160707";
        request.addProperty("adId",id);
        request.addProperty("tarih", tarih);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.debug=true;
        try{
            androidHttpTransport.call(SOAP_ACTION,envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(response.toString()));
            Document doc = db.parse(is);

            NodeList nodes = doc.getElementsByTagName("Table");

            for (int i =0;i<nodes.getLength();i++)
            {
                Element element = (Element) nodes.item(i);
                NodeList name = element.getElementsByTagName("Doviz_Alis");
                Element line = (Element) name.item(0);
                System.out.println("String tipi"+getCharacterDataFromElement(line));
                float deger= Float.parseFloat(getCharacterDataFromElement(line));
                System.out.println("ınt tipi"+deger);
                entries.add(new BarEntry(deger, i));
                months.add("");
            }
        }
        catch (Exception e1){
            System.out.println("cekExceptionCalisti\n");
            e1.printStackTrace();
        }

        BarDataSet dataset = new BarDataSet(entries,"p");
        BarData data = new BarData(months, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setValueTextSize(8f);
        dataset.setHighLightColor(Color.RED);
        dataset.setDrawValues(false);
        YAxis leftAxis= br.getAxisLeft();
        YAxis rightAxis=br.getAxisRight();
        leftAxis.setStartAtZero(false);
        rightAxis.setStartAtZero(false);

        CustomMarkerView customMarkerView=new CustomMarkerView(getContext(),R.layout.custom_marker_layout);

        br.setDrawMarkerViews(true);
        br.setMarkerView(customMarkerView);

        br.setData(data);
        br.animateY(2500);

    }



    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

}

