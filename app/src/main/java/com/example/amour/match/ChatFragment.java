package com.example.amour.match;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.amour.Login.Login;
import com.example.amour.R;
import com.google.firebase.auth.FirebaseAuth;

import com.example.amour.Login.Login;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
private FirebaseAuth mAuth;
private Button logout1;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth  = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_chat, container, false);


    
    }
}
