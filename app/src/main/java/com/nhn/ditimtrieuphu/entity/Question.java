package com.nhn.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Question",primaryKeys = {"_id"})
public class Question {
    @NonNull
    @ColumnInfo(name="_id")
    private String id;

    @ColumnInfo(name = "level")
    private String level;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @ColumnInfo(name="question")
    private String question;
    @ColumnInfo(name = "casea")
    private String caseA;
    @ColumnInfo(name = "caseb")
    private String caseB;
    @ColumnInfo(name = "casec")
    private String caseC;
    @ColumnInfo(name = "cased")
    private String caseD;

    @ColumnInfo(name = "truecase")
    private String trueCase;

    public String getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getCaseA() {
        return caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    public String getTrueCase() {
        return trueCase;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCaseA(String caseA) {
        this.caseA = caseA;
    }

    public void setCaseB(String caseB) {
        this.caseB = caseB;
    }

    public void setCaseC(String caseC) {
        this.caseC = caseC;
    }

    public void setCaseD(String caseD) {
        this.caseD = caseD;
    }

    public void setTrueCase(String trueCase) {
        this.trueCase = trueCase;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", question='" + question + '\'' +
                ", caseA='" + caseA + '\'' +
                ", caseB='" + caseB + '\'' +
                ", caseC='" + caseC + '\'' +
                ", caseD='" + caseD + '\'' +
                ", trueCase='" + trueCase + '\'' +
                '}';
    }
}
