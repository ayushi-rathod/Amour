package com.example.amour.match;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.amour.R;
import com.example.amour.Util.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.florescu.android.rangeseekbar.RangeSeekBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private FirebaseDatabase db;
    private FirebaseAuth mAuth;
    final String TAG = "ProfileFragment";
    EditText age_editText, height_editText, i_am_editText, i_like_editText, i_appreciate_editText;
    Spinner gender_spinner, degree_spinner, pref_gender_spinner;
    RangeSeekBar age_range, height_range;
    String userId;
    ImageView image;
    private StorageReference mStorageRef;
    Bitmap bitmap;
    final long ONE_MEGABYTE = 1024 * 1024;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        userId = mAuth.getCurrentUser().getUid();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        age_editText = view.findViewById(R.id.age_editText);
        height_editText = view.findViewById(R.id.height_editText);
        gender_spinner = view.findViewById(R.id.gender_spinner);
        //degree_spinner = findViewById(R.id.degree_spinner);
        pref_gender_spinner = view.findViewById(R.id.pref_gender_spinner);
        age_range = view.findViewById(R.id.age_range);
        height_range = view.findViewById(R.id.height_range);
        i_am_editText = view.findViewById(R.id.i_am_editText);
        i_appreciate_editText = view.findViewById(R.id.i_appreciate_editText);
        i_like_editText = view.findViewById(R.id.i_like_editText);
        image = (ImageView) view.findViewById(R.id.profile_pic);

        DatabaseReference myRef = db.getReference();
        findUser();
    }

    private void findUser() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference("userDetails");
        usersDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(userId)) {
                    getUserPhotoAndName(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUserPhotoAndName(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getUserPhotoAndName(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        //name.setText(user.getUsername());
        age_editText.setText(user.getAge());
        height_editText.setText(user.getHeight());
        i_am_editText.setText(user.getI_am());
        pref_gender_spinner.setSelection(getIndex(pref_gender_spinner, user.getPref_gender()));
        gender_spinner.setSelection(getIndex(gender_spinner, user.getSex()));
        i_appreciate_editText.setText(user.getI_appreciate());
        i_like_editText.setText(user.getI_like());
        StorageReference ref = mStorageRef.child(user.getImage_link());
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        });
        switch (user.getImage_link()) {
            case "default":
                Glide.with(getContext()).load(R.drawable.default_man).into(image);
                break;
            default:
                Glide.with(getContext()).load(bitmap).into(image);
                break;
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }
}
