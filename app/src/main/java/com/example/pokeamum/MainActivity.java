package com.example.pokeamum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchField;
    LinearLayout searchBar;
    ImageButton searchButton;
    Spinner searchResultSpinner;
    public static RequestQueue requestQueue;
    List<EveryCards> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();

        requestQueue = Volley.newRequestQueue(this);
        searchButton.setOnClickListener(this);
        getAllCards();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initGui() {
        searchField = findViewById(R.id.et_search);
        searchButton = findViewById(R.id.btn_search);
        searchResultSpinner = findViewById(R.id.sp_searchresult);
        searchBar = findViewById(R.id.ll_searchbar);
    }

    @Override
    public void onClick(View view) {
        if (cards == null) return;

        List<EveryCards> searchList = new ArrayList<>();
        for (EveryCards pc : cards) {
            if (pc.name.toLowerCase().contains(searchField.getText().toString().toLowerCase())) {
                searchList.add(pc);
            }
        }
        createSpinner(searchList);
    }

    private void createSpinner(List<EveryCards> pcList) {
        List<String> pcNames = pcList.stream().map(EveryCards -> EveryCards.name + ":" + EveryCards.id)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, pcNames);
        searchResultSpinner.setAdapter(adapter);
        searchResultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstTime = true;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (firstTime) {
                    firstTime = false;
                    return;
                }

                String id = pcNames.get(index).split(":")[1];
                Intent intent = new Intent(view.getContext(), CardActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

//               startActivity(new Intent(MainActivity.this, CardActivity.class)
//                       .putExtra("id", pcNames.get(index).split("|")[1]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAllCards() {
        String url = "https://api.tcgdex.net/v2/en/cards";

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            cards = new Gson().fromJson(response, new TypeToken<List<EveryCards>>() {
            }.getType());
            Toast.makeText(this, "Cards: " + cards.size(), Toast.LENGTH_LONG).show();
            searchBar.setVisibility(View.VISIBLE);
            Log.d("Cards", String.valueOf(cards.size()));
        }, error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        );
        requestQueue.add(request);
    }
}







