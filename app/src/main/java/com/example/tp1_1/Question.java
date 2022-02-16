package com.example.tp1_1;

import java.util.HashMap;
import java.util.Map;

public class Question {
    private String _question;
    private boolean _reponse;


    public String GetQuestion() {
        return _question;
    }

    public Boolean GetReponse() {
        return _reponse;
    }

    public void SetQuestion(String value){
        _question = value;
    }

    public void SetReponse(Boolean value){
        _reponse = value;
    }

    public Question(String pQuestion, Boolean pReponse) {
        SetQuestion(pQuestion);
        SetReponse(pReponse);
    }

}
