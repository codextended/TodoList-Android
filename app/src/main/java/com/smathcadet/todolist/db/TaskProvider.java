package com.smathcadet.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smathcadet.todolist.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo de Marcus on 6/24/2018.
 */

public class TaskProvider {

    private Context mContext;
    private SQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;

    public TaskProvider(Context context) {
        mContext = context;
        mOpenHelper = new DBHelper(mContext);
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void openDatabase(){
        mDatabase = mOpenHelper.getWritableDatabase();
    }

    public void closeDatabase(){
        mOpenHelper.close();
    }

    public void addTask(Task task){
        ContentValues values = task.toValues();
        mDatabase.insert(ContractTask.TABLE_NAME, null, values);
    }

    public Task getTask(String title){
        Task task = new Task();
        Cursor cursor = mDatabase.query(ContractTask.TABLE_NAME, ContractTask.ALL_COLUNMS, ContractTask.COLUMN_TITLE + " = ?", new String[]{title}, null, null, null);
        while (cursor.moveToNext()) {
            task.setTitle(cursor.getString(cursor.getColumnIndex(ContractTask.COLUMN_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(ContractTask.COLUMN_DESCRIPTION)));
            task.setTaskDate(new Date(cursor.getLong(cursor.getColumnIndex(ContractTask.COLUMN_DATE))));
            task.setDone(cursor.getInt(cursor.getColumnIndex(ContractTask.COLUMN_DONE)) == 1 ? true : false);
        }
        cursor.close();
        return task;
    }

    public void deleteTask(String title){
        mDatabase.delete(ContractTask.TABLE_NAME, ContractTask.COLUMN_TITLE + " = ?", new String[]{title});
    }

    public void updateTask(Task oldTask, Task newTask){
        String title = oldTask.getTitle();
        ContentValues values = newTask.toValues();

        mDatabase.update(ContractTask.TABLE_NAME, values, ContractTask.COLUMN_TITLE + " = ?", new String[]{title});

    }

    public List<Task> getAllTasks(){
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = mDatabase.query(ContractTask.TABLE_NAME, ContractTask.ALL_COLUNMS,
                null, null, null, null, null);

        while (cursor.moveToNext()){
            Task task = new Task();

            task.setTitle(cursor.getString(cursor.getColumnIndex(ContractTask.COLUMN_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(ContractTask.COLUMN_DESCRIPTION)));
            task.setTaskDate(new Date(cursor.getLong(cursor.getColumnIndex(ContractTask.COLUMN_DATE))));
            task.setDone(cursor.getInt(cursor.getColumnIndex(ContractTask.COLUMN_DONE)) == 1 ? true : false);
            tasks.add(task);
        }
        cursor.close();

        return tasks;
    }
}
