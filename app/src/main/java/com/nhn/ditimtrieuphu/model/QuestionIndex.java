package com.nhn.ditimtrieuphu.model;

public class QuestionIndex {

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

    public QuestionIndex(String prize, int index, boolean state) {
        Prize = prize;
        this.index = index;
        this.state = state;
    }

}
