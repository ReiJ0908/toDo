package com.example.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


public class task_adapter extends RecyclerView.Adapter<task_adapter.TaskViewHolder> {
    private List<String> list;

    public task_adapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_style, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String task = list.get(position);
        holder.textView.setText(task);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton doneButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.view_task);
            doneButton = itemView.findViewById(R.id.done);

            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}