package com.example.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adapters.MyStockExchangeListAdapter;
import com.example.models.StockItem;
import com.example.suat.financialasistant.R;

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

public class Fragment2 extends Fragment {


    ListView lstView;
    Thread t1;
    Thread t2;
    View v;
    List<StockItem> listStockItem;
    public static int id;
    public static String buy;
    public static String sell;
    public static String stockLow;
    public static String stockHigh;
    public static String tarih="0";

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final Activity a=getActivity();
        v = inflater.inflate(R.layout.fragment2_layout, container, false);

        buy="bekle";
        sell="bekle";
        stockLow="bekle";
        stockHigh="bekle";
        id=0;
        lstView= (ListView) v.findViewById(R.id.lstStockExchange);

        listStockItem=new ArrayList<>();
        listStockItem.add(new StockItem(R.drawable.facebook,"Facebook"));
        listStockItem.add(new StockItem(R.drawable.twitter,"Twitter"));
        listStockItem.add(new StockItem(R.drawable.apple,"Apple"));
        listStockItem.add(new StockItem(R.drawable.amazon,"Amazon"));
        listStockItem.add(new StockItem(R.drawable.google, "Google"));

        for (int i=0;i<listStockItem.size();i++)
        {
            listStockItem.get(i).setStockDate("wait");
            listStockItem.get(i).setBuy(buy);
            listStockItem.get(i).setSell(sell);
        }


        t1=new Thread(){
            @Override
            public void run() {
                try {
                    while (true){
                        try {
                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                            DocumentBuilder db = dbf.newDocumentBuilder();
                            InputSource is = new InputSource();
                            is.setCharacterStream(new StringReader(Fragment3.gonder));
                            Document doc = db.parse(is);

                            NodeList nodes = doc.getElementsByTagName("Table");
                            for (int i =3;i<8;i++)
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
                                NodeList nameGunlukDusukHisse = element.getElementsByTagName("EnDusuk");
                                Element line4 = (Element) nameGunlukDusukHisse.item(0);
                                NodeList nameGunlukYuksekHisse = element.getElementsByTagName("EnYuksek");
                                Element line5 = (Element) nameGunlukYuksekHisse.item(0);

                                id=Integer.parseInt(getCharacterDataFromElement(line0))-4;
                                tarih=getCharacterDataFromElement(line1);
                                buy=getCharacterDataFromElement(line2);
                                sell=getCharacterDataFromElement(line3);
                                stockLow=getCharacterDataFromElement(line4);
                                stockHigh=getCharacterDataFromElement(line5);


                                listStockItem.get(id).setStockDate(
                                        tarih.substring(8,10)
                                                +":"
                                                +tarih.substring(10,12)
                                                +":"
                                                +tarih.substring(12,14));

                                listStockItem.get(id).setBuy(buy);
                                listStockItem.get(id).setSell(sell);
                                listStockItem.get(id).setLowStock("L:" + stockLow);
                                listStockItem.get(id).setHighStock("H:" + stockHigh);
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
                                MyStockExchangeListAdapter adapter=new MyStockExchangeListAdapter(v.getContext().getApplicationContext(),
                                        R.layout.stock_exchange_list,listStockItem);

                                lstView.setAdapter(adapter);
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


