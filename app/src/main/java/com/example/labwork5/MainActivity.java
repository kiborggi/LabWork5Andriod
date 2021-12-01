package com.example.labwork5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    EditText nameView;
    EditText emailView;
    TextView content;
    DbHelp dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = (EditText)  findViewById(R.id.name);
        emailView = (EditText)  findViewById(R.id.email);
        dbHelp = new DbHelp(this);
        content = (TextView)  findViewById(R.id.content);

        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor =  db.query("CONTACT",null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            int idCol = cursor.getColumnIndex("_id");
            int nameCol = cursor.getColumnIndex("name");
            int emailCol = cursor.getColumnIndex("email");
            int dateCol = cursor.getColumnIndex("date");

            do{
                String line = cursor.getInt(idCol) + " | " + cursor.getString(nameCol) + " | " + cursor.getString(emailCol) + " | " + cursor.getString(dateCol);

                content.setText(content.getText() + "\n" + line);


            }while (cursor.moveToNext());



        }

    }


    public void onClick(View view){
        ContentValues cv = new ContentValues();
        String name = nameView.getText().toString();
        String email = emailView.getText().toString();


        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        SQLiteDatabase db = dbHelp.getReadableDatabase();

        cv.put("name" , name);
        cv.put("email" , email);
        cv.put("date" , date.toString());
        db.insert("CONTACT",null,cv);

        content.setText("");

        Cursor cursor =  db.query("CONTACT",null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            int idCol = cursor.getColumnIndex("_id");
            int nameCol = cursor.getColumnIndex("name");
            int emailCol = cursor.getColumnIndex("email");
            int dateCol = cursor.getColumnIndex("date");

            do{
                String line = cursor.getInt(idCol) + " | " + cursor.getString(nameCol) + " | " + cursor.getString(emailCol) + " | " + cursor.getString(dateCol);

                content.setText(content.getText() + "\n" + line);


            }while (cursor.moveToNext());



        }


    }

    public void onButtonDetete (View view){
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        db.delete("CONTACT","_id > 0",null);
        content.setText("");


    }


    public class DbHelp extends SQLiteOpenHelper {

        public DbHelp(@Nullable Context context) {
            super(context, "DBmy", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE  CONTACT (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, date TEXT); ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS CONTACT " );

                    onCreate(db);

        }


    }
}