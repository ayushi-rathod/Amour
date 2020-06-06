package com.example.amour.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.amour.R;
import com.example.amour.Util.PhotoAdapter;
import com.example.amour.Util.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private DatabaseReference userDB, matchDB;
    private FirebaseAuth mAuth;
    public String currentUID, preferredGender;
    List<Cards> rowItems;
    private PhotoAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userDB = FirebaseDatabase.getInstance().getReference("userDetails");
        matchDB = FirebaseDatabase.getInstance().getReference("Matches");
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkUserSex();
        rowItems = new ArrayList<Cards>();
        arrayAdapter = new PhotoAdapter(getContext(), R.layout.item, rowItems);
        updateSwipeCard();
    }

    public void checkUserSex() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            this.currentUID = user.getUid();
            DatabaseReference usersDB = FirebaseDatabase.getInstance().getReference("userDetails");
            usersDB.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(currentUID)) {
                        preferredGender = dataSnapshot.getValue(User.class).getPref_gender().toLowerCase();
                        getPotentialMatch();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
    }


    public void getPotentialMatch() {
        DatabaseReference potentialMatch = FirebaseDatabase.getInstance().getReference("userDetails");
        potentialMatch.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && dataSnapshot.getValue(User.class).getPref_gender().toLowerCase().equals(preferredGender) &&
                        !dataSnapshot.child("connections").child("dislikeme").hasChild(currentUID) && !dataSnapshot.child("connections").child("likeme").hasChild(currentUID) && !dataSnapshot.getKey().equals(currentUID)) {
                    User curUser = dataSnapshot.getValue(User.class);

                    //initialize card view
                    //check profile image first
                    String profileImageUrl = "default";
                    if (dataSnapshot.child("image_link").getValue() != null) {
                        profileImageUrl = dataSnapshot.child("image_link").getValue().toString();
                    }
                    String age = curUser.getAge();
                    String username = curUser.getUsername();

                    Cards item = new Cards(dataSnapshot.getKey(), username, age, profileImageUrl);
                    rowItems.add(item);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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

    private void updateSwipeCard() {
        final SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) getActivity().findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                userDB.child(userId).child("connections").child("dislikeme").child(currentUID).setValue(true);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                userDB.child(userId).child("connections").child("likeme").child(currentUID).setValue(true);
                //check matches
                isConnectionMatch(userId);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });
    }

    private void isConnectionMatch(String userId) {
        final String matchedUID = userId;
        DatabaseReference currentUserConnectionsDb = userDB.child(currentUID).child("connections").child("likeme").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    matchDB.child(currentUID).child(matchedUID).child("Matches").setValue("saved");
                    matchDB.child(matchedUID).child(currentUID).child("Matches").setValue("saved");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
