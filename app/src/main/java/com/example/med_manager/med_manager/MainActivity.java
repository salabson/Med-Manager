package com.example.med_manager.med_manager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.med_manager.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    LinearLayout profile_pane;
    ImageView profile_pic;
    Button logout_button;
    Button sigin_button;
    TextView txtName, txtEmail;
    GoogleApiClient googleApiClient;
    private static final int REQUEST_CODE = 200;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get reference to UI components
        profile_pane = (LinearLayout)findViewById(R.id.profile_pane);
        profile_pic = (ImageView)findViewById(R.id.profile_pic);
        logout_button = (Button) findViewById(R.id.logout);
        sigin_button = (Button) findViewById(R.id.sign_in);
        txtName = (TextView) findViewById(R.id.name);
        txtEmail= (TextView) findViewById(R.id.email);

        // Register on click lister to both logut and sign button
        logout_button.setOnClickListener(this);
        sigin_button.setOnClickListener(this);

        // Hide the user profile section
        profile_pane.setVisibility(View.GONE);

        // configure google client parameters
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient  = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                signIn();
                break;
            case R.id.logout:
                signOut();
                break;
        }

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUi(false);
            }
        });
    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            processSignInResult(result);
        }
    }

    // this method process the result return by google sign in intent
    private void processSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount signInAccount = result.getSignInAccount();
            updateUi(true);
            String name = signInAccount.getDisplayName();
            String email = signInAccount.getEmail();
            String imgURL = signInAccount.getPhotoUrl().toString();
            Glide.with(this).load(imgURL).into(profile_pic);
            txtName.setText(email.toString());
            txtEmail.setText(name.toString());



        } else {
            updateUi(false);
        }
    }

    private void updateUi(boolean isLogin) {
        if (isLogin) {
            profile_pane.setVisibility(View.VISIBLE);
            sigin_button.setVisibility(View.GONE);
        } else {
            profile_pane.setVisibility(View.GONE);
            sigin_button.setVisibility(View.VISIBLE);
        }
    }
}
