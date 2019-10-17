package com.labs.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.labs.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class SecondLabActivity extends AppCompatActivity {

    private AlertDialog currentDialog;
    private List<House> houses = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();

    private final static String FILE_PATH = "data.txt";

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
        switch (id) {
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
            case R.id.openFile:
                readFromFile();
                break;
            case R.id.saveFile:
                saveToFile();
                break;
        }
        return true;
    }

    public void addHouse(View view) {
        List<String> temp = new ArrayList<>(7);

        temp.add(((EditText) view.findViewById(R.id.editNumber)).getText().toString());
        temp.add(((EditText) view.findViewById(R.id.editSquare)).getText().toString());
        temp.add(((EditText) view.findViewById(R.id.editFloor)).getText().toString());
        temp.add(((EditText) view.findViewById(R.id.editRoom)).getText().toString());
        temp.add(((EditText) view.findViewById(R.id.editStreet)).getText().toString());
        temp.add(((EditText) view.findViewById(R.id.editType)).getText().toString());
        temp.add(((EditText) view.findViewById(R.id.editLifetime)).getText().toString());

        try {
            houses.add(newHouse(temp, 0));
        } catch (NumberFormatException e) {
            e.printStackTrace();

            Toast.makeText(this, "Ошибка!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        currentDialog.dismiss();

        Toast.makeText(this, "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
    }

    public void displayByRooms(View view) {
        LinearLayout linearLayout = findViewById(R.id.display);
        linearLayout.removeAllViews();

        EditText editText = view.findViewById(R.id.editRoomBy);
        List<House> temp = new House().PresetNumberOfRooms(houses, Integer.parseInt(editText.getText().toString()));

        display(linearLayout, temp);
    }

    public void displayByRoomsAndFloor(View view) {
        LinearLayout linearLayout = findViewById(R.id.display);
        linearLayout.removeAllViews();

        EditText editText = view.findViewById(R.id.editRoomBy);
        EditText leftFloor = view.findViewById(R.id.editFloorByLeft);
        EditText rightFloor = view.findViewById(R.id.editFloorByRight);

        int left = Integer.parseInt(leftFloor.getText().toString());
        int right = Integer.parseInt(rightFloor.getText().toString());

        if (left < right) {
            List<House> temp = new House().RoomsAndFloor(houses, Integer.parseInt(editText.getText().toString()), left, right);
            display(linearLayout, temp);
        } else {
            Toast.makeText(this, "Ошибка!!! Левая граница должна быть больше прваной!!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void displayBySquare(View view) {
        LinearLayout linearLayout = findViewById(R.id.display);
        linearLayout.removeAllViews();

        EditText editText = view.findViewById(R.id.editSquareBy);

        List<House> temp = new House().PresetSquare(houses, Double.parseDouble(editText.getText().toString()));
        display(linearLayout, temp);
    }


    private void display(LinearLayout linearLayout, List<House> temp) {
        for (House house : temp) {
            TextView textView = new TextView(this);
            textView.setText(house.toString());
            textViews.add(textView);

            linearLayout = findViewById(R.id.display);
            linearLayout.addView(textView);
        }
    }

    private void readFromFile() {
        try (FileInputStream fis = openFileInput(FILE_PATH)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String text = new String(bytes);

            List<String> data = textParsing(text);
            try {
                for (int i = 0; i < data.size(); i +=7){
                    houses.add(newHouse(data, i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            Toast.makeText(this, "Данные успешно загружены", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToFile() {
        try (FileOutputStream fos = openFileOutput(FILE_PATH, MODE_PRIVATE)) {
            if (houses.size() != 0) {
                for (House house : houses) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(house.getFlatNumber() + "," + house.getSquare() + "," +
                                house.getFloor() + "," + house.getNumberOfrooms() + "," +
                                house.getStreet() + "," + house.getBuildType() + "," +
                                house.getLifetime() + "\n");
                        fos.write(stringBuilder.toString().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> textParsing(String text) {
        Pattern pattern = Pattern.compile("[,]");
        List<String> result = Arrays.asList(pattern.split(text.replaceAll(" ", "")));

        return result;
    }

    private House newHouse(List<String> temp, int i){
        return new House(Integer.parseInt(temp.get(i)), Double.parseDouble(temp.get(i+1)),
                Integer.parseInt(temp.get(i+2)), Integer.parseInt(temp.get(i+3)), temp.get(i+4),
                temp.get(i+5), Double.parseDouble(temp.get(i+6)));
    }

}
