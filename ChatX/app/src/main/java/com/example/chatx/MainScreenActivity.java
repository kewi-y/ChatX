package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.security.FileIntegrityManager;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreenActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String userId,name,bio;
    User user;
    FragmentManager fg;
    ChatFragment chatFragment;
    ProfileFragment profileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        bottomNavigationView = findViewById(R.id.BottomNav);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        name = intent.getStringExtra("name");
        bio = intent.getStringExtra("bio");
        user = new User(userId,name,bio);
        chatFragment = new ChatFragment(user);
        profileFragment = new ProfileFragment(user);
        fg = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomChatButton:
                        fg.beginTransaction().replace(R.id.placeholder,chatFragment).commit();
                        break;
                    case R.id.bottomProfileButton:
                        fg.beginTransaction().replace(R.id.placeholder,profileFragment).commit();
                        break;
                }
                return false;
            }
        });

    }
}