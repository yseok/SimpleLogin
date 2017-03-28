package com.yuseok.android.simplelogin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YS on 2017-03-28.
 */

class LoginDBHelper extends SQLiteOpenHelper {
    public LoginDBHelper(Context context) {
        super(context, "login", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table member(_id INTEGER PRIMARY KEY AUTOINCREMENT,memId TEXT,pwd TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists member");
        onCreate(db);
    }
}