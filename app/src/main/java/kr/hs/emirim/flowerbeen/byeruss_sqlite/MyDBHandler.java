package kr.hs.emirim.flowerbeen.byeruss_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDBHandler {

    private final String TAG = "MyDBHandler";

    MySQLiteOpenHelper mySQLiteOpenHelper = null;
    SQLiteDatabase sqLiteDatabase = null;

    public MyDBHandler(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

//    public static MyDBHandler open(Context context, String name) {
//        return new MyDBHandler(context, name);
//    }

    public Cursor select()
    {
        sqLiteDatabase = mySQLiteOpenHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.query("byeruss_make_room", null, null, null, null, null, null);
        return c;
    }

    public void insert(String roomName, String roomTime, String roomPlace) {

        Log.d(TAG, "insert");

        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("roomName", roomName);
        value.put("roomTime", roomTime);
        value.put("roomPlace", roomPlace);

        sqLiteDatabase.insert("byeruss_make_room", null, value);

//        if(database != null){
//            String sql = "insert into customer(name, age, mobile) values(?,?,?)";
//            Object[] params = {name, age, mobile};
//
//            database.execSQL(sql, params);
//
//            println("데이터 추가함함");
//        }else{
//            println("먼저 데이터베이스를 오픈하세요");
//        }
    }

    public void delete(String name)
    {
        Log.d(TAG, "delete");
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete("byeruss_make_room", "roomName=?", new String[]{name});
    }

    public void close() {
        mySQLiteOpenHelper.close();
    }

}
