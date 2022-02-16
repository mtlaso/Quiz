package com.example.tp1_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    ArrayList<Stats> _lstStats;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

       _lstStats = (ArrayList<Stats>) ChargerDonnees();

       if (_lstStats != null) {
            recyclerView = (RecyclerView) findViewById(R.id.rcViewStats);
            StatsAdapter statsAdapter = new StatsAdapter(_lstStats);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(statsAdapter);
       }
    }

    private Object ChargerDonnees() {
        SharedPreferences sharedpreferences = getSharedPreferences(GameActivity.SHAREDPREFERENCES_FILE_STATS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedpreferences.getString(GameActivity.SHAREDPREFERENCES_STATS, null);

        Type type = new TypeToken<ArrayList<Stats>>(){}.getType();

        return gson.fromJson(json, type);
//        _lstStats = gson.fromJson(json, type);
    }
}