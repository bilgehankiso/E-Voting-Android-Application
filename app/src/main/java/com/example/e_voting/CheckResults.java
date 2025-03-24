package com.example.e_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class CheckResults extends AppCompatActivity {
    protected DatabaseReference mDatabase;
    protected int choiceNum;
    protected int choiceNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_results);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<PieEntry> chartt = new ArrayList<>();
        choiceNum = 0;
        choiceNum2 = 0;
        mDatabase.child("election").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        temp.add(String.valueOf(snapshot.getValue()));
                    }
                    choiceNum = Integer.parseInt(temp.get(3));
                    choiceNum2 = Integer.parseInt(temp.get(2));
                    chartt.add(new PieEntry(choiceNum, temp.get(0)));
                    chartt.add(new PieEntry(choiceNum2, temp.get(1)));
                    PieDataSet pieDataSet = new PieDataSet(chartt, "Votes");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("Votes");
                    pieChart.animate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckResults.this, "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}