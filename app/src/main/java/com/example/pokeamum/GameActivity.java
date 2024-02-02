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
import android.widget.Toast;

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
        getFight();


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
        int p1NrOfAttack = 0;
        int p2NrOfAttack = 0;
        for(Attack attack: players.get(0).cards.get(round).attacks){
            if (attack == null || attack.damage == null){break;}
            int damage = Integer.parseInt(attack.damage);
            if (players.get(1).cards.get(round).hp == 0){break;}
            int opponentHp = players.get(1).cards.get(round).hp;
            p1NrOfAttack = opponentHp / damage;
        }for(Attack attack: players.get(1).cards.get(round).attacks){
            if (attack == null || attack.damage == null){break;}
            int damage = Integer.parseInt(attack.damage);
            if (players.get(1).cards.get(round).hp == 0){break;}
            int opponentHp = players.get(0).cards.get(round).hp;
            p2NrOfAttack = opponentHp / damage;
        }
        if (p1NrOfAttack > p2NrOfAttack){
            Toast.makeText(this, "Player 1 Wins", Toast.LENGTH_LONG).show();
            p1Points++;
            ((TextView)findViewById(R.id.player1_point)).setText("Player 1 points: " + p1Points.toString());
        } else if (p1NrOfAttack < p2NrOfAttack) {
            Toast.makeText(this, "Player 2 Wins", Toast.LENGTH_LONG).show();
            p2Points++;
            ((TextView)findViewById(R.id.player2_point)).setText("Player 2 points: " + p2Points.toString());
        }
        else {
            Toast.makeText(this, "Draw", Toast.LENGTH_LONG).show();
            p1Points++;
            p2Points++;
            ((TextView)findViewById(R.id.player1_point)).setText("Player 1 points: " + p1Points.toString());
            ((TextView)findViewById(R.id.player2_point)).setText("Player 2 points: " + p2Points.toString());
        }
    }
    private void roundCounter(){
        round++;
        ((TextView)findViewById(R.id.round_count)).setText("Round: " + round.toString());
    }

}