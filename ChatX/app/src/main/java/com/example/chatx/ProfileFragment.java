package com.example.chatx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    User user;
    TextView nameView;
    TextView idView;
    TextView bioView;
    SimpleDraweeView avatarView;
    ImageButton profileEditButton, addImageButton;
    Button exitAccountButton;
    StorageReference stRefs;
    RoundingParams params;
    public ProfileFragment(User user) {
        this.user = user;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        params = RoundingParams.fromCornersRadius(1f);
        params.setRoundAsCircle(true);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameView = view.findViewById(R.id.nameView);
        idView = view.findViewById(R.id.idView);
        bioView = view.findViewById(R.id.bioView);
        profileEditButton = view.findViewById(R.id.profileEditButton);
        exitAccountButton = view.findViewById(R.id.buttonExit);
        avatarView = view.findViewById(R.id.avatarView);
        avatarView.setImageResource(R.drawable.outline_account_circle_black_48dp);
        addImageButton = view.findViewById(R.id.imageAddButton);
        stRefs = FirebaseStorage.getInstance().getReference();
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ImagePickTaskActivity.class);
                intent.putExtra("userId",user.getId());
                startActivity(intent);
            }
        });
        exitAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), StartActivity.class);
                startActivity(intent);
            }
        });
        profileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditActivity.class);
                intent.putExtra("name",user.getName());
                intent.putExtra("bio",user.getBio());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!user.getAvatarId().isEmpty()){
           stRefs.child(user.avatarId).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
               @Override
               public void onComplete(@NonNull Task<Uri> task) {
                   Uri avatarUri = task.getResult();
                   avatarView.getHierarchy().setRoundingParams(params);
                   avatarView.setImageURI(avatarUri);
               }
           });
        }
        nameView.setText(user.getName());
        idView.setText("ID: " + user.getId());
        bioView.setText(user.getBio());
    }

}