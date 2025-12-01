package com.example.todo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;   // edit and save buttons
import android.widget.EditText; // task editing input
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;     // save empty task

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

        holder.textView.setVisibility(View.VISIBLE);
        holder.editText.setVisibility(View.GONE);
        holder.editButton.setVisibility(View.VISIBLE);
        holder.saveButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton doneButton;

        EditText editText;
        Button editButton;
        Button saveButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.view_task);
            doneButton = itemView.findViewById(R.id.done);

            editText = itemView.findViewById(R.id.edit_task_text);
            editButton = itemView.findViewById(R.id.edit_task_button);
            saveButton = itemView.findViewById(R.id.save_edit_button);

            //
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        list.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        editText.setText(list.get(position));

                        textView.setVisibility(View.GONE);
                        editText.setVisibility(View.VISIBLE);
                        editButton.setVisibility(View.GONE);
                        saveButton.setVisibility(View.VISIBLE);

                        editText.requestFocus();
                    }
                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String newText = editText.getText().toString().trim();

                        if (!newText.isEmpty()) {
                            // 1. Update the underlying list
                            list.set(position, newText);

                            textView.setText(newText);
                            textView.setVisibility(View.VISIBLE);
                            editText.setVisibility(View.GONE);
                            editButton.setVisibility(View.VISIBLE);
                            saveButton.setVisibility(View.GONE);

                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(itemView.getContext(), "Task cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}