package com.example.laba8;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_CONTACTS = "personaldatabase.db";
    public static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DB_CONTACTS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + DBContract.DBEntry.TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBContract.DBEntry.COLUMN_NAME_NAME + " TEXT," +
                DBContract.DBEntry.COLUMN_NAME_PHONE + " TEXT," +
                DBContract.DBEntry.COLUMN_NAME_CARD + " TEXT);");

        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, "Тест Тестов");
        values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, "8-999-000-00-00");
        values.put(DBContract.DBEntry.COLUMN_NAME_CARD, "0000 0000 0000 0000");
        db.insert(DBContract.DBEntry.TABLE_NAME, DBContract.DBEntry.COLUMN_NAME_NAME, values);

        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, "Тестовая строка");
        values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, "8-999-000-00-00");
        values.put(DBContract.DBEntry.COLUMN_NAME_CARD, "0000 0000 0000 0000");
        db.insert(DBContract.DBEntry.TABLE_NAME, DBContract.DBEntry.COLUMN_NAME_NAME, values);

        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, "test");
        values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, "9-999-000-00-00");
        values.put(DBContract.DBEntry.COLUMN_NAME_CARD,  "0000 0000 0000 0000");
        db.insert(DBContract.DBEntry.TABLE_NAME, DBContract.DBEntry.COLUMN_NAME_NAME, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " +
                DBContract.DBEntry.TABLE_NAME);
        onCreate(db);
    }
}
