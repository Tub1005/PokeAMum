package com.example.pokeamum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker cardsPicker = findViewById(R.id.np_cards);
        cardsPicker.setMinValue(3);
        cardsPicker.setMaxValue(20);
        cardsPicker.setValue(5);

        NumberPicker playersPicker = findViewById(R.id.np_players);
        playersPicker.setMinValue(2);
        playersPicker.setMaxValue(2);
        playersPicker.setValue(2);

        GameSetup gameSetup = new GameSetup(this);

        Button startButton = findViewById(R.id.btn_start);
        startButton.setOnClickListener(view -> {
            startButton.setText(R.string.loading);
            startButton.setEnabled(false);
            gameSetup.numPlayers = playersPicker.getValue();
            gameSetup.numCards = cardsPicker.getValue();
            gameSetup.start();
        });
    }
}