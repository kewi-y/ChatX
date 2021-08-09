package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInTaskActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser FbUser;
    FirebaseDatabase db;
    DatabaseReference refs;
    User user;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_task);
        auth = FirebaseAuth.getInstance();
        FbUser = auth.getCurrentUser();
        Intent intent = getIntent();
        email = intent.getStringExtra("Email");
        password = intent.getStringExtra("Password");
        db = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FbUser == null){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                    refs = db.getReference("users");
                    refs.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            for(DataSnapshot children : snapshot.getChildren()){
                                FbUser = task.getResult().getUser();
                                if(FbUser.getUid().equals(children.child("id").getValue())) {
                                    user = new User((String) children.child("id").getValue(), (String) children.child("name").getValue(),(String) children.child("bio").getValue());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });
                }
            });
        }
        else {
            refs = db.getReference("users");
            refs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot children : snapshot.getChildren()) {
                        if(FbUser.getUid().equals(children.child("id").getValue())) {
                            user = new User((String) children.child("id").getValue(), (String) children.child("name").getValue(),(String) children.child("bio").getValue());
                            Intent intent = new Intent(SignInTaskActivity.this,MainScreenActivity.class);
                            intent.putExtra("userId",user.getId());
                            intent.putExtra("name",user.getName());
                            intent.putExtra("bio",user.getBio());
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}