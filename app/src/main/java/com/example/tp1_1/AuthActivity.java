package com.example.tp1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class AuthActivity extends AppCompatActivity {
    public static final String SHAREDPREFERENCES_FILE = "com.tp1.dannydeghmous.ACCOUNT_INFO";
    public static final String SHAREDPREFERENCES_STATUT_CONNECTION = "IS_CONNECTED";
    public static final String SHAREDPREFERENCES_INFOS_UTILISATEUR = "INFOS_UTILISATEUR";


    final String USERNAME = "cegep";
    final String PASSWORD = "123";

    Button btnLogin;
    EditText editTextUsername;
    EditText editTextPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

//        ChangerLangue("fr");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPasswd = (EditText) findViewById(R.id.editTextPassword);

        // Lors du lancement de l'app, verifier si utilisateur existe
        if (!EstDejaConnecte())
            Login();
        else {
            NaviguerVersMenuPrincipal();
        }
    }

    // Si utilisateur clique sur le bouton retour et reviens ici, verifier qu'il est connecté et le rediriger vers la bonne activité.
    // Évite à l'utilisateur de devoir re-entrer les infos de connections.
    @Override
    protected void onResume() {
        super.onResume();
        if (!EstDejaConnecte())
            Login();
        else {
            NaviguerVersMenuPrincipal();
        }
    }

    // Verifier si utilisateur est deja connecte
    private boolean EstDejaConnecte() {
        SharedPreferences sharedpreferences = getSharedPreferences(SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(SHAREDPREFERENCES_STATUT_CONNECTION, false);
    }


    private void Login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValiderChamps() && ValiderInfosConnection()) {
                    // Ajouter au sharepreferences le username/passwd
                    SharedPreferences sharedpreferences = getSharedPreferences(SHAREDPREFERENCES_FILE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putBoolean(SHAREDPREFERENCES_STATUT_CONNECTION, true);
                    editor.putString(SHAREDPREFERENCES_INFOS_UTILISATEUR, editTextUsername.getText().toString().trim());
                    editor.apply();

                    NaviguerVersMenuPrincipal();
                }
            }
        });
    }

    // Naviger vers le menu principal
    private void NaviguerVersMenuPrincipal() {
        Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(i);
    }

    // Valider les champs `username` et `password` (qu'ils sont définis)
    private boolean ValiderChamps() {
        if (editTextUsername.getText().toString().trim().length() <= 0) {
            Toast.makeText(getApplicationContext(),getString(R.string.username) + " " + getString(R.string.undefined_error), Toast.LENGTH_SHORT).show();
            return false;
        } else if (editTextPasswd.getText().toString().trim().length() <= 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.password) + " " + getString(R.string.undefined_error), Toast.LENGTH_SHORT).show();
            return false;
        }
            return true;
    }

    // Valider que `username` et `password` sont bons pour se connecter
    private boolean ValiderInfosConnection() {
        if (!editTextUsername.getText().toString().trim().equals(USERNAME) ||
                !editTextPasswd.getText().toString().trim().equals(PASSWORD)) {

            Toast.makeText(getApplicationContext(), getString(R.string.login_wrong_credentials), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /*
           Utilisé pour changer de langue pour tester l'internationalization
           ex: langue: "fr", "en", "fa", "jp", "it", etc.
           https://stackoverflow.com/a/9173571
           (Pour tester les langues)
        */
    private void ChangerLangue(String langue){
        String languageToLoad  = "fr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_auth);
    }

}