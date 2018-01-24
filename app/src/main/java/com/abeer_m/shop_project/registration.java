package com.abeer_m.shop_project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by abeer_m on 1/23/2018.
 */

public class registration extends Activity {
    EditText reg_name;
    EditText reg_password;
    Button register;
    ProgressDialog pd;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        reg_name=(EditText)findViewById(R.id.register_name);
        reg_password=(EditText)findViewById(R.id.register_password);
        reg_name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        pd=new ProgressDialog(this);
        register=(Button)findViewById(R.id.regigisterbtn) ;
        mauth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(); } });
    }
    public void register(){
        String name=reg_name.getText().toString().trim();
        String password=reg_password.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"empty Email",Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"empty password",Toast.LENGTH_LONG).show();
            return;
        }
        pd.setMessage("Registering user ..");
        pd.show();
        mauth.createUserWithEmailAndPassword(name,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pd.dismiss();
                        Toast.makeText(registration.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),shopping_list.class));
                    }else if(!task.isSuccessful()){
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(registration.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;

                    }
            }
        });

    }

}