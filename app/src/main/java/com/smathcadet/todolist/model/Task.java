package com.smathcadet.todolist.model;

import android.content.ContentValues;

import com.smathcadet.todolist.db.ContractTask;

import java.util.Date;

public class Task {
    private String title;
    private String description;
    private Date taskDate;
    private boolean done;

    public Task(){

    }

    public Task(String title, String description, Date date, boolean done){
        this.title = title;
        this.description = description;
        this.taskDate = date;
        this.done = done;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(ContractTask.COLUMN_TITLE, title);
        values.put(ContractTask.COLUMN_DESCRIPTION, description);
        values.put(ContractTask.COLUMN_DATE, taskDate.getTime());
        values.put(ContractTask.COLUMN_DONE, isDone()? 1 : 0);

        return values;
    }
}
