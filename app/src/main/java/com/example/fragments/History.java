package com.example.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class History extends Fragment {

    private static final String SOAP_ACTION="http://tempuri.org/VeriXmlCek";
    private static final String METHOD_NAME ="VeriXmlCek";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";

    ListView jobsDone;
    ArrayList<String> listHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

        View v=inflater.inflate(R.layout.fragment_history, container, false);

        listHistory =new ArrayList<>();
        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.debug=true;
        try{
            System.out.println("cekTryCalisti");
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
                NodeList nameIslem = element.getElementsByTagName("Islem");
                Element line2 = (Element) nameIslem.item(0);

                listHistory.add("Tarih : "+ getCharacterDataFromElement(line1)+ "\n" +"Islem: "+ getCharacterDataFromElement(line2) );
            }
        }
        catch (Exception e1){
            System.out.println("cekExceptionCalisti");
        }
        //
        jobsDone=(ListView) v.findViewById(R.id.lstHistory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,listHistory);

        jobsDone.setAdapter(adapter);

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
}
