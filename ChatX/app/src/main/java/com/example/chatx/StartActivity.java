package com.example.chatx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    EditText editEmail;
    EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
    }
    public void onClickStartActivity(View view){
        Intent intent;
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        switch (view.getId()) {
            case R.id.SignIn:
                if(!email.equals("") && !password.equals("")) {
                    intent = new Intent(this, SignInTaskActivity.class);
                    intent.putExtra("Email", editEmail.getText().toString());
                    intent.putExtra("Password", editPassword.getText().toString());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this,"Fill the fields",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.SignUp:
                intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user != null){
            Intent intent = new Intent(this,SignInTaskActivity.class);
            startActivity(intent);
        }
    }
}