package com.example.freedom.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by freedom on 2017/12/10.
 */

public class myDB extends SQLiteOpenHelper{
    private static final String DB_NAME = "contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public myDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){ //创建数据库
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY,name TEXT,birth TEXT,gift TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        //本次实验用不到，但必须重写这个函数才能实例化
    }

    public void insert(String name, String birth, String gift){//插入数据
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birth", birth);
        cv.put("gift", gift);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void update(String name, String birth, String gift){//更新数据
        SQLiteDatabase db = getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put("birth",birth);
        updateValues.put("gift",gift);
        db.update(TABLE_NAME, updateValues,"name="+"'"+name+"'",null);
        db.close();
    }

    public void delete(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"name="+"'"+name+"'",null);
        db.close();
    }

    public Cursor getAll() {//查询表中所有信息
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"_id", "name", "birth", "gift"}, null, null, null, null, null);
        return cursor;
    }

    public boolean query(String name) {//通过名字查询是否存在相关记录
        boolean In = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"_id", "name", "birth", "gift"}, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() >= 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                if (cursor.getString(1).equals(name)) {
                    In = true;
                    break;
                }
                cursor.moveToNext();
            }
        }
        db.close();
        return In;
    }
}
