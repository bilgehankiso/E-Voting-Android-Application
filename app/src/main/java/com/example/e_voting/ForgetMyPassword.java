package com.example.e_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetMyPassword extends AppCompatActivity {

    EditText mail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_my_password);
        mail = (EditText) findViewById(R.id.editTextTextEmailAddress3);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickButton(View view) {
        String stringEmail = mail.getText().toString();
        mAuth.sendPasswordResetEmail(stringEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ForgetMyPassword.this, "E-Mail sent", Toast.LENGTH_SHORT).show();
                Intent intentt = new Intent(ForgetMyPassword.this, LoginActivity.class);
                startActivity(intentt);
            }
        });
    }
}