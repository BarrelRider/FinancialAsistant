package com.example.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
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
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Fragment3 extends Fragment {

    //
    private static final String SOAP_ACTION="http://tempuri.org/GunlukVeriSorgu";
    private static final String METHOD_NAME ="GunlukVeriSorgu";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";
    //

    ListView listView;
    Thread t1;
    Thread t2;
    public static int babayaro=0;
    public static float anangil=0;
    List<MoneyItem> listMoneyItem;
    View v;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){

        v=inflater.inflate(R.layout.fragment3_layout,container,false);
        listView= (ListView) v.findViewById(R.id.money_list_view);
        final Activity a=getActivity();

        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //


        listMoneyItem=new ArrayList<>();
        listMoneyItem.add(new MoneyItem("Bekle","Bekle","Bekle"));

        MyListViewAdapter myListViewAdapter=new MyListViewAdapter(
                v.getContext().getApplicationContext(), R.layout.custom_row, listMoneyItem);
        listView.setAdapter(myListViewAdapter);
        listMoneyItem.get(0).setMoneyType("USD-EUR");
        listMoneyItem.get(0).setMoneyLow(""+babayaro);
        listMoneyItem.get(0).setMoneyHigh(""+anangil);

        t1=new Thread(){
            @Override
            public void run() {
                try{
                    while (true)
                    {
                        //
                        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
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
                                NodeList nameTarih = element.getElementsByTagName("Tarih");
                                Element line1 = (Element) nameTarih.item(0);
                                NodeList nameIslem = element.getElementsByTagName("Doviz_Alis");
                                Element line2 = (Element) nameIslem.item(0);
                                babayaro=Integer.parseInt( getCharacterDataFromElement(line1));
                                anangil=Float.parseFloat( getCharacterDataFromElement(line2));

                            }
                        }
                        catch (Exception e1){
                            System.out.println("cekExceptionCalisti");
                        }
                        //

                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listMoneyItem.get(0).setMoneyType("USD-EUR");
                                listMoneyItem.get(0).setMoneyLow(""+babayaro);
                                listMoneyItem.get(0).setMoneyHigh(""+anangil);
                                //babayaro= babayaro + 1;
                               // anangil= anangil+5;
                            }
                        });
                        Thread.sleep(1000);
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

                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyListViewAdapter myListViewAdapter=new MyListViewAdapter(
                                        v.getContext().getApplicationContext(), R.layout.custom_row, listMoneyItem);
                                listView.setAdapter(myListViewAdapter);
                            }
                        });
                        Thread.sleep(1000);
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

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        t1.interrupt();
        t2.interrupt();
    }
}






