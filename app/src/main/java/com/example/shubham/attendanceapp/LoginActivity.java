package com.example.shubham.attendanceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonSignin;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewSignup;
    private ProgressDialog dialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        buttonSignin=findViewById(R.id.buttonSignin);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        textViewSignup=findViewById(R.id.textViewSignup);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            // profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void userLogin(){
        String email=editTextEmail.getText().toString().trim(),password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this,"Please enter your E-mail address",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            // password is empty
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_LONG).show();
            return;
        }

        dialog.setMessage("Signing in..");
        dialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {dialog.cancel();
                            if(task.isSuccessful()){
                                // start profile activity!
                                finish();
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Please register first!",Toast.LENGTH_LONG).show();
                            }
                    }
                });




    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignin){
            userLogin();
        }
        if(view == textViewSignup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
