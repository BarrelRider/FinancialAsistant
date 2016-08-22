package com.example.suat.financialasistant;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class RegisterActivity extends AppCompatActivity {

    private static final String SOAP_ACTION="http://tempuri.org/KullaniciKayit";
    private static final String METHOD_NAME ="KullaniciKayit";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://fintechasistant.azurewebsites.net/MySpecialWebService.asmx?wsdl";

    //public String kullanicikayitturu="";
    EditText etUserReg;
    EditText etPassReg;
    Button btRegConfirm;


    static Activity thisActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_register);
        setTitle("Register");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etUserReg=(EditText) findViewById(R.id.regUser);
        etPassReg=(EditText) findViewById(R.id.regPass);
        btRegConfirm=(Button) findViewById(R.id.btRegConfirm);

        btRegConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserReg=etUserReg.getText().toString();
                String strPassReg=etPassReg.getText().toString();

                RegisterActivity.Register(strUserReg,strPassReg,"N");
            }
        });

    }

    public static void Register(String strUserReg, String strPassReg,String kullanicikayitturu)
    {
        String strSuccessRegister="2";

        if(strUserReg.equals("") || strPassReg.equals(""))
        {
            try {
                Toast.makeText(thisActivity, "Kullanıcı adı veya Şifre boş olmamalı", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {

            }
        }

        else {
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("kullanici_adi",strUserReg);
            request.addProperty("sifre",strPassReg);
            request.addProperty("kayitTuru",kullanicikayitturu);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug=true;
            try{
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                strSuccessRegister=response.toString();
                System.out.println("Babanın şarabi : "+strSuccessRegister);
            }
            catch (Exception e1){
                System.out.println("cekExceptionCalisti");
            }
        }

        if(strSuccessRegister.equals("0")){
            try {
                Toast.makeText(thisActivity, "Aynı isimde kullanıcı mevcut", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {

            }

        }


        else if(strSuccessRegister.equals("1")){
            new CountDownTimer(500,50){
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    try {
                        Toast.makeText(thisActivity, "Başarılı", Toast.LENGTH_SHORT).show();
                        thisActivity.finish();
                    }
                    catch (Exception e)
                    {

                    }
                }
            }.start();
        }

    }



}
