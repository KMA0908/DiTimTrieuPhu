package com.example.ditimtrieuphu.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Question {

    private String Prize;
    private boolean state;
    private int index;

    public String getPrize() {
        return Prize;
    }

    public void setPrize(String prize) {
        Prize = prize;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Question(String prize, int index, boolean state) {
        Prize = prize;
        this.index = index;
        this.state = state;
    }

}
