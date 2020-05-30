package com.example.amour.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText fullname, email, username, password;
    Button signUpButton;
    TextView error_msg;
    boolean isError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.editText_fullname);
        email = findViewById(R.id.editText_email);
        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        error_msg = findViewById(R.id.error_msg);
        signUpButton = findViewById(R.id.sign_up_btn);
        isError = false;

        // Client-Side Validations

        fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                error_msg.setText("");
                if (!b && fullname.getText().toString().isEmpty()) {
                    fullname.setError("Please enter your full name");
                }
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                error_msg.setText("");
                if (!email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    email.setError("Please enter university email");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Please enter a valid email");
                }
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                error_msg.setText("");
                String str = charSequence.toString();
                if(!str.isEmpty() && str.contains(" ")) {
                    username.setError("Space is not allowed");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (username.getText().toString().isEmpty()) {
                    isError = true;
                    username.setError("Please enter your username");
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                error_msg.setText("");
                if (!password.getText().toString().isEmpty() && password.length() < 6) {
                    password.setError("Password must be 6 characters");
                } else {
                    Drawable myIcon = getResources().getDrawable(R.drawable.tick_mark);
                    myIcon.setBounds(0, 0, 90, 90);
                    password.setError("Good", myIcon);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (password.getText().toString().isEmpty()) {
                    isError = true;
                    password.setError("Please enter your password");
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fullname.getText().toString().isEmpty() || username.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        error_msg.setText("Please provide a valid input!!");
                    return;
                } else {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                            .addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Sign_up.this, RegistrationForm.class);
                                        startActivity(intent);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (e instanceof FirebaseAuthWeakPasswordException) {
                                        error_msg.setError("Weak Password - minimum should be 6 char");
                                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        error_msg.setError("Invalid email address");
                                    } else if (e instanceof FirebaseAuthUserCollisionException) {
                                        error_msg.setError("user already exist");
                                    } else {
                                        Log.e("Sign_up", e.getLocalizedMessage());
                                    }
                                }
                            });
                }
            }
        });
    }
}