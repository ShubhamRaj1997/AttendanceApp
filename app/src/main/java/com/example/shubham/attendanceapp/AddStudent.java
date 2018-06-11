package com.example.shubham.attendanceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextAttendance;
    private Button buttonAdd;
    private DocumentReference dbrefrence;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        editTextName=findViewById(R.id.editTextName);
        editTextAttendance=findViewById(R.id.editTextAttendance);
        buttonAdd=findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        dbrefrence = FirebaseFirestore.getInstance().collection("Attendance").document(user.getEmail());

    }

    private void addStudent(){
        String name=editTextName.getText().toString().trim(),attendance=editTextAttendance.getText().toString().trim();
        StudentInformation studentInformation = new StudentInformation(name,attendance);

        Map<String,String> info = new HashMap<>();
        info.put(studentInformation.name,studentInformation.attendance);
        dbrefrence.set(info, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"data successfully added ",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"data not added ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        finish();
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view == buttonAdd){
            addStudent();
        }
    }
}
