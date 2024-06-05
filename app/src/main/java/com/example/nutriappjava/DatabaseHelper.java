package com.example.nutriappjava;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.nutriappjava.classes.Activityitem;
import com.example.nutriappjava.classes.Fooditem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 11;

    String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_username VARCHAR, " +
            "user_email VARCHAR, " +
            "user_password VARCHAR, " +
            "user_salt VARCHAR, " +
            "user_dob DATE, " +
            "user_gender INT, " +
            "user_height REAL, " +
            "user_weight REAL, " +
            "user_last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP);" +
            "";

    String CREATE_FOOD_TABLE = "CREATE TABLE " + "food" + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL," + "calories REAL," + "serving_size_g REAL,"
            + "fat_total_g REAL," + "fat_saturated_g REAL," + "protein_g REAL,"
            + "sodium_mg INTEGER," + "potassium_mg INTEGER," + "cholesterol_mg INTEGER,"
            + "carbohydrates_total_g REAL," + "fiber_g REAL," + "sugar_g REAL"
            + ")";

    String CREATE_ACTIVITIES_TABLE = "CREATE TABLE activities (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Name VARCHAR(255), " +
            "Description VARCHAR(255), " +
            "CaloriesPerHour INT, " +
            "DurationMinutes INT, " +
            "TotalCalories INT " +
            ");";

    String CREATE_DAILY_ACTIVITY_AND_INTAKE_TABLE = "CREATE TABLE daily_logs (" +
            " log_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " date DATE UNIQUE, " +
            " activity_id INTEGER, " +
            " activity_calories REAL, " +
            " activity_duration INTEGER, " +
            " food_id INTEGER, " +
            " food_calories REAL, " +
            " food_quantity INTEGER, " +
            " FOREIGN KEY (activity_id) REFERENCES activities(ID), " +
            " FOREIGN KEY (food_id) REFERENCES food(id) " +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public void clearTables(SQLiteDatabase db) {
        try{

            db.execSQL("DROP TABLE IF EXISTS users");
            db.execSQL("DROP TABLE IF EXISTS food");
            db.execSQL("DROP TABLE IF EXISTS activities");
            db.execSQL("DROP TABLE IF EXISTS daily_logs");

            db.execSQL(CREATE_FOOD_TABLE);
            db.execSQL(CREATE_ACTIVITIES_TABLE);
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_DAILY_ACTIVITY_AND_INTAKE_TABLE);
            onCreate(db);
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            System.out.println("TABLES CLEARED");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            if (!tableExists(db, "users")) {
                db.execSQL(CREATE_USER_TABLE);
            }
            if (!tableExists(db, "food")){
                db.execSQL(CREATE_FOOD_TABLE);
            }
            if (!tableExists(db, "activities")) {
                db.execSQL(CREATE_ACTIVITIES_TABLE);
            }
            if (!tableExists(db, "daily_logs")) {
                db.execSQL(CREATE_DAILY_ACTIVITY_AND_INTAKE_TABLE);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private boolean tableExists(SQLiteDatabase db, String tableName){
        Cursor cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        if (cursor != null && cursor.moveToFirst()){
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS food_diary_cal_eaten");
            db.execSQL("DROP TABLE IF EXISTS food_diary");
            db.execSQL("DROP TABLE IF EXISTS food");
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public DatabaseHelper open() {
        return this;
    }
    @Override
    public void close(){
    super.close();
    }
    @SuppressLint("Range")
    public void logTableStructure(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        if (cursor!= null) {
            while (cursor.moveToNext()) {
                String columnName = cursor.getString(cursor.getColumnIndex("name"));
                 String columnType = cursor.getString(cursor.getColumnIndex("type"));
                int notNull = cursor.getInt(cursor.getColumnIndex("notnull"));
                int primaryKey = cursor.getInt(cursor.getColumnIndex("pk"));
                System.out.println(columnName + ": " + columnType + " (NOT NULL: " + notNull + ", PK: " + primaryKey + ")");
            }
            cursor.close();
        }
    }

    public void insertFoodItem(Fooditem foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", foodItem.getName());
        values.put("calories", foodItem.getCalories());
        values.put("serving_size_g", foodItem.getServingSizeG());
        values.put("fat_total_g", foodItem.getFatTotalG());
        values.put("fat_saturated_g", foodItem.getFatSaturatedG());
        values.put("protein_g", foodItem.getProteinG());
        values.put("sodium_mg", foodItem.getSodiumMg());
        values.put("potassium_mg", foodItem.getPotassiumMg());
        values.put("cholesterol_mg", foodItem.getCholesterolMg());
        values.put("carbohydrates_total_g", foodItem.getCarbohydratesTotalG());
        values.put("fiber_g", foodItem.getFiberG());
        values.put("sugar_g", foodItem.getSugarG());

        long newRowId = db.insert("food", null, values);

        Log.d("DatabaseHelper", "Inserted food item: " + foodItem.getName() +
                        ", ROW ID: " + newRowId +
                        ", Calories: " + foodItem.getCalories() +
                        ", Serving Size g: " + foodItem.getServingSizeG()
        );

        db.close();
    }

    public void createDailyLogIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daily_logs WHERE date =?", new String[]{(String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime())});
        if (!cursor.moveToFirst()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("date", (String) DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()));
            contentValues.put("activity_id", -1);
            contentValues.put("activity_calories", 0);
            contentValues.put("activity_duration", 0);
            contentValues.put("food_id", -1);
            contentValues.put("food_calories", 0);
            contentValues.put("food_quantity", 0);
            db.insert("daily_logs", null, contentValues);
        }
        cursor.close();
    }


    public int countRows(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        int count = 0;
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }
}
