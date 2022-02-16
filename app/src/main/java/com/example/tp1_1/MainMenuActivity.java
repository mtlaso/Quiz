package com.example.tp1_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

       ChargerDonnees();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChargerDonnees();
    }

    private void ChargerDonnees() {
        SharedPreferences sharedpreferences = getSharedPreferences(AuthActivity.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
        String userName = sharedpreferences.getString(AuthActivity.SHAREDPREFERENCES_INFOS_UTILISATEUR, "");

        // Si l'utilisateur arrive directement vers `activity_jeu` sans authentification, le retrourner au menu de connection
        if (userName.trim().length() <= 0) {
            Quitter(null);
            NaviguerVersAuth();
        }

        TextView txtViewConnectedAs = (TextView) findViewById(R.id.txtViewConnectedAs);
        txtViewConnectedAs.setText(String.format("%s %s", getString(R.string.connected_as), userName));
    }

    // Commencer le jeu
    public void Jouer(View v){
        Intent i = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(i);
    }

    public void AfficherStats(View v) {
        Intent i = new Intent(getApplicationContext(), StatsActivity.class);
        startActivity(i);
    }

    public void AfficherConfiguration(View v) {
        Intent i = new Intent(getApplicationContext(), ConfigurationActivity.class);
        startActivity(i);
    }

    // Quitter l'application (déconnecter)
    public void Quitter(View v) {
        // Effacer sharedPreferences
        SharedPreferences s = getSharedPreferences(AuthActivity.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
        s.edit().clear().apply();

        // Message
        Toast.makeText(getApplicationContext(), getString(R.string.logout_message), Toast.LENGTH_SHORT).show();

        // Retourner au menu de connection
        NaviguerVersAuth();

    }

    private void NaviguerVersAuth() {
        Intent i = new Intent(getApplicationContext(), AuthActivity.class);
        startActivity(i);
    }

}