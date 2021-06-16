package com.example.ditimtrieuphu.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity(tableName = "Question")
public class Question {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    @NotNull
    private String id;

    @ColumnInfo(name = "level")
    private String level;

    @ColumnInfo(name = "question")
    private String ques;

    @ColumnInfo(name = "casea")
    private String caseA;

    @ColumnInfo(name = "caseb")
    private String caseB;

    @ColumnInfo(name = "casec")
    private String caseC;

    @ColumnInfo(name = "cased")
    private String caseD;

    @ColumnInfo(name = "truecase")
    private String truecase;

    public Question() {
    }
}
