package com.example.pokeamum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    List<Player> players;
    TextView p1Point;
    TextView p2Point;
    TextView roundCount;
    ImageView p1Card;
    ImageView p2Card;
    Button nextRound;

    Integer round;
    Integer p1Points;
    Integer p2Points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initGui();
        round = 0;
        p1Points = 0;
        p2Points = 0;

        players = (List<Player>) getIntent().getSerializableExtra("Players");
        nextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battle();
            }
        });
        battle();


        //this is a comment
    }
    private void initGui(){
        p1Point = findViewById(R.id.player1_point);
        p2Point = findViewById(R.id.player2_point);
        roundCount = findViewById(R.id.round_count);
        p1Card = findViewById(R.id.player1_card);
        p2Card = findViewById(R.id.player2_card);
        nextRound = findViewById(R.id.nxt_round);

    }

    private void battle(){
        getCard(0);
        getCard(1);
        roundCounter();



    }
    private void getCard(int playIndex){
        String cardUrl = players.get(playIndex).cards.get(round).image;
        String imageUrl = cardUrl + "/high.jpg";
        if (playIndex == 0){
            Picasso.get().load(imageUrl).into(p1Card);
        } else if (playIndex == 1) {
            Picasso.get().load(imageUrl).into(p2Card);
        }

    }
    private void getFight(){
        for(Attack attack: players.get(1).cards.get(round).attacks){

        }
    }
    private void roundCounter(){
        round++;
        ((TextView)findViewById(R.id.round_count)).setText(round.toString());
    }

}