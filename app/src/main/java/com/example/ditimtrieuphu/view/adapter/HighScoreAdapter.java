package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.HighScore;

import java.text.DecimalFormat;
import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHoder> {

    private List<HighScore> highScoreList;
    private Context context;

    public HighScoreAdapter(List<HighScore> highScoreList, Context context) {
        this.highScoreList = highScoreList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.highscore_row, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreAdapter.ViewHoder holder, int position) {
        holder.highScore = highScoreList.get(position);
        holder.name.setText(highScoreList.get(position).getName());
        holder.score.setText(highScoreList.get(position).getScore());
        holder.level.setText(highScoreList.get(position).getLevel());
        if(position % 2 == 1) {
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_yellow));
        }
    }

    @Override
    public int getItemCount() {
        return highScoreList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        HighScore highScore;
        ImageView image;
        TextView name, score, level;
        CardView cardView;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_highScore);
            name = itemView.findViewById(R.id.txt_highScoreName);
            score = itemView.findViewById(R.id.txt_highScore);
            level = itemView.findViewById(R.id.txt_highScoreLevel);
            cardView = itemView.findViewById(R.id.cardview1);
        }
    }
}
