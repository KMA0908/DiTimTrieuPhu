package com.example.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HighScore")
public class HighScore {
    @PrimaryKey
    @ColumnInfo(name = "Name")
    @NonNull
    private String name;
    @ColumnInfo(name = "Score")
    private String Score;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    private String level;
    public HighScore(@NonNull String name, String score,String level) {
        this.name = name;
        Score = score;
        this.level = level;
    }
    public HighScore() {

    }
    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "name='" + name + '\'' +
                ", Score='" + Score + '\'' +
                '}';
    }
}
