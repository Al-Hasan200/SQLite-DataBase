package com.example.sqlitedatabase.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    //variable declaring
    private Context context;
    private static final String database_name = "students";
    private static final int database_version = 2;
    private static final String database_table_name = "students_info";
    private static final String column_name_id = "_Id";
    private static final String column_name_name = "Name";
    private static final String column_name_age = "Age";
    private static final String column_name_gender = "Gender";
    private static final String create_table = "CREATE TABLE "+database_table_name+" ("+column_name_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+column_name_name+" VARCHAR(255), "+column_name_age+" INTEGER, "+column_name_gender+" VARCHAR(15));";
    private static final String drop_table = "DROP TABLE IF EXISTS "+database_table_name;
    private static final String select_table = "SELECT * FROM "+database_table_name;
    public MyDataBaseHelper(@Nullable Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            Toast.makeText(context, "onCreate is Called...", Toast.LENGTH_SHORT).show();
            db.execSQL(create_table);
        }catch (Exception exception){
            Toast.makeText(context, "Exception: "+exception, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            Toast.makeText(context, "onUpgrade is Called...", Toast.LENGTH_SHORT).show();
            db.execSQL(drop_table);
            onCreate(db);
        }catch (Exception exception){
            Toast.makeText(context, "Exception: "+exception, Toast.LENGTH_SHORT).show();
        }
    }

    //insert data in database
    public long insertData(String name, String age, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name_name, name);
        contentValues.put(column_name_age, age);
        contentValues.put(column_name_gender, gender);

        long rowId = sqLiteDatabase.insert(database_table_name, null, contentValues);
        return rowId;
    }

    //display data
    public Cursor displayData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(select_table, null);
        return cursor;
    }

    //update data in database
    public boolean updateData(String id, String name, String age, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name_id, id);
        contentValues.put(column_name_name, name);
        contentValues.put(column_name_age, age);
        contentValues.put(column_name_gender, gender);

        sqLiteDatabase.update(database_table_name, contentValues, column_name_id+" = ?", new String[]{id});
        return true;
    }

    //delete data in database
    public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(database_table_name,column_name_id+" = ?", new String[]{id});
    }
}
