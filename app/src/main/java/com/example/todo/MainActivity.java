package com.example.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

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