package com.example.ditimtrieuphu.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "manager")
@Getter
@Setter
public class Manager {
    @PrimaryKey @NotNull
    public int _id;

    public Integer Pass;


    public Manager() {
    }
}
