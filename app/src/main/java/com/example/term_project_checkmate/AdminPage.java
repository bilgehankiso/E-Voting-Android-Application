package com.example.term_project_checkmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    protected EditText question;
    protected EditText choice;
    protected EditText choice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        question = (EditText) findViewById(R.id.editTextQuestion);
        choice = (EditText) findViewById(R.id.editTextChoice);
        choice2 = (EditText) findViewById(R.id.editTextChoice2);
    }

    public void onClickAddElection(View view) {
        String strQuestion = question.getText().toString();
        String strChoice = choice.getText().toString();
        String strChoice2 = choice2.getText().toString();

        mDatabase.child("election").child("question").setValue(strQuestion);
        mDatabase.child("election").child("choice").setValue(strChoice);
        mDatabase.child("election").child("choice2").setValue(strChoice2);
        mDatabase.child("election").child("choiceNum").setValue(0);
        mDatabase.child("election").child("choice2Num").setValue(0);

    }

    public void onClickCheckResults(View view) {
        Intent intent= new Intent(AdminPage.this,CheckResults.class);
        startActivity(intent);
    }
}