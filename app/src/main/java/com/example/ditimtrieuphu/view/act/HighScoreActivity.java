package com.example.ditimtrieuphu.view.act;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ditimtrieuphu.R;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    ArrayList<String> arrayNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        arrayNames = new ArrayList<>();
    }
}