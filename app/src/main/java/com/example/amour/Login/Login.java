package com.example.amour.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.example.amour.match.HomeScreen;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText username, password;
    Button loginButton;
    TextView textView;
    TextView error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        textView = findViewById(R.id.textView);
        loginButton = findViewById(R.id.login_btn);
        error_msg = findViewById(R.id.error_msg);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                error_msg.setText("");
                if (username.getText().length() == 0) {
                    username.setError("Please enter your username");
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                error_msg.setText("");
                if (password.getText().length() == 0) {
                    password.setError("Please enter your password");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username.getText().length() == 0 || password.getText().length() == 0) {
                    error_msg.setText("Please enter username and password");
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if (!currentUser.isEmailVerified()) {
                                    Intent verificationIntent = new Intent(Login.this, verification.class);
                                    startActivity(verificationIntent);
                                } else {
                                    Intent homeIntent = new Intent(Login.this, HomeScreen.class);
                                    startActivity(homeIntent);
                                }
                            } else {
                                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthWeakPasswordException) {
                                error_msg.setError("Invalid Password");
                            } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                error_msg.setError("No account with this email");
                            } else if (e instanceof FirebaseAuthUserCollisionException) {
                                error_msg.setError(e.getLocalizedMessage());
                            } else {
                                Log.e("Login", e.getMessage());
                            }
                        }
                    });
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Sign_up.class);
                startActivity(intent);
            }
        });
    }
}