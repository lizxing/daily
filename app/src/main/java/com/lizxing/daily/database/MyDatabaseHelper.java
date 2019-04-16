package com.lizxing.daily.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabaseHelper";
    public static final String CREATE_NEWS = "create table News ("
            + "id integer primary key autoincrement, "
            + "time text, "
            + "title text unique, "
            + "description text, "
            + "picUrl text, "
            + "url text, "
            + "type int)";
    public static final String CREATE_Articles = "create table Articles ("
            + "id integer primary key autoincrement, "
            + "time text, "
            + "title text unique, "
            + "description text, "
            + "picUrl text, "
            + "url text)";


    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
        db.execSQL(CREATE_Articles);
        Log.d(TAG, "创建数据库");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
