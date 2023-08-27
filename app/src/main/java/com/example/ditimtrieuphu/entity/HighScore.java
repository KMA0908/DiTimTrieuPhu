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
