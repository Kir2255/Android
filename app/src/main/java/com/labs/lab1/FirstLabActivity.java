package com.labs.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.labs.R;

public class FirstLabActivity extends AppCompatActivity {

    public static final String FIO = "Курако Кирилл";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_lab);
    }

    public void displayFIO(View view){
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(FIO);
    }

    public void clearFIO(View view){
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(null);
    }
}
