package com.example.chatx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignUpActivity extends AppCompatActivity {
    EditText editName;
    EditText editPasswordReg;
    EditText editEmailReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editName = findViewById(R.id.editTextName);
        editEmailReg = findViewById(R.id.editTextEmailAddressReg);
        editPasswordReg = findViewById(R.id.editTextPasswordReg);
    }
    public void OnClickSignUp(View view){
        String name,email,password;
        name = editName.getText().toString();
        email = editEmailReg.getText().toString();
        password = editPasswordReg.getText().toString();
        if(!name.replace(" ","").equals("") && !email.replace(" ","").equals("") && !password.replace(" ","").equals("")){
            Intent intent = new Intent(this,SignUpTaskActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Fill the fields",Toast.LENGTH_SHORT).show();
        }

    }
}