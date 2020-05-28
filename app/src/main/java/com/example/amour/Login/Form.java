package com.example.amour.Login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amour.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;


public class Form extends AppCompatActivity {
    Button edit_pic_btn;
    Spinner gender_spinner,degree_spinner,pref_gender_spinner;
    RangeSeekBar age_range, height_range;
    EditText age_editText,height_editText, i_am_editText, i_like_editText, i_appreciate_editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        edit_pic_btn = findViewById(R.id.edit_pic_btn);
        age_editText = findViewById(R.id.age_editText);
        height_editText = findViewById(R.id.height_editText);
        gender_spinner = findViewById(R.id.gender_spinner);
        degree_spinner =  findViewById(R.id.degree_spinner);
        pref_gender_spinner =  findViewById(R.id.pref_gender_spinner);
        age_range =  findViewById(R.id.age_range);
        height_range =  findViewById(R.id.height_range);
        i_am_editText =  findViewById(R.id.i_am_editText);
        i_appreciate_editText = findViewById(R.id.i_appreciate_editText);
        i_like_editText =  findViewById(R.id.i_like_editText);


    }
}
