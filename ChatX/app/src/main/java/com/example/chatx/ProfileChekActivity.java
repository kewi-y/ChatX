package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileChekActivity extends AppCompatActivity {
    SimpleDraweeView avatarChekView;
    RoundingParams params;
    String userId;
    TextView bioChekView,idChekView,nameChekView;
    DatabaseReference rootRef;
    DatabaseReference personRef;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_profile_chek);
        avatarChekView = findViewById(R.id.avatarChekView);
        bioChekView = findViewById(R.id.bioChekView);
        idChekView = findViewById(R.id.idChekView);
        nameChekView = findViewById(R.id.nameChekView);
        storageReference = FirebaseStorage.getInstance().getReference();
        params = RoundingParams.fromCornersRadius(1f);
        params.setRoundAsCircle(true);
        avatarChekView.setImageResource(R.drawable.outline_account_circle_black_48dp);
        userId = getIntent().getStringExtra("userId");
        rootRef = FirebaseDatabase.getInstance().getReference("users");

    }

    @Override
    protected void onStart() {
        super.onStart();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren()){
                    if(userId.equals(children.child("id").getValue())){
                        personRef = children.getRef();
                        String avatarId,name,bio;
                        personRef.addValueEventListener(new ValueEventListener() {
                            String avatarId,name,bio;
                            Uri avatarUri;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                name = (String) snapshot.child("name").getValue();
                                bio = (String) snapshot.child("bio").getValue();
                                avatarId = (String) snapshot.child("avatarId").getValue();
                                bioChekView.setText(bio);
                                nameChekView.setText(name);
                                if(!avatarId.isEmpty()){
                                    avatarChekView.getHierarchy().setRoundingParams(params);
                                    storageReference.child(avatarId).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            avatarUri = task.getResult();
                                            avatarChekView.setImageURI(avatarUri);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}