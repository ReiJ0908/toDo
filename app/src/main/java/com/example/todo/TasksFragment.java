package com.example.todo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    private ArrayList<Task> list;   // Task list
    private task_adapter adapter;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tasksfragment, container, false);

        // Initialize Task list & adapter
        list = new ArrayList<>();
        adapter = new task_adapter((ArrayList<Task>) CalendarFragment.allTasks);

        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Setup Add Task button
        ImageButton addTask = view.findViewById(R.id.add_task);

        addTask.setOnClickListener(v -> {

            View dialogView = LayoutInflater.from(getContext())
                    .inflate(R.layout.task_add, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setView(dialogView);

            EditText askTask = dialogView.findViewById(R.id.new_task);
            DatePicker dueDatePicker = dialogView.findViewById(R.id.due_date_picker); // <-- date picker
            Button cancel = dialogView.findViewById(R.id.c_t);
            Button add = dialogView.findViewById(R.id.a_t);

            AlertDialog dialog = builder.create();
            dialog.show();

            cancel.setOnClickListener(v1 -> dialog.dismiss());

            add.setOnClickListener(v2 -> {
                String taskName = askTask.getText().toString().trim();

                // Retrieve date from DatePicker
                int day = dueDatePicker.getDayOfMonth();
                int month = dueDatePicker.getMonth() + 1; // month is 0-based
                int year = dueDatePicker.getYear();
                String dueDate = month + "/" + day + "/" + year; // format: MM/DD/YYYY

                if (!taskName.isEmpty()) {
                    Task newTask = new Task(taskName, dueDate);

                    // Add to local list (RecyclerView)
                    list.add(newTask);
                    adapter.notifyItemInserted(list.size() - 1);

                    // Add to global calendar list
                    CalendarFragment.allTasks.add(newTask);
                    adapter.notifyItemInserted(CalendarFragment.allTasks.size() - 1);

                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
