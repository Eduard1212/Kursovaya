package com.example.whatook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBANotifications {

    private DBHNotifications dbHelper;
    private SQLiteDatabase database;

    public DBANotifications(Context context){
        dbHelper = new DBHNotifications(context.getApplicationContext());
    }

    public DBANotifications open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DBHNotifications.COLUMN_ID, DBHNotifications.COLUMN_TEXT, DBHNotifications.COLUMN_DATE};
        return database.query(DBHNotifications.TABLE, columns, null, null, null, null, null);
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DBHLogs.TABLE);
    }

    public Notification getNotification(long id){
        Notification notification = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHNotifications.TABLE, DBHNotifications.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String text = cursor.getString(cursor.getColumnIndex(DBHNotifications.COLUMN_TEXT));
            String date = cursor.getString(cursor.getColumnIndex(DBHNotifications.COLUMN_DATE));
            notification = new Notification(id, text, date);
        }
        cursor.close();
        return notification;
    }

    public long insert(Notification notification){

        ContentValues cv = new ContentValues();
        cv.put(DBHNotifications.COLUMN_TEXT, notification.getText());
        cv.put(DBHNotifications.COLUMN_DATE, notification.getDate());

        return database.insert(DBHNotifications.TABLE, null, cv);
    }

    public long delete(long Id){

        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(Id)};
        return database.delete(DBHNotifications.TABLE, whereClause, whereArgs);
    }
}