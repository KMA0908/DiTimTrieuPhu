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

import java.text.DecimalFormat;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHoder> {

    private int images[];
    private String names[];
    private int scores[];
    private String dates[];
    Context context;

    public HighScoreAdapter(int[] images, String[] names, int[] scores, String[] dates, Context context) {
        this.images = images;
        this.names = names;
        this.scores = scores;
        this.dates = dates;
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
        String scoreString = String.format("$%,d", scores[position]);

        holder.image.setImageResource(images[position]);
        holder.name.setText(names[position]);
        holder.score.setText(scoreString);
        holder.date.setText(dates[position]);
        if(position % 2 == 1) {
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_yellow));
        }
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, score, date;
        CardView cardView;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_highScore);
            name = itemView.findViewById(R.id.txt_highScoreName);
            score = itemView.findViewById(R.id.txt_highScore);
            date = itemView.findViewById(R.id.txt_highScoreDate);
            cardView = itemView.findViewById(R.id.cardview1);
        }
    }
}
