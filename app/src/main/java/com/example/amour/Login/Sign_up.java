package com.example.amour.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        username = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText4);
        loginButton = findViewById(R.id.angry_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Sign_up.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Sign_up.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}