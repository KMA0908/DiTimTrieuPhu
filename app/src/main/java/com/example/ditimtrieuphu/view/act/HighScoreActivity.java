package com.example.ditimtrieuphu.view.act;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.view.adapter.HighScoreAdapter;
import com.example.ditimtrieuphu.view.fragment.M002MainFragment;

import static com.example.ditimtrieuphu.R.color.black;

public class HighScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ImageView highScoreBackBtn;

    private MediaPlayer backGoundMusic;

    private boolean musicIsOn = false;

    private int images[] = {R.drawable.ic_launcher_background, R.drawable.tutorial_background, R.drawable.ic_call, R.drawable.ic_reset};
    private String names[] = {"Nguyen Van Thanh", "Nguyen Van B", "Nguyen Van C", "Nguyen Van D"};
    private int scores[] = {150000000, 200000, 300000, 400000};
    private String dates[] = {"01/01/2021", "02/02/2021", "03/03/2021", "04/04/2021"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        recyclerView = findViewById(R.id.recyclerView);

        if(M002MainFragment.m002MainFragment.musicIsOn) {
            backGoundMusic = MediaPlayer.create(this, R.raw.background_music_c);
            backGoundMusic.setLooping(true);
            backGoundMusic.start();
            musicIsOn = true;
        }

        highScoreBackBtn = findViewById(R.id.highScoreBackBtn);
        highScoreBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScoreActivity.super.onBackPressed();
            }
        });

        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(images, names, scores, dates, this);
        recyclerView.setAdapter(highScoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(musicIsOn) {
            backGoundMusic.pause();
            backGoundMusic.release();
        }
    }
}