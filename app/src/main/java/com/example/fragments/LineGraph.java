package com.example.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suat.financialasistant.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
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

public class LineGraph extends Fragment {

    View v;
    private int moneyId;
    private static final String SOAP_ACTION="http://tempuri.org/DovizKurlariSorgu";
    private static final String METHOD_NAME ="DovizKurlariSorgu";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.line_layout,container,false);
        LineChart lineChart = (LineChart) v.findViewById(R.id.lineChart);
        initGraphic(lineChart,moneyId);

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

    public void initGraphic(LineChart ln , int id ){
        ArrayList<Entry> entries = new ArrayList<>();
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
                entries.add(new Entry(deger, i));
                months.add("");
            }
        }
        catch (Exception e1){
            System.out.println("cekExceptionCalisti\n");
            e1.printStackTrace();
        }

        LineDataSet dataset = new LineDataSet(entries,"Paranın 1 biriminin ,TRY Karşılığı");
        dataset.setDrawFilled(true);
        LineData data = new LineData(months, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        ln.setData(data);
        ln.animateY(2500);

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
