package com.example.ditimtrieuphu.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "HighScore")
@Getter
@Setter
public class HighScore {
    @PrimaryKey
    @NotNull
    private String Name;

    private String Score;

    public HighScore() {
    }
}
