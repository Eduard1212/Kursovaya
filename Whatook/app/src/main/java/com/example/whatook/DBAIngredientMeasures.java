package com.example.whatook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBAIngredientMeasures {

    private DBHIngredientMeasures dbHelper;
    private SQLiteDatabase database;

    public DBAIngredientMeasures(Context context){
        dbHelper = new DBHIngredientMeasures(context.getApplicationContext());
    }

    public DBAIngredientMeasures open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DBHIngredientMeasures.COLUMN_ID, DBHIngredientMeasures.COLUMN_NAME};
        return  database.query(DBHIngredientMeasures.TABLE, columns, null, null, null, null, null);
    }

    public List<IngredientMeasure> getIngredientMeasures(){
        ArrayList<IngredientMeasure> igs = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBHIngredientMeasures.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHIngredientMeasures.COLUMN_NAME));
            igs.add(new IngredientMeasure(id, name));
        }
        cursor.close();
        return igs;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DBHIngredientMeasures.TABLE);
    }

    public IngredientMeasure getIngredientMeasure(long id){
        IngredientMeasure ig = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DBHIngredientMeasures.TABLE, DBHIngredientMeasures.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DBHIngredientMeasures.COLUMN_NAME));
            ig = new IngredientMeasure(id, name);
        }
        cursor.close();
        return ig;
    }

    public long insert(IngredientMeasure ig){

        ContentValues cv = new ContentValues();
        cv.put(DBHIngredientMeasures.COLUMN_NAME, ig.getName());

        return database.insert(DBHIngredientMeasures.TABLE, null, cv);
    }

    public long delete(long Id){

        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(Id)};
        return database.delete(DBHIngredientMeasures.TABLE, whereClause, whereArgs);
    }

    public long update(IngredientMeasure ig){

        String whereClause = DBHIngredientMeasures.COLUMN_ID + "=" + String.valueOf(ig.getId());
        ContentValues cv = new ContentValues();
        cv.put(DBHIngredientMeasures.COLUMN_NAME, ig.getName());
        return database.update(DBHIngredientMeasures.TABLE, cv, whereClause, null);
    }
}