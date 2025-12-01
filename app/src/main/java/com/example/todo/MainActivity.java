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

        list = new ArrayList<>();
        adapter = new task_adapter(list);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ImageButton add_Task = findViewById(R.id.add_task);

        // 🔽 Add BottomNavigationView setup
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_tasks:
                        Toast.makeText(MainActivity.this, "Tasks", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_calendar:
                        Toast.makeText(MainActivity.this, "Calendar", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_notifications:
                        Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        // 🔼 End BottomNavigationView setup

        add_Task.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View task_dialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.task_add, null);

                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setView(task_dialog);

                EditText ask_task = task_dialog.findViewById(R.id.new_task);
                Button cancel = task_dialog.findViewById(R.id.c_t);
                Button add = task_dialog.findViewById(R.id.a_t);

                AlertDialog d = b.create();
                d.show();

                cancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        d.dismiss();
                    }
                });

                add.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String task = ask_task.getText().toString().trim();

                        if (!task.isEmpty())
                        {
                            list.add(task);
                            adapter.notifyItemInserted(list.size() - 1);
                            d.dismiss();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}