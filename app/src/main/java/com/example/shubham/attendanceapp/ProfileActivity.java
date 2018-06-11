package com.example.shubham.attendanceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView textViewEmail;
    private Button buttonLogout;
    private FirebaseAuth firebaseAuth;
    private DocumentReference dbrefrence;
    private Button buttonaddStudent;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        list=findViewById(R.id.list);
        textViewEmail=findViewById(R.id.textViewEmail);
        buttonLogout=findViewById(R.id.buttonLogout);
        firebaseAuth=FirebaseAuth.getInstance();
        buttonaddStudent=findViewById(R.id.buttonAddstudent);
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }




        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        textViewEmail.setText("Welcome " + firebaseUser.getEmail());

        buttonLogout.setOnClickListener(this);
        buttonaddStudent.setOnClickListener(this);

        dbrefrence = FirebaseFirestore.getInstance().collection("Attendance").document(firebaseUser.getEmail());

        dbrefrence.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Log.i("dcsnp",documentSnapshot.toString());
                    Map<String,Object> xp=new HashMap<>();
                    xp=documentSnapshot.getData();
                    MyAdapter myAdapter = new MyAdapter(xp);
                    list.setAdapter(myAdapter);
                }


            }
        });



    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(view == buttonaddStudent){
            // Add student activity
            finish();
            startActivity(new Intent(getApplicationContext(),AddStudent.class));
        }

    }
}
