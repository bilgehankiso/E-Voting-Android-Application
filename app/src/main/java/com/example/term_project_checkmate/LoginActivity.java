package com.example.term_project_checkmate;

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

public class LoginActivity extends AppCompatActivity {
    EditText mail;
    EditText password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        mail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClickLogin(View view) {
        String stringMail = mail.getText().toString();
        String stringPassword = password.getText().toString();
        if (stringMail.equals("admin") && stringPassword.equals("123456")) {
            Intent intent = new Intent(LoginActivity.this, AdminPage.class);
            startActivity(intent);
        } else {
            mAuth.signInWithEmailAndPassword(stringMail, stringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "You are successfully logged in", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginIntent);
                    } else {
                        Toast.makeText(LoginActivity.this, "!!!ERROR!!! Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void onClickForget(View view) {
        Intent forget = new Intent(LoginActivity.this, ForgetMyPassword.class);
        startActivity(forget);
    }

    public void onClickDontHaveAccount(View view) {
        Intent account = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(account);
    }
}
