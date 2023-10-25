package com.example.sqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlitedatabase.DataBase.MyDataBaseHelper;

public class MainActivity extends AppCompatActivity {

    //variable declaring
    MyDataBaseHelper myDataBaseHelper;

    EditText studentName, studentAge, studentGender, studentID;
    Button addData, showData, updateData, deleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find id
        studentName = findViewById(R.id.studentName);
        studentAge = findViewById(R.id.studentAge);
        studentGender = findViewById(R.id.studentGender);
        studentID = findViewById(R.id.studentID);
        addData = findViewById(R.id.addData);
        showData = findViewById(R.id.showData);
        updateData = findViewById(R.id.updateData);
        deleteData = findViewById(R.id.deleteData);

        //database write
        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        //add data in database
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edit text data get
                String name = studentName.getText().toString();
                studentName.getText().clear();
                String age = studentAge.getText().toString();
                studentAge.getText().clear();
                String gender = studentGender.getText().toString();
                studentGender.getText().clear();
                String id = studentID.getText().toString();
                studentID.getText().clear();

                long rowId = myDataBaseHelper.insertData(name, age, gender);
                if(rowId == -1){
                    Toast.makeText(MainActivity.this, "Row not successfully inserted...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Row "+rowId+" successfully inserted...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //show data from database
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDataBaseHelper.displayData();

                if(cursor.getCount() == 0){
                    //there is no data so display message
                    displayData("Error", "No data found");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                    stringBuffer.append("Name: "+cursor.getString(1)+"\n");
                    stringBuffer.append("Age: "+cursor.getString(2)+"\n");
                    stringBuffer.append("Gender: "+cursor.getString(3)+"\n\n");
                }

                displayData("DataSet", stringBuffer.toString());
            }
        });

        //update data in database
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edit text data get
                String name = studentName.getText().toString();
                studentName.getText().clear();
                String age = studentAge.getText().toString();
                studentAge.getText().clear();
                String gender = studentGender.getText().toString();
                studentGender.getText().clear();
                String id = studentID.getText().toString();
                studentID.getText().clear();

                boolean isUpdated = myDataBaseHelper.updateData(id, name, age, gender);
                if (isUpdated == true){
                    Toast.makeText(MainActivity.this, "Update is successfully...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Update not successfully...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //delete data in database
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = studentID.getText().toString();
                studentID.getText().clear();

                int value = myDataBaseHelper.deleteData(id);
                if(value > 0){
                    Toast.makeText(MainActivity.this, "Data is deleted...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data is not deleted...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //database data display in alert dialog
    public void displayData(String title, String dataSet){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(dataSet);
        builder.setCancelable(true);
        builder.show();
    }
}