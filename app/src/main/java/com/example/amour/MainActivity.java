package com.example.amour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.amour.Login.Login;
import com.example.amour.match.MatchHomescreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

//        Intent loginIntent = new Intent(this, RegistrationForm.class);
//        startActivity(loginIntent);
        if (currentUser == null) {
            Intent loginIntent = new Intent(this, Login.class);
            startActivity(loginIntent);
        } else {
            Intent homeIntent = new Intent(this, MatchHomescreen.class);
            startActivity(homeIntent);

            //setContentView(R.layout.activity_main);
            setContentView(R.layout.activity_match_homescreen);

        }
    }
}