package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.entity.Question;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context context;
    private List<Question> list;

    public QuestionAdapter(Context context, List<Question> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvBonus.setText(list.get(position).getPrize());

        int pos = list.get(position).getIndex();
        if(pos == 1 || pos % 5 == 0){
            holder.tvBonus.setTextColor(context.getResources().getColor(R.color.color_question));
        }
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvBonus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBonus = itemView.findViewById(R.id.tv_prize);
        }
    }
}
