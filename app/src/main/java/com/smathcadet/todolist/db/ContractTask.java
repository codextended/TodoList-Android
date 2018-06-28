package com.smathcadet.todolist.db;

import android.provider.BaseColumns;

/**
 * Created by Smath Cadet on 6/24/2018.
 */

public class ContractTask{
    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DONE = "done";

    public static final String[] ALL_COLUNMS = {COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_DONE};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + ", " + COLUMN_DESCRIPTION + "," +
            COLUMN_DATE + ", " + COLUMN_DONE + ")";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;

}
