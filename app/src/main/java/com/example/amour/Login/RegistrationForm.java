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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationForm extends AppCompatActivity {
    Button edit_pic_btn, save_btn;
    private static final int SELECT_PICTURE = 100;
    CircleImageView profile_pic;
    Spinner gender_spinner, degree_spinner, pref_gender_spinner;
    RangeSeekBar age_range, height_range;
    EditText age_editText, height_editText, i_am_editText, i_like_editText, i_appreciate_editText;
    private StorageReference mStorageRef;
    public Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrattion_form);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        edit_pic_btn = findViewById(R.id.edit_pic_btn);
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

        edit_pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileUploader();
            }
        });
    }
    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileUploader() {
        StorageReference ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imageuri));

        ref.putFile(imageuri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(RegistrationForm.this, "Image Uploaded Successfully!!", Toast.LENGTH_LONG).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    // ...
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
}
