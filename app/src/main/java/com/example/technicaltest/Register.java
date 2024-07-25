package com.example.technicaltest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    EditText nameField, emailField, passwordField;
    Button registerBtn,loginHereBtn;

    FirebaseApp firebaseApp;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        emailField = findViewById(R.id.et_emailRegister);
        passwordField = findViewById(R.id.et_passwordRegister);
        nameField = findViewById(R.id.et_nameRegister);
        registerBtn = findViewById(R.id.btn_register);
        loginHereBtn = findViewById(R.id.btn_loginHere);

        firebaseApp = FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://technicaltestpascal-default-rtdb.asia-southeast1.firebasedatabase.app/");

        loginHereBtn.setOnClickListener(view -> {
            Intent registerIntent = new Intent(this, Login.class);
            startActivity(registerIntent);
            finish();
        });

        registerBtn.setOnClickListener(view -> {
            String email = emailField.getText().toString();
            String name = nameField.getText().toString();
            String password = passwordField.getText().toString();

            if(!email.contains("@") || !email.endsWith(".com")){
                Toast.makeText(this, "Email must contain '@' and ends with '.com'", Toast.LENGTH_SHORT).show();
            }else if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            }else if(password.length() < 6){
                Toast.makeText(this, "Password length must be more than 5 characters", Toast.LENGTH_SHORT).show();
            }else if(name.length() < 5){
                Toast.makeText(this, "Name must be more than 5 characters", Toast.LENGTH_SHORT).show();
            }else{

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(this, "Register Failed, email already exits", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Register Success, go Back to Login", Toast.LENGTH_SHORT).show();
                        userReference = firebaseDatabase.getReference().child("users").child(mAuth.getCurrentUser().getUid());
                        userReference.setValue(new User(name,email));
                    }
                });
            }

        });
    }
}