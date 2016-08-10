package com.example.suat.financialasistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String SOAP_ACTION="http://tempuri.org/KullaniciSorgu";
    private static final String METHOD_NAME ="KullaniciSorgu";
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL="http://zaferbozkurtt.azurewebsites.net/WebService1.asmx?wsdl";
    int deneme=5;
    //duy sesimi suatt
    Button btLogin;
    Button btCancel;
    EditText etUser;
    TextView txClickRegister;
    EditText etPassword;
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        btLogin=(Button) findViewById(R.id.btLogin);
        btCancel=(Button) findViewById(R.id.btCancel);
        etUser=(EditText) findViewById(R.id.username);
        etPassword=(EditText) findViewById(R.id.password);
        txClickRegister=(TextView) findViewById(R.id.click);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isThatUser=etUser.getText().toString();
                String isThatPassword=etPassword.getText().toString();
                boolean loginSuccesful=false;
                //

                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("kullanici_adi",isThatUser);
                request.addProperty("sifre",isThatPassword);

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
                        NodeList name = element.getElementsByTagName("Id");
                        Element line = (Element) name.item(0);
                        System.out.println("For içi" +getCharacterDataFromElement(line));
                        loginSuccesful=true;
                    }
                }
                catch (Exception e1){
                    System.out.println("cekExceptionCalisti");
                }
                if(loginSuccesful){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    //Zafer modifiye etçek

                    int babaId=50;
                    intent.putExtra("send_Id",babaId);

                    //Bende arkama yaslanıp izlicem , hi ha ha ha
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AreYouSureHandler();
            }
        });

        txClickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


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
    public void onBackPressed() {
        AreYouSureHandler();
    }
    public void AreYouSureHandler(){
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(context);
        alertBuilder.setTitle("Quit App");
        alertBuilder.setMessage("Are you sure ?!")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                });
        AlertDialog alertDialog=alertBuilder.create();
        alertDialog.show();
    }

}
