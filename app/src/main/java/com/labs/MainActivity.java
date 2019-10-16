package com.labs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.labs.lab1.FirstLabActivity;
import com.labs.lab2.SecondLabActivity;
import com.labs.lab3.ThirdLabActivity;
import com.labs.lab4.FouthLabActivity;
import com.labs.lab5.FifthLabActivity;
import com.labs.lab6.SixLabActivity;
import com.labs.lab7.SevensLabActivity;
import com.labs.lab8.EightsLabActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayLab1(View view){
        Intent intent = new Intent(this, FirstLabActivity.class);
        startActivity(intent);
    }

    public void displayLab2(View view){
        Intent intent = new Intent(this, SecondLabActivity.class);
        startActivity(intent);
    }

    public void displayLab3(View view){
        Intent intent = new Intent(this, ThirdLabActivity.class);
        startActivity(intent);
    }

    public void displayLab4(View view){
        Intent intent = new Intent(this, FouthLabActivity.class);
        startActivity(intent);
    }

    public void displayLab5(View view){
        Intent intent = new Intent(this, FifthLabActivity.class);
        startActivity(intent);
    }

    public void displayLab6(View view){
        Intent intent = new Intent(this, SixLabActivity.class);
        startActivity(intent);
    }

    public void displayLab7(View view){
        Intent intent = new Intent(this, SevensLabActivity.class);
        startActivity(intent);
    }

    public void displayLab8(View view){
        Intent intent = new Intent(this, EightsLabActivity.class);
        startActivity(intent);
    }
}
