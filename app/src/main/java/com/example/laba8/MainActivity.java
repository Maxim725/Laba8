package com.example.laba8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button buttonDel, buttonClear, buttonShow, buttonAdd;
    EditText ET_name, ET_phone, ET_card;
    TextView text;
    DatabaseHelper DatabaseHelper;

    String input_name, input_card, input_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        buttonShow = findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(this);
        buttonDel = findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(this);
        buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);
        ET_name = findViewById(R.id.ET_name);
        ET_phone = findViewById(R.id.ET_phone);
        ET_card = findViewById(R.id.ET_card);
        text = findViewById(R.id.text);
        DatabaseHelper = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v)
    {
        input_name = ET_name.getText().toString();
        input_phone = ET_phone.getText().toString();
        input_card = ET_card.getText().toString();

        SQLiteDatabase db = DatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        int viewId = v.getId();

        if (viewId == R.id.buttonShow)
        {
            text.setText("");

            String[] projection = {
                    DBContract.DBEntry.COLUMN_NAME_NAME,
                    DBContract.DBEntry.COLUMN_NAME_PHONE,
                    DBContract.DBEntry.COLUMN_NAME_CARD
            };

            Cursor cursor = db.query(
                    DBContract.DBEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            int index_name =
                    cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_NAME);
            int index_phone =
                    cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_PHONE);
            int index_city =
                    cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_CARD);

            while (cursor.moveToNext()) {
                String value_name = cursor.getString(index_name);
                String value_phone = cursor.getString(index_phone);
                String value_city = cursor.getString(index_city);
                text.append("\n" + value_name + " \n " +
                        value_phone + " \n " + value_city + " \n ");
            }

            cursor.close();
            ClearEditTexts();
        }

        if (viewId == R.id.buttonAdd)
        {
            if (CheckEmpty())
            {
                DatabaseHelper.close();
                return;
            }

            values.put(DBContract.DBEntry.COLUMN_NAME_NAME,
                    input_name);
            values.put(DBContract.DBEntry.COLUMN_NAME_PHONE,
                    input_phone);
            values.put(DBContract.DBEntry.COLUMN_NAME_CARD,
                    input_card);
            db.insert(DBContract.DBEntry.TABLE_NAME, null, values);
            buttonShow.callOnClick();
        }

        if (viewId == R.id.buttonDel)
        {
            if (CheckEmpty())
            {
                DatabaseHelper.close();
                return;
            }


            String selection = DBContract.DBEntry.COLUMN_NAME_NAME +
                    "=?";
            String[] selectionArgs = {input_name};
            db.delete(DBContract.DBEntry.TABLE_NAME, selection,
                    selectionArgs);
            buttonShow.callOnClick();
        }

        if (viewId == R.id.buttonClear)
        {
            db.delete(DBContract.DBEntry.TABLE_NAME, null, null);
            buttonShow.callOnClick();
        }

        DatabaseHelper.close();
    }

    private void ClearEditTexts()
    {
        ET_name.setText("");
        ET_phone.setText("");
        ET_card.setText("");
    }

    private boolean CheckEmpty()
    {
        if (TextUtils.isEmpty(input_name) || TextUtils.isEmpty(input_phone) || TextUtils.isEmpty(input_card))
        {
            text.setText("Неправильна заполнены данные");
            return true;
        }

        if (!TextUtils.isDigitsOnly(input_phone))
        {
            text.setText("В поле Номер: Не верное число");
            return true;
        }

        return false;
    }
}

