package com.example.suat.financialasistant;

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
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";

    EditText etUserReg;
    EditText etPassReg;
    Button btRegConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                String strSuccessRegister="2";

                if(strUserReg.equals("") || strPassReg.equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "Kullanıcı adı veya Şifre boş olmamalı", Toast.LENGTH_SHORT).show();
                }

                //

                else {
                    SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                    request.addProperty("kullanici_adi",strUserReg);
                    request.addProperty("sifre",strPassReg);

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
                    Toast.makeText(RegisterActivity.this, "Aynı isimde kullanıcı mevcut", Toast.LENGTH_SHORT).show();
                }


                else if(strSuccessRegister.equals("1")){
                    new CountDownTimer(500,50){
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(RegisterActivity.this, "Başarılı", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }.start();
                }

            }
        });






    }
}
