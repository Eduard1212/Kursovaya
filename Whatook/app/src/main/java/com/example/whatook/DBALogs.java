package com.example.whatook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBALogs {

    private DBHLogs dbHelper;
    private SQLiteDatabase database;

    public DBALogs(Context context){
        dbHelper = new DBHLogs(context.getApplicationContext());
    }

    public DBALogs open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DBHLogs.COLUMN_ID, DBHLogs.COLUMN_USER_ID, DBHLogs.COLUMN_ACTION_ID, DBHLogs.COLUMN_RECIPE_ID, DBHLogs.COLUMN_TIME};
        return database.query(DBHLogs.TABLE, columns, null, null, null, null, null);
    }

    public List<Log> getLogs(){
        ArrayList<Log> logs = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_USER_ID));
            int actionId = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_ACTION_ID));
            int recipeId = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_RECIPE_ID));
            String time = cursor.getString(cursor.getColumnIndex(DBHLogs.COLUMN_TIME));
            String query = String.format("SELECT * FROM %s WHERE %s=?", DBHUsers.TABLE, DBHUsers.COLUMN_ID);
            Cursor nc = database.rawQuery(query, new String[]{ String.valueOf(userId)});
            nc.moveToFirst();
            String user = nc.getString(nc.getColumnIndex(DBHUsers.COLUMN_USERNAME));
            nc.close();
            query = String.format("SELECT * FROM %s WHERE %s=?", DBHActions.TABLE, DBHActions.COLUMN_ID);
            nc = database.rawQuery(query, new String[]{ String.valueOf(actionId)});
            nc.moveToFirst();
            String action = nc.getString(nc.getColumnIndex(DBHActions.COLUMN_NAME));
            nc.close();
            query = String.format("SELECT * FROM %s WHERE %s=?", DBHRecipes.TABLE, DBHRecipes.COLUMN_ID);
            nc = database.rawQuery(query, new String[]{ String.valueOf(recipeId)});
            nc.moveToFirst();
            String recipe = nc.getString(nc.getColumnIndex(DBHRecipes.COLUMN_NAME));
            nc.close();
            logs.add(new Log(id, userId, actionId, recipeId, user, action, recipe, time));
        }
        cursor.close();
        return logs;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DBHLogs.TABLE);
    }

    public Log getLog(long id){
        Log log = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHLogs.TABLE, DBHLogs.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DBHIngredientMeasures.COLUMN_NAME));
            int userId = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_USER_ID));
            int actionId = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_ACTION_ID));
            int recipeId = cursor.getInt(cursor.getColumnIndex(DBHLogs.COLUMN_RECIPE_ID));
            String time = cursor.getString(cursor.getColumnIndex(DBHLogs.COLUMN_TIME));
            String query2 = String.format("SELECT * FROM %s WHERE %s=?", DBHUsers.TABLE, DBHUsers.COLUMN_ID);
            Cursor nc = database.rawQuery(query2, new String[]{ String.valueOf(userId)});
            nc.moveToFirst();
            String user = nc.getString(nc.getColumnIndex(DBHUsers.COLUMN_USERNAME));
            nc.close();
            query2 = String.format("SELECT * FROM %s WHERE %s=?", DBHActions.TABLE, DBHActions.COLUMN_ID);
            nc = database.rawQuery(query2, new String[]{ String.valueOf(actionId)});
            nc.moveToFirst();
            String action = nc.getString(nc.getColumnIndex(DBHActions.COLUMN_NAME));
            nc.close();
            query2 = String.format("SELECT * FROM %s WHERE %s=?", DBHRecipes.TABLE, DBHRecipes.COLUMN_ID);
            nc = database.rawQuery(query2, new String[]{ String.valueOf(recipeId)});
            nc.moveToFirst();
            String recipe = nc.getString(nc.getColumnIndex(DBHRecipes.COLUMN_NAME));
            nc.close();
            log = new Log(id, userId, actionId, recipeId, user, action, recipe, time);
        }
        cursor.close();
        return log;
    }

    public long insert(Log log){

        ContentValues cv = new ContentValues();
        cv.put(DBHLogs.COLUMN_USER_ID, log.getUserId());
        cv.put(DBHLogs.COLUMN_ACTION_ID, log.getActionId());
        cv.put(DBHLogs.COLUMN_RECIPE_ID, log.getRecipeId());
        cv.put(DBHLogs.COLUMN_TIME, log.getTime());

        return database.insert(DBHLogs.TABLE, null, cv);
    }

    public long delete(long Id){

        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(Id)};
        return database.delete(DBHLogs.TABLE, whereClause, whereArgs);
    }
}