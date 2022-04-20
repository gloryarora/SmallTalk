package com.example.smalltalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private Button loginbutton,Phoneloginbutton;
    private EditText UserEmail,UserPassword;
    private TextView NeedNewAccountlink,ForgetPasswordLink;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();

        InitializeField();
        NeedNewAccountlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToRegisterActivity();

            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();

            }
        });
    }

    private void AllowUserToLogin() {
        String Email=UserEmail.getText().toString();
        String password=UserPassword.getText().toString();
        if(TextUtils.isEmpty(Email))
            Toast.makeText(LoginActivity.this,"Please enter email..",Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(password))
            Toast.makeText(LoginActivity.this,"Please enter Password..",Toast.LENGTH_SHORT).show();
        else {
            loadingbar.setTitle("Sign in");
            loadingbar.setMessage("Please wait...");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        SendUserToMainActivity();
                        Toast.makeText(LoginActivity.this,"Logged in Successful",Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                }
                    else {
                         String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this,"" +
                                "Error: "+message,Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();

                    }
                }
            });

        }
        }


    private void InitializeField() {
       loginbutton =(Button) findViewById(R.id.login_button);
        Phoneloginbutton =(Button) findViewById(R.id.phone_login_button);
        UserEmail=(EditText) findViewById(R.id.login_email);
        UserPassword=(EditText) findViewById(R.id.login_password);
        NeedNewAccountlink=(TextView) findViewById(R.id.need_new_account_link);
        ForgetPasswordLink=(TextView)findViewById(R.id.forget_password_link);
        loadingbar=new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null)
        {
            SendUserToMainActivity();
        }
    }

    private void SendUserToMainActivity() {
        Intent loginIntent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginIntent);
    }
    private void SendUserToRegisterActivity() {
        Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
    }
}