package com.example.ditimtrieuphu.model;

import java.io.Serializable;

public class PeopleCall implements Serializable {
    private String name;
    private String answer;
    private String urlImage;

    public PeopleCall(String name, String answer, String urlImage) {
        this.name = name;
        this.answer = answer;
        this.urlImage = urlImage;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
