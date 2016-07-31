package com.example.suat.financialasistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;
    Button btCancel;
    EditText etUser;
    EditText etPassword;
    final Context context=this;

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
                AreYouSureHandler();
                return;
            }
        });
    }
    @Override
    public void onBackPressed() {
        AreYouSureHandler();
        return;
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
                        finish();
                    }
                });
        AlertDialog alertDialog=alertBuilder.create();
        alertDialog.show();
    }

}
