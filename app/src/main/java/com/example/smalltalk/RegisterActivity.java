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

public class RegisterActivity extends AppCompatActivity {
    private Button CreateAccountbutton;
    private EditText UserEmail,UserPassword;
    private TextView Alreadyhaveaccountlink;
    private FirebaseAuth mAuth;
    private String message;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();

        InitializeField();
      Alreadyhaveaccountlink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              SendUserToLoginActivity();

          }
      });

      CreateAccountbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              CreateNewAccount();
          }

          private void CreateNewAccount() {
              String Email=UserEmail.getText().toString();
              String password=UserPassword.getText().toString();
              if(TextUtils.isEmpty(Email))
                  Toast.makeText(RegisterActivity.this,"Please enter email..",Toast.LENGTH_SHORT).show();

              if(TextUtils.isEmpty(password))
                  Toast.makeText(RegisterActivity.this,"Please enter Password..",Toast.LENGTH_SHORT).show();
              else{
                  loadingbar.setTitle("creating new account");
                  loadingbar.setMessage("Please wait while we are creating new account for you");
                  loadingbar.setCanceledOnTouchOutside(true);
                  loadingbar.show();
                  mAuth.createUserWithEmailAndPassword(Email,password)
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()) {
                                      SendUserToLoginActivity();
                                      Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                      loadingbar.dismiss();
                                  }
                                  else {
                                      message = task.getException().toString();
                                      Toast.makeText(RegisterActivity.this,"" +
                                              "Error: "+message,Toast.LENGTH_SHORT).show();
                                      loadingbar.dismiss();
                                  }


                              }
                          });

              }
          }
      });
    }

    private void InitializeField() {
        CreateAccountbutton =(Button) findViewById(R.id.register_button);
        UserEmail=(EditText) findViewById(R.id.register_email);
        UserPassword=(EditText) findViewById(R.id.register_password);
        Alreadyhaveaccountlink=(TextView)findViewById(R.id.already_have_an_account_link);
        loadingbar=new ProgressDialog(this);
    }
    private void SendUserToLoginActivity() {
        Intent loginIntent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }
}