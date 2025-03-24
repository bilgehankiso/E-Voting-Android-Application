package com.example.e_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    protected ArrayList<String> answer;
    protected String question;
    protected int choiceNum;
    protected int choiceNum2;
    protected ListView listView;
    protected TextView quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<String> temp = new ArrayList<>();
        quest = (TextView) findViewById(R.id.questView);
        answer = new ArrayList<>();
        choiceNum = 0;
        choiceNum2 = 0;
        listView = (ListView) findViewById(R.id.ListVieww);
        mDatabase.child("election").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        temp.add(String.valueOf(snapshot.getValue()));
                    }
                    answer.add(temp.get(0));
                    answer.add(temp.get(1));
                    choiceNum = Integer.parseInt(temp.get(3));
                    choiceNum2 = Integer.parseInt(temp.get(2));
                    question = temp.get(4);
                    quest.setText(question);
                    ArrayAdapter<String> adappter = new ArrayAdapter<String>
                            (MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, answer);

                    listView.setAdapter(adappter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AlertDialog.Builder dialogWindow =
                                    new AlertDialog.Builder(MainActivity.this);
                            dialogWindow.setMessage("Choice is " + answer.get(position) + " are you sure to continue ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.out.println(which);
                                            if (position == 0) {
                                                choiceNum++;
                                                mDatabase.child("election").child("choiceNum").setValue(choiceNum);
                                                Toast.makeText(MainActivity.this, "You are successfully voted", Toast.LENGTH_SHORT).show();
                                                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                                                startActivity(mainIntent);
                                            } else if (position == 1) {
                                                choiceNum2++;
                                                mDatabase.child("election").child("choice2Num").setValue(choiceNum2);
                                                Toast.makeText(MainActivity.this, "You are successfully voted", Toast.LENGTH_SHORT).show();
                                                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                                                startActivity(mainIntent);
                                            } else {
                                                Toast.makeText(MainActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                                            }
                                            dialog.dismiss();
                                        }
                                    });
                            dialogWindow.create().show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}