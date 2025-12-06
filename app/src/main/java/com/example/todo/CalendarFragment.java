package com.example.todo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    // Static list shared with TasksFragment
    public static List<Task> allTasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendarfragment, container, false);

        CalendarView calendar = view.findViewById(R.id.calendar);
        TextView dateView = view.findViewById(R.id.date_view);
        TextView tasksForDate = view.findViewById(R.id.tasks_for_date);

        // Default text
        tasksForDate.setText("No tasks for this date");

        calendar.setOnDateChangeListener((calView, year, month, dayOfMonth) -> {

            // Month is 0-based, so add 1
            int realMonth = month + 1;

            // Match the format used by TasksFragment: MM/DD/YYYY
            String selectedDate = realMonth + "/" + dayOfMonth + "/" + year;

            // Update header text
            dateView.setText("Selected Date: " + selectedDate);

            // Find tasks due on this date
            StringBuilder builder = new StringBuilder();
            for (Task t : allTasks) {
                if (t.getDueDate().equals(selectedDate)) {
                    builder.append("• ").append(t.getTitle()).append("\n");
                }
            }

            // Display results
            if (builder.length() == 0) {
                tasksForDate.setText("No tasks for this date");
            } else {
                tasksForDate.setText(builder.toString());
            }
        });

        return view;
    }
}
