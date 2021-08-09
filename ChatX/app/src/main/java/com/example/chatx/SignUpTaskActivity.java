package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpTaskActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser FbUser;
    FirebaseDatabase db;
    DatabaseReference refs;
    User user;
    String name,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_task);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FbUser = task.getResult().getUser();
                refs = db.getReference("users");
                user = new User(FbUser.getUid(),name,"");
                refs.push().setValue(user);
                Intent intent = new Intent(SignUpTaskActivity.this,MainScreenActivity.class);
                intent.putExtra("userId",user.getId());
                intent.putExtra("name",user.getName());
                intent.putExtra("bio",user.getBio());
                startActivity(intent);
            }
        });

    }
}