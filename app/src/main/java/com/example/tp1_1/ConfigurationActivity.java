package com.example.tp1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigurationActivity extends AppCompatActivity {
    EditText editTxtUsernameConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        editTxtUsernameConf = (EditText) findViewById(R.id.EditTxtUsernameConf);

        ChargerDonnees();

    }

    public void ChangerUsername(View v) {
        if (ValiderChamps()) {
            // Ajouter au sharepreferences le username/passwd
            SharedPreferences sharedpreferences = getSharedPreferences(AuthActivity.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putBoolean(AuthActivity.SHAREDPREFERENCES_INFOS_UTILISATEUR, true);
            editor.putString(AuthActivity.SHAREDPREFERENCES_INFOS_UTILISATEUR, editTxtUsernameConf.getText().toString().trim());
            editor.apply();

            Toast.makeText(getApplicationContext(), getString(R.string.config_info_saved), Toast.LENGTH_SHORT).show();

            NavigerVersMenuPrincipal();
        }
    }

    private void ChargerDonnees() {
        SharedPreferences sharedpreferences = getSharedPreferences(AuthActivity.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
        String userName = sharedpreferences.getString(AuthActivity.SHAREDPREFERENCES_INFOS_UTILISATEUR, "");

        if (userName.trim().length() <= 0) {
            Quitter();
            NaviguerVersAuth();
        } else {
            editTxtUsernameConf.setText(userName);
        }

    }

    // Valider les champs `username` et `password` (qu'ils sont définis)
    private boolean ValiderChamps() {
        if (editTxtUsernameConf.getText().toString().trim().length() <= 0) {
            Toast.makeText(getApplicationContext(),getString(R.string.username) + " " + getString(R.string.undefined_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Quitter l'application (déconnecter)
    public void Quitter() {
        // Effacer sharedPreferences
        SharedPreferences s = getSharedPreferences(AuthActivity.SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
        s.edit().clear().apply();

        // Message
        Toast.makeText(getApplicationContext(), getString(R.string.logout_message), Toast.LENGTH_SHORT).show();

        // Retourner au menu de connection
        NaviguerVersAuth();

    }

    private void NavigerVersMenuPrincipal() {
        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(i);
    }

    private void NaviguerVersAuth() {
        Intent i = new Intent(getApplicationContext(), AuthActivity.class);
        startActivity(i);
    }
}