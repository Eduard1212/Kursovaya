package com.example.whatook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBAIngredients {

    private DBHIngredients dbHelper;
    private SQLiteDatabase database;

    public DBAIngredients(Context context){
        dbHelper = new DBHIngredients(context.getApplicationContext());
    }

    public DBAIngredients open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DBHIngredients.COLUMN_ID, DBHIngredients.COLUMN_MEASURE_ID, DBHIngredients.COLUMN_NAME};
        return  database.query(DBHIngredientMeasures.TABLE, columns, null, null, null, null, null);
    }

    public List<Ingredient> getIngredients(){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DBHIngredients.COLUMN_ID));
            int measureId = cursor.getInt(cursor.getColumnIndex(DBHIngredients.COLUMN_MEASURE_ID));
            String query = String.format("SELECT * FROM %s WHERE %s=?", DBHIngredientMeasures.TABLE, DBHIngredientMeasures.COLUMN_ID);
            Cursor nc = database.rawQuery(query, new String[]{ String.valueOf(measureId)});
            nc.moveToFirst();
            String measure = nc.getString(nc.getColumnIndex(DBHIngredientMeasures.COLUMN_NAME));
            nc.close();
            String name = cursor.getString(cursor.getColumnIndex(DBHIngredients.COLUMN_NAME));
            ingredients.add(new Ingredient(id, measureId, measure, name));
        }
        cursor.close();
        return ingredients;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DBHIngredients.TABLE);
    }

    public Ingredient getIngredient(long id){
        Ingredient ingredient = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DBHIngredients.TABLE, DBHIngredients.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DBHIngredients.COLUMN_NAME));
            int measureId = cursor.getInt(cursor.getColumnIndex(DBHIngredients.COLUMN_MEASURE_ID));
            String query2 = String.format("SELECT * FROM %s WHERE %s=?", DBHIngredientMeasures.TABLE, DBHIngredientMeasures.COLUMN_ID);
            Cursor nc = database.rawQuery(query2, new String[]{ String.valueOf(measureId)});
            nc.moveToFirst();
            String measure = nc.getString(nc.getColumnIndex(DBHIngredientMeasures.COLUMN_NAME));
            nc.close();
            ingredient = new Ingredient(id, measureId, measure, name);
        }
        cursor.close();
        return ingredient;
    }

    public long insert(Ingredient ingredient){

        ContentValues cv = new ContentValues();
        cv.put(DBHIngredients.COLUMN_MEASURE_ID, ingredient.getMeasureId());
        cv.put(DBHIngredients.COLUMN_NAME, ingredient.getName());

        return database.insert(DBHIngredients.TABLE, null, cv);
    }

    public long delete(long Id){

        String whereClause = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(Id)};
        return database.delete(DBHIngredients.TABLE, whereClause, whereArgs);
    }

    public long update(Ingredient ingredient){

        String whereClause = DBHIngredients.COLUMN_ID + "=" + String.valueOf(ingredient.getId());
        ContentValues cv = new ContentValues();
        cv.put(DBHIngredients.COLUMN_MEASURE_ID, ingredient.getMeasureId());
        cv.put(DBHIngredients.COLUMN_NAME, ingredient.getName());
        return database.update(DBHIngredientMeasures.TABLE, cv, whereClause, null);
    }
}