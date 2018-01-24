package com.abeer_m.shop_project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by abeer_m on 1/23/2018.
 */

public class resetpassword extends Activity {
    EditText emailreset;
    private FirebaseAuth mauth;
    Button send;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        emailreset = (EditText) findViewById(R.id.emailrest);
        mauth = FirebaseAuth.getInstance();
        send=(Button)findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

    }
    public void send(){
        String emailr = emailreset.getText().toString().trim();
        if (TextUtils.isEmpty(emailr)) {
            Toast.makeText(resetpassword.this, "Enter Your E_mail to reset your password", Toast.LENGTH_LONG).show();
            return;
        }
        mauth.sendPasswordResetEmail(emailr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(resetpassword.this, "we have sent you instruction to reset your password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(resetpassword.this, "failed to send reset email", Toast.LENGTH_LONG).show();}
            }
        });
    }
}