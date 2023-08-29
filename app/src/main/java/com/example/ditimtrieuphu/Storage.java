package com.example.ditimtrieuphu;

public class Storage {
    private int currentLevel = 0;
    private boolean state50 = false;
    private boolean stateAudi = false;
    private boolean stateChange = false;
    private boolean stateCall = false;

    public boolean isStateAudi() {
        return stateAudi;
    }

    public void setStateAudi(boolean stateAudi) {
        this.stateAudi = stateAudi;
    }

    public boolean isStateChange() {
        return stateChange;
    }

    public void setStateChange(boolean stateChange) {
        this.stateChange = stateChange;
    }

    public boolean isStateCall() {
        return stateCall;
    }

    public void setStateCall(boolean stateCall) {
        this.stateCall = stateCall;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }


    public boolean isState50() {
        return state50;
    }

    public void setState50(boolean state50) {
        this.state50 = state50;
    }

    public void resetPlaySession() {
        currentLevel = 0;
        state50 = false;
        stateAudi = false;
        stateChange = false;
        stateCall = false;
    }

}
