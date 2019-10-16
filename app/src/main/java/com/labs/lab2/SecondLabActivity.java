package com.labs.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.labs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

public class SecondLabActivity extends AppCompatActivity {

    private AlertDialog currentDialog;
    private List<House> houses = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_lab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_item:
                final View view = this.getLayoutInflater().inflate(R.layout.add_menu, null);
                currentDialog = new AlertDialog.Builder(this)
                        .setTitle("Добавить дом")
                        .setView(view)
                        .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                addHouse(view);
                            }
                        })
                        .create();
                currentDialog.show();
                break;
            case R.id.displayHousees:
                LinearLayout linearLayout = findViewById(R.id.display);
                linearLayout.removeAllViews();

                textViews.clear();
                display(linearLayout, houses);

                break;
            case R.id.rooms:
                final View view1 = this.getLayoutInflater().inflate(R.layout.display_by_rooms, null);
                currentDialog = new AlertDialog.Builder(this)
                        .setTitle("По комнатам")
                        .setView(view1)
                        .setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                displayByRooms(view1);
                            }
                        })
                        .create();
                currentDialog.show();
                break;
            case R.id.roomsAndFloor:
                final View view2 = this.getLayoutInflater().inflate(R.layout.display_by_rooms_and_floor, null);
                currentDialog = new AlertDialog.Builder(this)
                        .setTitle("По комнатам и этажу")
                        .setView(view2)
                        .setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                displayByRoomsAndFloor(view2);
                            }
                        })
                        .create();
                currentDialog.show();
                break;
            case R.id.square:
                final View view3 = this.getLayoutInflater().inflate(R.layout.display_by_square, null);
                currentDialog = new AlertDialog.Builder(this)
                        .setTitle("По площади")
                        .setView(view3)
                        .setPositiveButton("Выбрать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                displayBySquare(view3);
                            }
                        })
                        .create();
                currentDialog.show();
                break;
        }
        return true;
    }

    public void addHouse(View view) {
        List<String> temp = new ArrayList<>(7);
        
        temp.add(((EditText)view.findViewById(R.id.editNumber)).getText().toString());
        temp.add(((EditText)view.findViewById(R.id.editSquare)).getText().toString());
        temp.add(((EditText)view.findViewById(R.id.editFloor)).getText().toString());
        temp.add(((EditText)view.findViewById(R.id.editRoom)).getText().toString());
        temp.add(((EditText)view.findViewById(R.id.editStreet)).getText().toString());
        temp.add(((EditText)view.findViewById(R.id.editType)).getText().toString());
        temp.add(((EditText)view.findViewById(R.id.editLifetime)).getText().toString());

        try {
            houses.add(new House(Integer.parseInt(temp.get(0)), Double.parseDouble(temp.get(1)),
                    Integer.parseInt(temp.get(2)), Integer.parseInt(temp.get(3)), temp.get(4),
                    temp.get(5), Double.parseDouble(temp.get(6))));
        } catch (NumberFormatException e) {
            e.printStackTrace();

            Toast.makeText(this, "Ошибка!!!", Toast.LENGTH_SHORT ).show();
            return;
        }
        currentDialog.dismiss();

        Toast.makeText(this, "Запись успешно добавлена", Toast.LENGTH_SHORT ).show();
    }

    public void displayByRooms(View view){
        LinearLayout linearLayout = findViewById(R.id.display);
        linearLayout.removeAllViews();

        EditText editText = view.findViewById(R.id.editRoomBy);
        List<House> temp = new House().PresetNumberOfRooms(houses, Integer.parseInt(editText.getText().toString()));

        display(linearLayout, temp);
    }

    public void displayByRoomsAndFloor(View view){
        LinearLayout linearLayout = findViewById(R.id.display);
        linearLayout.removeAllViews();

        EditText editText = view.findViewById(R.id.editRoomBy);
        EditText leftFloor = view.findViewById(R.id.editFloorByLeft);
        EditText rightFloor = view.findViewById(R.id.editFloorByRight);

        int left =Integer.parseInt(leftFloor.getText().toString());
        int right = Integer.parseInt(rightFloor.getText().toString());

        if (left < right){
            List<House> temp = new House().RoomsAndFloor(houses, Integer.parseInt(editText.getText().toString()),left, right );
            display(linearLayout, temp);
        }else {
            Toast.makeText(this, "Ошибка!!! Левая граница должна быть больше прваной!!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void  displayBySquare(View view){
        LinearLayout linearLayout = findViewById(R.id.display);
        linearLayout.removeAllViews();

        EditText editText = view.findViewById(R.id.editSquareBy);

        List<House> temp = new House().PresetSquare(houses, Double.parseDouble(editText.getText().toString()));
        display(linearLayout, temp);
    }


    private void display(LinearLayout linearLayout, List<House> temp){
        for(House house : temp){
            TextView textView = new TextView(this);
            textView.setText(house.toString());
            textViews.add(textView);

            linearLayout = findViewById(R.id.display);
            linearLayout.addView(textView);
        }
    }
}
