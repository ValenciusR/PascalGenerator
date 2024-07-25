package com.example.technicaltest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    Button loginBtn, changePassbtn;
    FirebaseAuth mAuth;
    EditText emailField, oldpasswordField, newpassword;
    FirebaseApp firebaseApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);


        emailField = findViewById(R.id.et_emailForget);
        oldpasswordField = findViewById(R.id.et_oldpassword);
        newpassword = findViewById(R.id.et_newPassword);
        loginBtn = findViewById(R.id.btn_loginForgot);
        changePassbtn = findViewById(R.id.btn_changePassword);

        firebaseApp = FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            Intent registerIntent = new Intent(this, Login.class);
            startActivity(registerIntent);
            finish();
        });


        changePassbtn.setOnClickListener(v->{
            FirebaseUser user;
            user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(emailField.getText().toString(),oldpasswordField.getText().toString());
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this, "Fail", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ForgotPassword.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(ForgotPassword.this, "Auth Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}