package com.example.myapplication;

import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.database.Cursor;
import android.app.Activity;

public class MainActivity extends AppCompatActivity {
    String databaseName = "db";
    String tableName = "mode";
    TextView status;
    boolean databaseCreated = false;
    boolean tableCreated = false;
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    Button bttn_option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase(databaseName);
        createTable(tableName);
        int count = insertRecord(tableName);
        executeRawQuery();
        executeRawQueryParam();

        bttn_option = (Button) findViewById(R.id.bttn_option);

        bttn_option.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity.this, OptionActivity.class);

                startActivity(intent1);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                finish();




            }

        });


    }

    private void createDatabase(String name) {

        try {
            db = openOrCreateDatabase(
                    name,
                    Activity.MODE_PRIVATE,
                    null);

            databaseCreated = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createTable(String name) {

        db.execSQL("create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " modename text, "
                + " emertime text, "
                + " emercount integer, "
                + " favorites integer, "
                + " nonfavorites integer, "
                + " unknown integer);" );
        tableCreated = true;
    }

    private int insertRecord(String name) {

        int count = 1;
        db.execSQL( "insert into " + name + "(modename, emertime, emercount, favorites, nonfavorites, unknown) values ('Ring', '2007:05:03:12:36', 2, 1, 2, 1);" );

        return count;
    }
    private void executeRawQuery() {

        Cursor c1 = db.rawQuery("select count(*) as Total from " + tableName, null);
//        println("cursor count : " + c1.getCount());

        c1.moveToNext();
//        println("record count : " + c1.getInt(0));

        c1.close();

    }
    private void executeRawQueryParam() {

        String SQL = "select modename, emertime, emercount, favorites, nonfavorites, unknown "
                + " from " +tableName;
        String[] args= {};

        Cursor c1 = db.rawQuery(SQL, args);
        int recordCount = c1.getCount();


        for (int i = 0; i < recordCount; i++) {
            c1.moveToNext();
            String name = c1.getString(0);
            int age = c1.getInt(1);
            String phone = c1.getString(2);


        }

        c1.close();
    }

    private void executeRawQueryParam2() {

        String[] columns = {"modename", "emertime", "emercount", "favorites", "nonfavorites", "unknown"};
        String whereStr = ""; // 질문
        String[] whereParams = {};
        Cursor c1 = db.query(TABLE_NAME, columns,
                whereStr, whereParams,
                null, null, null);

        int recordCount = c1.getCount();
//        println("cursor count : " + recordCount + "\n");

        for (int i = 0; i < recordCount; i++) {
            c1.moveToNext();
            String name = c1.getString(0);
            int age = c1.getInt(1);
            String phone = c1.getString(2);

        }

        c1.close();

    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, "MODE", null, DATABASE_VERSION);
        }


        public void onCreate(SQLiteDatabase db) {
//        println("creating table [" + TABLE_NAME + "].");

            try {
                String DROP_SQL = "drop table if exists " + tableName;
                db.execSQL(DROP_SQL);
            } catch (Exception ex) {
//                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "create table " + tableName + "("
                    + " _id integer PRIMARY KEY autoincrement, "
                    + " modename text, "
                    + " emertime text, "
                    + " emercount integer, "
                    + " favorites integer, "
                    + " nonfavorites integer, "
                    + " unknown integer);" ;

            try {
                db.execSQL(CREATE_SQL);
            } catch (Exception ex) {
//                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

//            println("inserting records.");

            try {
//                db.execSQL("insert into " + tableName + "(name, age, phone) values ('John', 20, '010-7788-1234');");
            } catch (Exception ex) {
//                Log.e(TAG, "Exception in insert SQL", ex);
            }

        }

        public void onOpen(SQLiteDatabase db) {

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }


}





