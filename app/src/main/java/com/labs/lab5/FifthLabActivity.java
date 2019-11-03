package com.labs.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labs.R;

public class FifthLabActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText topicEditText;
    private EditText messageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_lab);

        emailEditText = findViewById(R.id.emailEditText);
        topicEditText = findViewById(R.id.topicEditText);
        messageEditText = findViewById(R.id.messageEditText);

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void send() {
        final String email = emailEditText.getText().toString().trim();
        final String theme = topicEditText.getText().toString().trim();
        final String text = messageEditText.getText().toString().trim();
        if (!email.equals("") && !theme.equals("") && !text.equals("")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageSender emailSender = new MessageSender(email, theme);
                    emailSender.sendMessage(text);
                    onSuccess();
                }
            }).start();
        } else {
            Toast.makeText(this, "Пожалуйста заполните поля!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        emailEditText.setText("");
        topicEditText.setText("");
        messageEditText.setText("");
    }

    private void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FifthLabActivity.this, "Письмо отправлено", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        });
    }

}
