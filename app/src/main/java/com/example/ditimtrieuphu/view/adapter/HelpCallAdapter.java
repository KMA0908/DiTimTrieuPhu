package com.example.ditimtrieuphu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ditimtrieuphu.R;
import com.example.ditimtrieuphu.model.PeopleCall;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HelpCallAdapter extends RecyclerView.Adapter<HelpCallAdapter.HelpCallHolder> {
    private OnItemClick callBack;

    public void setOnItemClick(OnItemClick event) {
        callBack = event;
    }

    private List<PeopleCall> callList;
    private Context context;

    public HelpCallAdapter(Context context,List<PeopleCall> callList) {
        this.callList = callList;
        this.context = context;
    }

    @NonNull
    @Override
    public HelpCallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_call_help, parent, false);
        return new HelpCallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpCallHolder holder, int position) {
        holder.peopleCall = callList.get(position);
        holder.name.setText(callList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    public class HelpCallHolder extends RecyclerView.ViewHolder {
        PeopleCall peopleCall;
        CircleImageView image;
        TextView name;

        public HelpCallHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_person_help);
            name = itemView.findViewById(R.id.tv_name_person);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onItemClick(peopleCall);
                }
            });
        }
    }

    public interface OnItemClick {
        void onItemClick(PeopleCall peopleCall);
    }
}
