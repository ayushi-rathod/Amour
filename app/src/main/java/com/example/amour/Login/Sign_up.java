package com.example.amour.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        // Client-Side Validations

        fullname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b && fullname.getText().length() == 0) {
                    fullname.setError("Please enter your full Name");
                }
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if(str.length() > 0 && str.contains(" ")) {
                    username.setError("Space is not allowed");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (username.getText().length() == 0) {
                    username.setError("Please enter your username");
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b && email.getText().length() == 0) {
                    email.setError("Please enter your email");
                }

                if (!b && !email.getText().toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    email.setError("Please enter university email");
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password.length() > 0 && password.length() < 6) {
                    password.setError("Password must be 6 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (password.getText().length() == 0) {
                    password.setError("Please enter your password");
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Sign_up.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                error_msg.setError("Weak Password - minimum should be 6 char");
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                error_msg.setError("Invalid email address");
                            } catch(FirebaseAuthUserCollisionException e) {
                                error_msg.setError("user already exist");
                            } catch(Exception e) {
                                Log.e("Sign_up", e.getMessage());
                            }
                        }
                    }
                });
            }
        });
    }
}