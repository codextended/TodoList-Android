package com.smathcadet.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.smathcadet.todolist.db.TaskProvider;
import com.smathcadet.todolist.model.Task;

import java.text.DateFormat;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvDate;
    private CheckBox cbIsDone;

    private Task mTask;
    private TaskProvider mProvider;
    private String extraString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvDate = findViewById(R.id.tvDate);
        cbIsDone = findViewById(R.id.cbIsDone);

//        id = getIntent().getIntExtra("id", 0);
        extraString = getIntent().getStringExtra("title");

        mProvider = new TaskProvider(this);
        mTask = mProvider.getTask(extraString);
        String stringDate = DateFormat.getDateInstance().format(mTask.getTaskDate());

        tvTitle.setText(mTask.getTitle());
        tvDescription.setText(mTask.getDescription());
        tvDate.setText(stringDate);
        cbIsDone.setChecked(mTask.isDone());
    }

    public static Intent newIntent(Context context, String title){
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("title", title);
        return intent;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editMenu:
                Intent intent = EditActivity.newIntent(this, extraString);
                startActivity(intent);
                return true;
            case R.id.deleteMenu:
                deleteTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteTask() {
        mProvider = new TaskProvider(this);
        mProvider.deleteTask(extraString);
        onBackPressed();
    }
}
