package com.example.todo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;
import android.app.AlarmManager;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{
    private List<String> list;
    private task_adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestExactAlarmPermission();
        // 🔽 Add BottomNavigationView setup
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Load TasksFragment by default on startup
        if (savedInstanceState == null) {
            loadFragment(new TasksFragment());
            bottomNav.setSelectedItemId(R.id.nav_tasks); // highlight the tab
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.nav_tasks:

                        loadFragment(new TasksFragment());
                        return true;

                    case R.id.nav_calendar:

                        loadFragment(new CalendarFragment());
                        return true;

                    case R.id.nav_notifications:

                        loadFragment(new NotificationsFragment());
                        return true;

                    case R.id.nav_progress:

                        loadFragment(new ProgressTrackerFragment());
                        return true;
                }
                return false;

            }



        });
        // 🔼 End BottomNavigationView setup
    }

    private void loadFragment(Fragment fragment) {
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }
    private void requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
    }

}