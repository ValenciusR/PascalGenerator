package com.example.technicaltest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button loginBtn,registerHereBtn, forgetBtn;
    FirebaseAuth mAuth;
    EditText emailField, passwordField;
    FirebaseApp firebaseApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.et_email);
        passwordField = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        registerHereBtn = findViewById(R.id.btn_registerHere);
        forgetBtn = findViewById(R.id.btn_forgotpass);

        firebaseApp = FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        registerHereBtn.setOnClickListener(view -> {
            Intent registerIntent = new Intent(this, Register.class);
            startActivity(registerIntent);
            finish();
        });
        forgetBtn.setOnClickListener(view -> {
            Intent forgetIntent = new Intent(this, ForgotPassword.class);
            startActivity(forgetIntent);
            finish();
        });

        loginBtn.setOnClickListener(view -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, task -> {
                if(!task.isSuccessful()){
                    Toast.makeText(this, "Login Failed, Email doesn't exist", Toast.LENGTH_SHORT).show();
                    Log.e("Signup Error", "onCancelled", task.getException());
                }else{
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }
            });

        });

    }
}