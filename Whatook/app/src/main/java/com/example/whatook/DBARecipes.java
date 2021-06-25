package com.example.whatook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBARecipes {

    private DBHRecipes dbHelper;
    private SQLiteDatabase database;

    public DBARecipes(Context context){
        dbHelper = new DBHRecipes(context.getApplicationContext());
    }

    public DBARecipes open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DBHRecipes.COLUMN_ID, DBHRecipes.COLUMN_USER_ID, DBHRecipes.COLUMN_NAME, DBHRecipes.COLUMN_TIME_TO_COOK,
                DBHRecipes.COLUMN_TEXT, DBHRecipes.COLUMN_LIKES, DBHRecipes.COLUMN_USER_VISIBLE};
        return database.query(DBHRecipes.TABLE, columns, null, null, null, null, null);
    }

    public List<Recipe> getRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_USER_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHRecipes.COLUMN_NAME));
            int ttc = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_TIME_TO_COOK));
            String text = cursor.getString(cursor.getColumnIndex(DBHRecipes.COLUMN_TEXT));
            int likes = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_LIKES));
            int uv = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_USER_VISIBLE));
            String query = String.format("SELECT * FROM %s WHERE %s=?", DBHUsers.TABLE, DBHUsers.COLUMN_ID);
            Cursor nc = database.rawQuery(query, new String[]{ String.valueOf(userId)});
            nc.moveToFirst();
            String user = nc.getString(nc.getColumnIndex(DBHUsers.COLUMN_USERNAME));
            nc.close();
            recipes.add(new Recipe(id, userId, user, name, ttc, text, likes, uv));
        }
        cursor.close();
        return recipes;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DBHLogs.TABLE);
    }

    public Recipe getRecipe(long id){
        Recipe recipe = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHRecipes.TABLE, DBHRecipes.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            int userId = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_USER_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHRecipes.COLUMN_NAME));
            int ttc = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_TIME_TO_COOK));
            String text = cursor.getString(cursor.getColumnIndex(DBHRecipes.COLUMN_TEXT));
            int likes = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_LIKES));
            int uv = cursor.getInt(cursor.getColumnIndex(DBHRecipes.COLUMN_USER_VISIBLE));
            String query2 = String.format("SELECT * FROM %s WHERE %s=?", DBHUsers.TABLE, DBHUsers.COLUMN_ID);
            Cursor nc = database.rawQuery(query2, new String[]{ String.valueOf(userId)});
            nc.moveToFirst();
            String user = nc.getString(nc.getColumnIndex(DBHUsers.COLUMN_USERNAME));
            nc.close();
            recipe = new Recipe(id, userId, user, name, ttc, text, likes, uv);
        }
        cursor.close();
        return recipe;
    }

    public long insert(Recipe recipe){

        ContentValues cv = new ContentValues();
        cv.put(DBHRecipes.COLUMN_USER_ID, recipe.getUserId());
        cv.put(DBHRecipes.COLUMN_NAME, recipe.getName());
        cv.put(DBHRecipes.COLUMN_TIME_TO_COOK, recipe.getTTC());
        cv.put(DBHRecipes.COLUMN_TEXT, recipe.getText());
        cv.put(DBHRecipes.COLUMN_LIKES, recipe.getLikes());
        cv.put(DBHRecipes.COLUMN_USER_VISIBLE, recipe.getUV());

        return database.insert(DBHRecipes.TABLE, null, cv);
    }

    public long delete(long Id){

        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(Id)};
        return database.delete(DBHRecipes.TABLE, whereClause, whereArgs);
    }
}