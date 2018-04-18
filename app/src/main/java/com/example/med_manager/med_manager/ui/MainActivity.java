package com.example.med_manager.med_manager.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.med_manager.R;
import com.example.med_manager.med_manager.utils.InternetUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    public static final String GOOGLE_ACCOUNT_NAME ="google_name";
    public static final String GOOGLE_ACCOUNT_EMAIL ="google_email";
    public static final String GOOGLE_ACCOUNT_IMGURL ="google_img_url";
    ProgressDialog pDialog;

    SignInButton sigin_button;
       GoogleApiClient googleApiClient;
    private static final int REQUEST_CODE = 200;
    Intent listActivityIntent;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get reference to UI components
        sigin_button = (com.google.android.gms.common.SignInButton) findViewById(R.id.sign_in);
        // Register on click lister to both logut and sign button
        sigin_button.setOnClickListener(this);

        pDialog = new ProgressDialog(this);

        // Hide the user profile section

         listActivityIntent = new Intent(this, MedListActivity.class);

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
                if (InternetUtils.isInternetConnected(this)) {
                    signIn();
                } else {
                    Toast.makeText(this, getString(R.string.internet_problem),Toast.LENGTH_LONG).show();
                }

                break;

        }

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                login(false);
            }
        });
    }

    private void signIn() {
        displayProgressDialog();
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
            hideProgressDialog();
            processSignInResult(result);
        }
    }

    // this method process the result return by google sign in intent
    private void processSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount signInAccount = result.getSignInAccount();
            String name = signInAccount.getDisplayName();
            String email = signInAccount.getEmail();
            String imgURL = signInAccount.getPhotoUrl().toString();

            listActivityIntent.putExtra(GOOGLE_ACCOUNT_NAME, name);
            listActivityIntent.putExtra(GOOGLE_ACCOUNT_EMAIL,email );
            listActivityIntent.putExtra(GOOGLE_ACCOUNT_IMGURL,imgURL );
            login(true);
        } else {
            login(false);
        }
    }

    private void login(boolean isLogin) {
        if (isLogin) {
           // profile_pane.setVisibility(View.VISIBLE);
           // sigin_button.setVisibility(View.GONE);
            startActivity(listActivityIntent);

        } else {

           // profile_pane.setVisibility(View.GONE);
            //sigin_button.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Cannot start the list activity", Toast.LENGTH_LONG).show();
        }
    }

    // this fuction display progress dialog while signing in progress
    private void displayProgressDialog() {
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void hideProgressDialog() {

        pDialog.hide();

    }


}
