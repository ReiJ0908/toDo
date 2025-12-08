package com.example.todo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class task_adapter extends RecyclerView.Adapter<task_adapter.TaskViewHolder> {

    private List<Task> list;

    // Updated constructor to use Task instead of String
    public task_adapter(ArrayList<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_style, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = list.get(position);

        // Display task title
        holder.textView.setText(task.getTitle());

        // Show due date if available
        if (task.getDueDate() != null && !task.getDueDate().isEmpty()) {
            holder.dueDateView.setText("Due: " + task.getDueDate());
        } else {
            holder.dueDateView.setText("Due: (none)");
        }

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
        TextView dueDateView;

        ImageButton doneButton;

        EditText editText;
        Button editButton;
        Button saveButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.view_task);
            dueDateView = itemView.findViewById(R.id.view_due_date); // ADD THIS TO list_style.xml
            doneButton = itemView.findViewById(R.id.done);

            editText = itemView.findViewById(R.id.edit_task_text);
            editButton = itemView.findViewById(R.id.edit_task_button);
            saveButton = itemView.findViewById(R.id.save_edit_button);

            // Delete Task
            doneButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            });

            // Start Editing
            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    Task task = list.get(position);

                    // Put the task title into the edit field
                    editText.setText(task.getTitle());

                    textView.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);

                    // 🔥 Hide due date while editing
                    dueDateView.setVisibility(View.GONE);

                    editText.requestFocus();
                }
            });


            // Save Edited Task
            saveButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    String newTitle = editText.getText().toString().trim();

                    if (!newTitle.isEmpty()) {

                        Task task = list.get(position);
                        task.setTitle(newTitle);

                        textView.setText(newTitle);

                        textView.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                        editButton.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.GONE);

                        // 🔥 Show due date again after saving
                        dueDateView.setVisibility(View.VISIBLE);

                        notifyItemChanged(position);

                    } else {
                        Toast.makeText(itemView.getContext(),
                                "Task cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
