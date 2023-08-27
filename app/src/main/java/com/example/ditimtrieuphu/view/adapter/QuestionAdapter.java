package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.QuestionIndex;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context context;
    private List<QuestionIndex> listIndexQuestion;

    public QuestionAdapter(Context context, List<QuestionIndex> listIndexQuestion) {
        this.context = context;
        this.listIndexQuestion = listIndexQuestion;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvBonus.setText(listIndexQuestion.get(position).getPrize());

        int pos = listIndexQuestion.get(position).getIndex();
        if(pos == 1 || pos % 5 == 0){
            holder.tvBonus.setTextColor(context.getResources().getColor(R.color.color_question));
        }
        if (listIndexQuestion.get(position).isState()) {
            holder.frBackGround.setVisibility(View.VISIBLE);
        } else {
            holder.frBackGround.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(listIndexQuestion != null){
            return listIndexQuestion.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private FrameLayout frBackGround;
        private TextView tvBonus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBonus = itemView.findViewById(R.id.tv_prize);
            frBackGround = itemView.findViewById(R.id.background_question);
        }
    }
}
