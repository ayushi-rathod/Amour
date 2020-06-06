package com.example.amour.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.amour.R;
import com.example.amour.match.Cards;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PhotoAdapter extends ArrayAdapter<Cards> {
    Context mContext;
    private StorageReference mStorageRef;
    final private String TAG = "PhotoAdapter";
    Bitmap bitmap;

    public PhotoAdapter(@NonNull Context context, int resource, @NonNull List<Cards> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Cards card_item = getItem(position);
        final long ONE_MEGABYTE = 1024 * 1024;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageButton btnInfo = (ImageButton) convertView.findViewById(R.id.checkInfoBeforeMatched);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, selectedFragment).commit();
                //Intent intent = new Intent(mContext, DetailedProfile.class);
                //intent.putExtra("name", card_item.getName() + ", " + card_item.getAge());
                //intent.putExtra("photo", card_item.getProfileImageUrl());
                //mContext.startActivity(intent);l
            }
        });

        name.setText(card_item.getName() + ", " + card_item.getAge());
        StorageReference ref = mStorageRef.child(card_item.getProfileImageUrl());
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        });

        switch (card_item.getProfileImageUrl()) {
            case "default":
                Glide.with(getContext()).load(R.drawable.default_man).into(image);
                break;
            default:
                Glide.with(getContext()).load(bitmap).into(image);
                break;
        }

        return convertView;
    }
}
