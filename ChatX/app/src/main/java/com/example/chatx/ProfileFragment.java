package com.example.chatx;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    User user;
    TextView nameView;
    TextView idView;
    TextView bioView;
    ImageButton profileEditButton;
    Button exitAccountButton;
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameView = view.findViewById(R.id.nameView);
        idView = view.findViewById(R.id.idView);
        bioView = view.findViewById(R.id.bioView);
        profileEditButton = view.findViewById(R.id.profileEditButton);
        exitAccountButton = view.findViewById(R.id.buttonExit);
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
        nameView.setText(user.getName());
        idView.setText("ID: " + user.getId());
        bioView.setText(user.getBio());
    }
}