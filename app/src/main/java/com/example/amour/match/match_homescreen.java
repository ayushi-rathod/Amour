package com.example.amour.match;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.amour.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

public class match_homescreen extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainframe;
   private HomeFragment homeFragment;
    private  ProfileFragment profileFragment;
    private ChatFragment chatFragment;
    private static final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_homescreen);
        Log.d("mmm", "onCreate: ");
        mMainframe = (FrameLayout)findViewById(R.id.mainframe);
        mMainNav = (BottomNavigationView)findViewById(R.id.main_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,new HomeFragment()).commit();
      //  mMainNav.inflateMenu(R.menu.nav_items);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch(item.getItemId()){

                    case R.id.nav_profile:
                      selectedFragment = new ProfileFragment();
                        Log.i("MMM", "onNavigationItemSelected: nav_profile");
                        break;

                    case R.id.nav_amour:
                        selectedFragment = new HomeFragment();
                        Log.i("MMM", "onNavigationItemSelected: nav_amour");
                        break;

                    case R.id.nav_chat:
                        selectedFragment = new ChatFragment();
                        Log.i("MMM", "onNavigationItemSelected: nav_chat");
                        break;
                }
                if(selectedFragment!= null) {
                    Log.i("mmm", "onNavigationItemSelected:selected fragment ");
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe, selectedFragment).commit();
                    return true;
                }
                return false;

            }
        });
        mMainNav.setSelectedItemId(R.id.nav_amour);
    }

}
