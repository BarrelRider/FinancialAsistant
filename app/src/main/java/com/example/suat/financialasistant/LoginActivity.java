package com.example.suat.financialasistant;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;
    Button btCancel;
    EditText etUser;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin=(Button) findViewById(R.id.btLogin);
        btCancel=(Button) findViewById(R.id.btCancel);
        etUser=(EditText) findViewById(R.id.username);
        etPassword=(EditText) findViewById(R.id.password);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isThatUser=etUser.getText().toString();
                String isThatPassword=etPassword.getText().toString();
                System.out.println(isThatUser+ "  " + isThatPassword);

                if(isThatUser.equals("admin") && isThatPassword.equals("123")){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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
                finish();
            }
        });
    }
}
