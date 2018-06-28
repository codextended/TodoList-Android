package com.smathcadet.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.smathcadet.todolist.db.TaskProvider;
import com.smathcadet.todolist.model.Task;

import java.util.Date;
import java.util.GregorianCalendar;

public class TaskManagerActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private DatePicker datePicker;
    private CheckBox cbIsDone;

    private TaskProvider mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        Toolbar toolbar = findViewById(R.id.managerToolbar);
        setSupportActionBar(toolbar);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        datePicker = findViewById(R.id.datePicker);
        cbIsDone = findViewById(R.id.cbIsDone);
    }

    @Override
    protected void onPause() {
        if (mProvider != null) {
            mProvider.closeDatabase();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_header, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveMenu:
                saveTask();
                finish();
                return true;
            case R.id.cancelMenu:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTask() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day =datePicker.getDayOfMonth();
        Date date = new GregorianCalendar(year, month, day).getTime();
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        boolean done = cbIsDone.isChecked();

        Task task = new Task(title, description, date, done);
        mProvider = new TaskProvider(this);
        mProvider.addTask(task);
    }
}
