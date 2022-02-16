package com.example.tp1_1;

import java.time.LocalDate;
import java.util.Date;

public class Stats {
    private int _score;
    private String _date;

    public int GetScore() {
        return _score;
    }

    public void SetScore(int _score) {
        this._score = _score;
    }

    public String GetDate() {
        return _date;
    }

    public void SetDate(String _date) {
        this._date = _date;
    }

    public Stats(int pScore, String pDate) {
        SetScore(pScore);
        SetDate(pDate);
    }
}
