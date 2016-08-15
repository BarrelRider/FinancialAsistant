package com.example.fragments;

import com.example.adapters.MyCommodityAdapter;
import com.example.adapters.MyStockExchangeListAdapter;
import com.example.models.CommodityItem;
import com.example.models.StockItem;
import com.example.suat.financialasistant.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class Fragment1 extends Fragment {

    ListView lstView;
    Thread t1;
    Thread t2;
    View v;
    Activity a;
    List<CommodityItem> listCommodidy;
    public static int id;
    public static String buy;
    public static String sell;
    public static String commodityLow;
    public static String commodityHigh;
    public static String tarih;


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,Bundle savedInstanceState){

        v=inflater.inflate(R.layout.fragment1_layout, container, false);
        a=getActivity();

        buy="bekle";
        sell="bekle";
        commodityLow="bekle";
        commodityHigh="bekle";
        tarih="bekle";
        lstView= (ListView) v.findViewById(R.id.lstCommodityExchange);

        listCommodidy=new ArrayList<>();
        listCommodidy.add(new CommodityItem(R.drawable.gold,"GramAltın"));
        listCommodidy.add(new CommodityItem(R.drawable.copper,"Bakır"));
        listCommodidy.add(new CommodityItem(R.drawable.silver,"Gümüş"));
        listCommodidy.add(new CommodityItem(R.drawable.gasoline,"Benzin"));
        listCommodidy.add(new CommodityItem(R.drawable.naturalgas,"DoğalGaz"));
        listCommodidy.add(new CommodityItem(R.drawable.wheat,"Buğday"));


        for (int i=0;i<listCommodidy.size();i++)
        {
            listCommodidy.get(i).setCommodityDate("wait");
            listCommodidy.get(i).setBuy(buy);
            listCommodidy.get(i).setSell(sell);
            listCommodidy.get(i).setLowCommodity(commodityLow);
            listCommodidy.get(i).setHighCommodity(commodityHigh);
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
                            for (int i =8;i<14;i++)
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

                                id=Integer.parseInt(getCharacterDataFromElement(line0))-9;
                                tarih=getCharacterDataFromElement(line1);
                                buy=getCharacterDataFromElement(line2);
                                sell=getCharacterDataFromElement(line3);
                                commodityLow=getCharacterDataFromElement(line4);
                                commodityHigh=getCharacterDataFromElement(line5);


                                listCommodidy.get(id).setCommodityDate(
                                        tarih.substring(8, 10)
                                                + ":"
                                                + tarih.substring(10, 12)
                                                + ":"
                                                + tarih.substring(12, 14));

                                listCommodidy.get(id).setBuy(buy);
                                listCommodidy.get(id).setSell(sell);
                                listCommodidy.get(id).setLowCommodity("L:"+commodityLow);
                                listCommodidy.get(id).setHighCommodity("H:" + commodityHigh);
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
                                MyCommodityAdapter adapter=new MyCommodityAdapter(v.getContext().getApplicationContext(),
                                        R.layout.commodity_exchange_list,listCommodidy);
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






        MyCommodityAdapter comAdapter=new MyCommodityAdapter(v.getContext().getApplicationContext(),R.layout.commodity_exchange_list,listCommodidy);
        lstView.setAdapter(comAdapter);

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
