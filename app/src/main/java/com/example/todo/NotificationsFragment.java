package com.example.todo;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notificationsfragment, container, false);

        LinearLayout taskContainer = view.findViewById(R.id.task_list_container);

        if (CalendarFragment.allTasks.isEmpty()) {
            TextView tv = new TextView(getContext());
            tv.setText("No tasks available");
            tv.setTextSize(18);
            taskContainer.addView(tv);
            return view;
        }

        for (Task task : CalendarFragment.allTasks) {

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 20, 0, 20);

            TextView taskText = new TextView(getContext());
            taskText.setText("• " + task.getTitle() + " (Due: " + task.getDueDate() + ")");
            taskText.setTextSize(16);
            taskText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            Button reminderBtn = new Button(getContext());
            reminderBtn.setText("Set Reminder");

            reminderBtn.setOnClickListener(v -> showReminderOptions(task));

            row.addView(taskText);
            row.addView(reminderBtn);

            taskContainer.addView(row);
        }

        return view;
    }


    // ============================
    //  Reminder Options Dialog
    // ============================
    private void showReminderOptions(Task task) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Set Reminder for " + task.getTitle());

        String[] options = {
                "Later today (4 hours later)",
                "Tomorrow",
                "Next Week"
        };

        builder.setItems(options, (dialog, which) -> {

            switch (which) {
                case 0:
                    scheduleReminder(task, 4 * 60 * 60 * 1000); // 4 hours
                    break;

                case 1:
                    scheduleReminder(task, 24 * 60 * 60 * 1000); // 24 hours
                    break;

                case 2:
                    scheduleReminder(task, 7 * 24 * 60 * 60 * 1000); // 7 days
                    break;
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    // ============================
    //  SCHEDULE ALARM
    // ============================
    private void scheduleReminder(Task task, long offsetMillis) {

        Context context = getContext();
        if (context == null) return;

        long triggerTime = System.currentTimeMillis() + offsetMillis;

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("task_title", task.getTitle());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int) System.currentTimeMillis(),  // unique request code
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
        );

        Toast.makeText(context,
                "Reminder set for \"" + task.getTitle() + "\"",
                Toast.LENGTH_SHORT).show();
    }
}
