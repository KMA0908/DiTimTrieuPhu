package com.nhn.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HighScore")
public class HighScore {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="_id")
    @NonNull
    private int id;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "Score")
    private String Score;

    @ColumnInfo(name = "level")
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setScore(String score) {
        Score = score;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "name='" + name + '\'' +
                ", Score='" + Score + '\'' +
                '}';
    }
}
