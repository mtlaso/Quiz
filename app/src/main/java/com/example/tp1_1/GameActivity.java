package com.example.tp1_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    public static final String SHAREDPREFERENCES_FILE_STATS = "com.tp1.dannydeghmous.GAME_STATS";
    public static final String SHAREDPREFERENCES_STATS = "GAME_STATS";

    ArrayList<Question> _lstQuestions = new ArrayList<>();

    int _position = 0;
    int _score = 0;

    TextView txtViewNumQuestions;
    TextView txtViewQuestion;
    TextView txtViewScore;
    Button btnTrue;
    Button btnFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_FILE_STATS, 0);
//        preferences.edit().clear().apply();

        Question q1 = new Question("La capitale du Vietnam est-elle Ho Chi Minh City ?", false);
        Question q2 = new Question("La Capitale du Maroc est-elle Tanger ?", false);
        Question q3 = new Question("Belgrade, est la capitale de la Serbie.", true);
        Question q4 = new Question("Le Super Bowl fut crée en 1968.", false);
        Question q5 = new Question("Le premier acteur à avoir joué dans James Bond est Roger Moore.", false);
        Question q6 = new Question("Le Luxembourg, est un des pays avec le plus grand secteur spatial au monde.", true);
        Question q7 = new Question("Le Brésil a accumulé un total de 5 coupes du monde.", true);
        Question q8 = new Question("Le réseau social Reddit a plus d'utilisateur actif par jour que Twitter.", false);
        Question q9 = new Question("Les habitants de la Barbade sont appelés Barbadiens.", true);
        Question q10 = new Question("La couleur la plus aimée au monde est le jaune.", false);

        _lstQuestions.add(q1);
        _lstQuestions.add(q2);
        _lstQuestions.add(q3);
        _lstQuestions.add(q4);
        _lstQuestions.add(q5);
        _lstQuestions.add(q6);
        _lstQuestions.add(q7);
        _lstQuestions.add(q8);
        _lstQuestions.add(q9);
        _lstQuestions.add(q10);


        Collections.shuffle(_lstQuestions);


        txtViewNumQuestions = (TextView) findViewById(R.id.txtViewNumQuestion);
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewScore = (TextView) findViewById(R.id.txtViewScore);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);

        ProchaineQuestion(0);
        ValiderQuestions();
    }

    private void ProchaineQuestion(int pPosition) {
        ChangerScore();
        if (!FinJeu())
        {
            int numQuestions = _lstQuestions.size();
            int vraiPosition = _position;

            txtViewNumQuestions.setText(String.format("%s / %s", vraiPosition + 1, numQuestions));
            txtViewQuestion.setText(_lstQuestions.get(pPosition).GetQuestion());
            txtViewScore.setText(String.format("%s %s", getString(R.string.game_score), _score));

            btnTrue.setText(getString(R.string.gamebtn_true));
            btnFalse.setText(getString(R.string.gamebtn_false));
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.game_ended), Toast.LENGTH_SHORT).show();
        }

    }

    private void ChangerScore() {
        txtViewScore.setText(String.format("%s %s", getString(R.string.game_score), _score));
    }

    private void ValiderQuestions() {
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = _lstQuestions.get(_position).GetReponse().toString().trim().toLowerCase();
                String reponse = btnTrue.getText().toString().trim().toLowerCase();

                if (question.equals(reponse)) {
                    _score+=1;
                    Toast.makeText(getApplicationContext(), getString(R.string.game_correct), Toast.LENGTH_SHORT).show();
                } else {
                    DiminuerScore();
                    Toast.makeText(getApplicationContext(), getString(R.string.game_incorrect), Toast.LENGTH_SHORT).show();
                }

                _position+=1;
                ProchaineQuestion(_position);
            }
        });


        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = _lstQuestions.get(_position).GetReponse().toString().trim().toLowerCase();
                String reponse = btnFalse.getText().toString().trim().toLowerCase();

                if (question.equals(reponse)) {
                    _score += 1;
                    Toast.makeText(getApplicationContext(), getString(R.string.game_correct), Toast.LENGTH_SHORT).show();
                } else {
                    DiminuerScore();
                    Toast.makeText(getApplicationContext(), getString(R.string.game_incorrect), Toast.LENGTH_SHORT).show();
                }


                _position+=1;
                ProchaineQuestion(_position);

            }
        });

    }

    private void DiminuerScore() {
        if (_score <= 0) _score = 0;
        else _score -= 1;
    }

    private boolean FinJeu() {
        if (_position >= _lstQuestions.size()){
            btnTrue.setOnClickListener(null);
            btnFalse.setOnClickListener(null);

            EnregisterStats();
            return true;
        }
        else return false;
    }

    public void PartagerScore(View v) {
        if (FinJeu()) {
            Intent sendIntent = new Intent();
            String text = String.format("%s! %s %s!",getString(R.string.app_name), getString(R.string.game_shareintent_text), _score);
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        } else
            Toast.makeText(getApplicationContext(),
                    getString(R.string.game_shareintent_alert),
                    Toast.LENGTH_SHORT).show();
    }

    private void EnregisterStats() {
        // Charger ancinnes données
        ArrayList<Stats> anciennesDonnees = (ArrayList<Stats>) ChargerStats();

        // Creer nouvelle donnée
        SharedPreferences sharedpreferences = getSharedPreferences(SHAREDPREFERENCES_FILE_STATS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        Stats statistiques = new Stats(_score, java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));

        // Combiner ancinnes données et nouvelles
        ArrayList<Stats> nouvellesDonnees = new ArrayList<>();
        if (anciennesDonnees != null)
            nouvellesDonnees.addAll(anciennesDonnees);

        nouvellesDonnees.add(statistiques);

        Gson gson = new Gson();
        String json = gson.toJson(nouvellesDonnees);

        editor.putString(SHAREDPREFERENCES_STATS, json);
        editor.apply();
    }

    private Object ChargerStats() {
        SharedPreferences sharedpreferences = getSharedPreferences(SHAREDPREFERENCES_FILE_STATS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedpreferences.getString(SHAREDPREFERENCES_STATS, null);

        Type type = new TypeToken<ArrayList<Stats>>(){}.getType();

        return gson.fromJson(json, type);
    }

}