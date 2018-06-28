package com.smathcadet.todolist;

import android.content.Context;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etDescription;
    private DatePicker datePicker;
    private CheckBox cbIsDone;

    private Task mTask;
    private TaskProvider mProvider;
    private String extraString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        datePicker = findViewById(R.id.datePicker);
        cbIsDone = findViewById(R.id.cbIsDone);

        extraString = getIntent().getStringExtra("title");

        mProvider = new TaskProvider(this);
        mTask = mProvider.getTask(extraString);

        Date date = mTask.getTaskDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        etTitle.setText(mTask.getTitle());
        etDescription.setText(mTask.getDescription());
        datePicker.init(year, month, day, null);
        cbIsDone.setChecked(mTask.isDone());

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
        mProvider.openDatabase();
    }

    public static Intent newIntent(Context context, String title){
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("title", title);
        return intent;
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
                goToMain();
                return true;
            case R.id.cancelMenu:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveTask() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        Date date = new GregorianCalendar(year, month, day).getTime();
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        boolean done = cbIsDone.isChecked();

        Task task = new Task(title, description, date, done);
        mProvider = new TaskProvider(this);
        mProvider.updateTask(mTask, task);
    }
}
