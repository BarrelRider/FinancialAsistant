package com.example.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.adapters.MySettingListAdapter;
import com.example.models.UserInfo;
import com.example.suat.financialasistant.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class Settings  extends Fragment {

    ListView lst;
    ArrayList<String> listSettings;

    private static final String SOAP_ACTION3="http://tempuri.org/VeriTemizle";
    private static final String METHOD_NAME3 ="VeriTemizle";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);


        View v=inflater.inflate(R.layout.fragment_settings, container, false);
        lst=(ListView) v.findViewById(R.id.SettingList);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listSettings=new ArrayList<>();
        listSettings.add("Clear History");
        listSettings.add("About Us");
        listSettings.add("Contact Us");

        MySettingListAdapter adapter=new MySettingListAdapter(getContext(),R.layout.bold_listview,listSettings);

        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                if (position == 0) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME3);
                            request.addProperty("id", UserInfo.id);
                            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                            envelope.dotNet = true;
                            envelope.setOutputSoapObject(request);
                            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                            androidHttpTransport.debug = true;

                            try {
                                System.out.println("silTryCalisti");
                                androidHttpTransport.call(SOAP_ACTION3, envelope);
                            } catch (Exception e1) {
                                System.out.println("silExceptionCalisti");
                            }

                        }
                    }).start();
                    Toast.makeText(getContext(), "Cleared", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Not cleared", Toast.LENGTH_SHORT).show();
                }
                //

            }
        });


        return v;
    }
}
