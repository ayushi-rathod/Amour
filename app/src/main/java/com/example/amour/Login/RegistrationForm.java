package com.example.amour.Login;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amour.R;
import com.example.amour.match.HomeScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.amour.Util.User;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationForm extends AppCompatActivity implements View.OnClickListener {
    Button save_btn;
    FloatingActionButton add_profile_pic;
    private static final int SELECT_PICTURE = 100;
    CircleImageView profile_pic;
    Spinner gender_spinner, degree_spinner, pref_gender_spinner;
    RangeSeekBar age_range, height_range;
    EditText age_editText, height_editText, i_am_editText, i_like_editText, i_appreciate_editText;
    private StorageReference mStorageRef;
    public Uri imageuri;
    String TAG = "Registration Form!!";
    FirebaseDatabase db;
    private FirebaseAuth mAuth;

    String email, image_link;
    int pref_age_min_val, pref_age_max_val, pref_height_min_val, pref_height_max_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        add_profile_pic = findViewById(R.id.add_profile_picture);
        age_editText = findViewById(R.id.age_editText);
        height_editText = findViewById(R.id.height_editText);
        gender_spinner = findViewById(R.id.gender_spinner);
        degree_spinner = findViewById(R.id.degree_spinner);
        pref_gender_spinner = findViewById(R.id.pref_gender_spinner);
        age_range = findViewById(R.id.age_range);
        height_range = findViewById(R.id.height_range);
        i_am_editText = findViewById(R.id.i_am_editText);
        i_appreciate_editText = findViewById(R.id.i_appreciate_editText);
        i_like_editText = findViewById(R.id.i_like_editText);
        profile_pic = findViewById(R.id.profile_pic);
        save_btn = findViewById(R.id.button);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        email = mAuth.getCurrentUser().getEmail();

        age_editText.setOnClickListener(this);
        height_editText.setOnClickListener(this);
        i_am_editText.setOnClickListener(this);
        i_appreciate_editText.setOnClickListener(this);
        i_like_editText.setOnClickListener(this);

        age_range.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                pref_age_min_val = (Integer) minValue;
                pref_age_max_val = (Integer) maxValue;
            }
        });

        height_range.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                pref_height_min_val = (Integer) minValue;
                pref_height_max_val = (Integer) maxValue;
            }
        });


        add_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            // Write a message to the database

            //getcontext.getcurrentuser
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    saveData();
                    Intent intent = new Intent(RegistrationForm.this, HomeScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationForm.this, "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateInput() {
        if (age_editText.getText().toString().isEmpty()) {
            age_editText.setError("Please enter your age.");
            return false;
        }

        if (height_editText.getText().toString().isEmpty()) {
            height_editText.setError("Please enter your Height.");
            return false;
        }

        if (i_am_editText.getText().toString().isEmpty()) {
            i_am_editText.setError("Tell your date about you!");
            return false;
        }

        if (i_like_editText.getText().toString().isEmpty()) {
            i_like_editText.setError("Help us find you your best match!");
            return false;
        }

        if (i_appreciate_editText.getText().toString().isEmpty()) {
            i_appreciate_editText.setError("Let your date know how to impress you.");
            return false;
        }

        if (imageuri == null) {
            Toast.makeText(RegistrationForm.this, "Please select an image for profile picture.!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void saveData() {
        image_link = System.currentTimeMillis()+"."+getExtension(imageuri);
        User userDetails = new User(age_editText.getText().toString(), height_editText.getText().toString(),
                i_am_editText.getText().toString(), i_appreciate_editText.getText().toString(), i_like_editText.getText().toString(),
                pref_age_min_val, pref_age_max_val, pref_height_min_val, pref_height_max_val, pref_gender_spinner.getSelectedItem().toString(),
                image_link);
        DatabaseReference myRef = db.getReference("userDetails");
        myRef.child(mAuth.getCurrentUser().getUid()).setValue(userDetails);
        fileUploader();
    }
    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileUploader() {
        StorageReference ref = mStorageRef.child(image_link);

        ref.putFile(imageuri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(RegistrationForm.this, exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData() !=null ) {
            imageuri = data.getData();
            profile_pic.setImageURI(imageuri);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.age_editText:
                if (age_editText.getText().toString().isEmpty()) {
                    age_editText.setError("Please enter your age!");
                }
                break;
            case R.id.height_editText:
                if (height_editText.getText().toString().isEmpty()) {
                    height_editText.setError("Please enter your height!");
                }
                break;
            case R.id.i_am_editText:
                if (i_am_editText.getText().toString().isEmpty()) {
                    i_am_editText.setError("Please enter your height!");
                }
                break;
        }
    }
}
