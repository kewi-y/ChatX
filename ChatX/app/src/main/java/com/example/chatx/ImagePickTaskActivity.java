package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class ImagePickTaskActivity extends AppCompatActivity {
    SimpleDraweeView draweeView;
    Uri uri;
    String userId;
    FirebaseStorage fbStorage;
    StorageReference stRef;
    FirebaseDatabase db;
    DatabaseReference rootRef;
    DatabaseReference personRef;
    RoundingParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pick_task);
        Fresco.initialize(this);
        userId = getIntent().getStringExtra("userId");
        draweeView = findViewById(R.id.draweeView);
        fbStorage = FirebaseStorage.getInstance();
        params = RoundingParams.fromCornersRadius(1f);
        params.setRoundAsCircle(true);
        draweeView.getHierarchy().setRoundingParams(params);
        ImagePicker.Companion.with(this).galleryOnly().maxResultSize(350,350).crop().start();
        db = FirebaseDatabase.getInstance();
        rootRef = db.getReference("users");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren()){
                    if(userId.equals(children.child("id").getValue())){
                        personRef = children.getRef();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        draweeView.setImageURI(uri);
    }
    public void acceptAvatar(View view){
        view.setClickable(false);
        Date date = new Date();
        String id = String.valueOf(date.getTime());
        stRef = FirebaseStorage.getInstance().getReference();
        personRef.child("avatarId").setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                stRef.child(id).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Intent intent = new Intent(ImagePickTaskActivity.this,SignInTaskActivity.class);
                    }
                });
            }
        });
    }
}