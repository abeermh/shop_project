package com.abeer_m.shop_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import bolts.Task;

public class login extends AppCompatActivity {
    TextView signup;
    EditText email;
    EditText password;
    TextView forgetpass;
    CheckBox remmember;
    TextView skip;
    Button log;
    ProgressDialog pd;
    private CallbackManager callbackManager;
    LoginButton facebook;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signup);
        forgetpass = (TextView) findViewById(R.id.forgetpassword);
        remmember = (CheckBox) findViewById(R.id.checkBox);
        skip = (TextView) findViewById(R.id.skip);

        log=(Button)findViewById(R.id.btnlog) ;
        pd = new ProgressDialog(this);
        facebook = (LoginButton) findViewById(R.id.facebook);
        facebook.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest gr = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                    }
                });

                gr.executeAsync();
                startActivity(new Intent(login.this,shopping_list.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        mauth = FirebaseAuth.getInstance();
        if (mauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), shopping_list.class));
        }
        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });

    }

    public void signup() {
        Intent i = new Intent(this, registration.class);
        startActivity(i);
    }

    public void skip() {
        startActivity(new Intent(login.this, shopping_list.class));
    }

    public void resetpassword() {
        startActivity(new Intent(login.this, resetpassword.class));

    }


    private void login() {
        final String name = email.getText().toString().trim();
        final String passwrd = password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "empty Email", Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(passwrd)) {
            Toast.makeText(this, "empty password", Toast.LENGTH_LONG).show();
            return;
        }
        pd.setMessage("login ..");
        pd.show();
        mauth.signInWithEmailAndPassword(name, passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    pd.dismiss();
                    Toast.makeText(login.this, " logged in successfully ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), shopping_list.class));
                    return;
                }else{
                    pd.dismiss();
                    Toast.makeText(login.this, " failed to log in ", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mauth.getCurrentUser();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
