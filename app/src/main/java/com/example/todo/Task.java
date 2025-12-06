package com.example.todo;

public class Task {
    private String title;
    private String dueDate;

    public Task(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
