package com.smathcadet.todolist.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smathcadet.todolist.R;
import com.smathcadet.todolist.TaskDetailActivity;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> todoList;
    private Context mContext;

    public TaskAdapter(List<Task> todoList, Context context){
        this.todoList = todoList;
        this.mContext = context;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);
        return new TaskHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, final int position) {
        holder.tvTitle.setText(todoList.get(position).getTitle());
        Date date = todoList.get(position).getTaskDate();
        String stringDate = DateFormat.getDateInstance().format(date);
        holder.tvDate.setText(stringDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TaskDetailActivity.newIntent(mContext, todoList.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public TextView tvDate;

        public TaskHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
