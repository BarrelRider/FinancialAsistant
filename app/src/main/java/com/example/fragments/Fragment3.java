package com.example.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private static final String URL="http://fintechasistant.azurewebsites.net/MySpecialWebService.asmx?wsdl";
    //

    ListView listView;
    Thread t1;
    Thread t2;
    public static int id;
    public static String buy;
    public static String sell;
    public static String tarih;
    public static String gonder="";
    public static String dailyLow;
    public static String dailyHigh;

    List<MoneyItem> listMoneyItem;
    View v;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){

        v=inflater.inflate(R.layout.fragment3_layout,container,false);
        buy="bekle";
        sell="bekle";
        dailyLow="bekle";
        dailyHigh="bekle";
        id=0;
        listView= (ListView) v.findViewById(R.id.money_list_view);
        final Activity a=getActivity();

        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //


        listMoneyItem=new ArrayList<>();
        listMoneyItem.add(new MoneyItem());
        listMoneyItem.add(new MoneyItem());
        listMoneyItem.add(new MoneyItem());


        MyListViewAdapter myListViewAdapter=new MyListViewAdapter(
                v.getContext().getApplicationContext(), R.layout.custom_row, listMoneyItem);
        listView.setAdapter(myListViewAdapter);

        listMoneyItem.get(0).setMoneyType("USD-TRY");
        listMoneyItem.get(1).setMoneyType("EUR-TRY");
        listMoneyItem.get(2).setMoneyType("GBP-TRY");
        for (int i=0;i<listMoneyItem.size();i++)
        {
            listMoneyItem.get(i).setCurrentDate("wait");
            listMoneyItem.get(i).setMoneyLow(buy);
            listMoneyItem.get(i).setMoneyHigh(sell);
            listMoneyItem.get(i).setDailyLow(dailyLow);
            listMoneyItem.get(i).setDailyHigh(dailyHigh);
        }

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
                            gonder=response.toString();
                            //System.out.println(response.toString());
                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                            DocumentBuilder db = dbf.newDocumentBuilder();
                            InputSource is = new InputSource();
                            is.setCharacterStream(new StringReader(response.toString()));
                            Document doc = db.parse(is);

                            NodeList nodes = doc.getElementsByTagName("Table");

                            for (int i =0;i<3;i++)
                            {
                                Element element = (Element) nodes.item(i);
                                NodeList nameId = element.getElementsByTagName("Ad_ID");
                                Element line0 = (Element) nameId.item(0);
                                NodeList nameTarih = element.getElementsByTagName("Tarih");
                                Element line1 = (Element) nameTarih.item(0);
                                NodeList nameAlis = element.getElementsByTagName("Doviz_Alis");
                                Element line2 = (Element) nameAlis.item(0);
                                NodeList nameSatis = element.getElementsByTagName("Doviz_Satis");
                                Element line3 = (Element) nameSatis.item(0);
                                NodeList nameGunlukDusuk = element.getElementsByTagName("EnDusuk");
                                Element line4 = (Element) nameGunlukDusuk.item(0);
                                NodeList nameGunlukYuksek = element.getElementsByTagName("EnYuksek");
                                Element line5 = (Element) nameGunlukYuksek.item(0);

                                id=Integer.parseInt(getCharacterDataFromElement(line0))-1;
                                tarih=getCharacterDataFromElement(line1);
                                buy=getCharacterDataFromElement(line2);
                                sell=getCharacterDataFromElement(line3);
                                dailyLow=getCharacterDataFromElement(line4);
                                dailyHigh = getCharacterDataFromElement(line5);

                                listMoneyItem.get(id).setCurrentDate(
                                        tarih.substring(8, 10)
                                                + ":"
                                                + tarih.substring(10, 12)
                                                +":"
                                                +tarih.substring(12,14));

                                listMoneyItem.get(id).setMoneyLow(buy);
                                listMoneyItem.get(id).setMoneyHigh(sell);
                                listMoneyItem.get(id).setDailyLow("L: "+dailyLow);
                                listMoneyItem.get(id).setDailyHigh("H: "+dailyHigh);

                                    }
                        }
                        catch (Exception e1){
                            e1.printStackTrace();
                            System.out.println("cekExceptionCalisti");
                        }
                        Thread.sleep(500);
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
                        Thread.sleep(500);
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






