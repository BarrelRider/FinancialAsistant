package com.example.applications;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;


/**
 * Created by Suat on 16.08.2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
    }

    public void printHashKey(){

        try {
            PackageInfo packageInfo= getPackageManager().getPackageInfo(
                    "com.example.suat.financialasistant",PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures){
                MessageDigest md= MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
