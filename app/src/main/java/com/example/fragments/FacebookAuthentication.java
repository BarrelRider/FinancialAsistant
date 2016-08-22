package com.example.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.suat.financialasistant.LoginActivity;
import com.example.suat.financialasistant.MainActivity;
import com.example.suat.financialasistant.R;
import com.example.suat.financialasistant.RegisterActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookAuthentication extends Fragment {
    public boolean kayitYapilsinmi=false;
    public static String facebookName="";
    public static String facebookId="";
    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private EditText edUser;
    private EditText edPass;
    private ProfileTracker mProfileTracker;
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("VIVZ", "onSuccess");
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            mTextDetails.setText(constructWelcomeMessage(profile));



            /*
            Intent intent=new Intent(getActivity(),MainActivity.class);

            intent.putExtra("face_Id",facebookId);
            intent.putExtra("face_name", facebookName);
            startActivity(intent);

            */


        }


        @Override
        public void onCancel() {
            Log.d("Baba yaptı", "onCancel");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("Papabiceps", "onError " + e);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();
        System.out.println("onCreate");

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.facebook_login_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        System.out.println("onViewCreated");
        setupTextDetails(view);
        setupLoginButton(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        Profile profile = Profile.getCurrentProfile();
        mTextDetails.setText(constructWelcomeMessage(profile));
        setupTokenTracker();
        setupProfileTracker();
        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
        System.out.println("onResume");

    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
        System.out.println("onStop");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult");
        kayitYapilsinmi=true;
    }

    private void setupTextDetails(View view) {
        mTextDetails = (TextView) view.findViewById(R.id.papabiceps);
        edUser= (EditText) getActivity().findViewById(R.id.username);
        edPass= (EditText) getActivity().findViewById(R.id.password);

    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                if(currentAccessToken==null) mTextDetails.setText(null);
                Log.d("Suat token tracker", "" + currentAccessToken);
            }
        };
    }

    private void FillLoginTexts(Profile profile){
        String user=profile.getName();
        String pass=profile.getId();
        edUser.setText(user);
        edPass.setText(pass);
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(currentProfile==null) {
                    mTextDetails.setText(null);
                    edUser.setText(null);
                    edPass.setText(null);
                }
                Log.d("Suat prof tracker", "" + currentProfile);
                mTextDetails.setText(constructWelcomeMessage(currentProfile));
            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
        mButtonLogin.setFragment(this);
        mButtonLogin.setReadPermissions("user_friends");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Hoşgeldin " + profile.getName() + "\nLütfen \" Oturum aç\" butonuna tıklayın");
            FillLoginTexts(profile);
            facebookId=profile.getId();
            facebookName=profile.getName();
            if(kayitYapilsinmi)
            {
                RegisterActivity.Register(profile.getName(),profile.getId(),"F");
                kayitYapilsinmi=false;
            }
        }



        return stringBuffer.toString();

    }

}
