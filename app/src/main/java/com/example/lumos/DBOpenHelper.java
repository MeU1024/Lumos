package com.example.lumos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建数据库sql语句并执行
        String construct_habit="create table habit(id integer primary key autoincrement,name varchar(20),days integer,des varchar(40), cday date,lday date,sday date,state int)";
        String construct_record="create table record(id integer primary key autoincrement,today date,allhabit integer, done integer)";
        db.execSQL(construct_habit);
        db.execSQL(construct_record);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}