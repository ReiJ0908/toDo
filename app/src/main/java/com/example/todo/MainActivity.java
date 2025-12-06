package com.example.todo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
                        Toast.makeText(MainActivity.this, "Tasks", Toast.LENGTH_SHORT).show();
                        loadFragment(new TasksFragment());
                        return true;

                    case R.id.nav_calendar:
                        Toast.makeText(MainActivity.this, "Calendar", Toast.LENGTH_SHORT).show();
                        loadFragment(new CalendarFragment());
                        return true;

                    case R.id.nav_notifications:
                        Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
                        loadFragment(new NotificationsFragment());
                        return true;

                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        loadFragment(new SettingsFragment());
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
}