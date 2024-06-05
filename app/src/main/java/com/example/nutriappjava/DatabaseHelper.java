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
import android.widget.Toast;

import com.example.nutriappjava.classes.foodItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 20;

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

    String CREATE_DAILY_LOG_TABLE = "CREATE TABLE IF NOT EXISTS daily_log (" +
            "log_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER, " +
            "date TEXT NOT NULL, " +
            "time TEXT NOT NULL, " +
            "meal_type TEXT NOT NULL, " +
            "food_item_id INTEGER, " +
            "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "FOREIGN KEY(user_id) REFERENCES users(user_id), " +
            "FOREIGN KEY(food_item_id) REFERENCES food(id)" +
            ");";

    String CREATE_MEAL_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS meal_items (" +
            "log_id INTEGER, " +
            "food_item_id INTEGER, " +
            "PRIMARY KEY(log_id, food_item_id)," +
            "FOREIGN KEY(log_id) REFERENCES daily_log(log_id)," +
            "FOREIGN KEY(food_item_id) REFERENCES food(id)" +
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
            db.execSQL("DROP TABLE IF EXISTS daily_log");
            db.execSQL("DROP TABLE IF EXISTS meal_items");

            db.execSQL(CREATE_FOOD_TABLE);
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_DAILY_LOG_TABLE);
            db.execSQL(CREATE_MEAL_ITEMS_TABLE);
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
            if (!tableExists(db, "daily_log")){
                db.execSQL(CREATE_DAILY_LOG_TABLE);
            }
            if (!tableExists(db, "meal_items")){
                db.execSQL(CREATE_MEAL_ITEMS_TABLE);
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

    public void insertFoodItem(foodItem foodItem) {
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
        try{
            long newRowId = db.insert("food", null, values);

            Log.d("DatabaseHelper", "Inserted food item: " + foodItem.getName() +
                    ", ROW ID: " + newRowId +
                    ", Calories: " + foodItem.getCalories() +
                    ", Serving Size g: " + foodItem.getServingSizeG()
            );
        } catch (SQLException e){
            Log.e("DatabaseHelper", "Error inserting food item: " + foodItem.getName(), e);
        } finally {
            db.close();
        }
    }

    public List<foodItem> getAllFoods() {
        List<foodItem> foodItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food", null);
        if (cursor!= null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") foodItem foodItem = new foodItem(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getFloat(cursor.getColumnIndex("calories")),
                        cursor.getFloat(cursor.getColumnIndex("serving_size_g")),
                        cursor.getFloat(cursor.getColumnIndex("fat_total_g")),
                        cursor.getFloat(cursor.getColumnIndex("fat_saturated_g")),
                        cursor.getFloat(cursor.getColumnIndex("protein_g")),
                        cursor.getInt(cursor.getColumnIndex("sodium_mg")),
                        cursor.getInt(cursor.getColumnIndex("potassium_mg")),
                        cursor.getInt(cursor.getColumnIndex("cholesterol_mg")),
                        cursor.getFloat(cursor.getColumnIndex("carbohydrates_total_g")),
                        cursor.getFloat(cursor.getColumnIndex("fiber_g")),
                        cursor.getFloat(cursor.getColumnIndex("sugar_g"))
                );
                foodItems.add(foodItem);
            }
            cursor.close();
        }
        return foodItems;
    }



    public List<foodItem> getAllMeals() {
        List<foodItem> meals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food", null);

        if (cursor!= null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") foodItem meal = new foodItem(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getFloat(cursor.getColumnIndex("calories")),
                        cursor.getFloat(cursor.getColumnIndex("serving_size_g")),
                        cursor.getFloat(cursor.getColumnIndex("fat_total_g")),
                        cursor.getFloat(cursor.getColumnIndex("fat_saturated_g")),
                        cursor.getFloat(cursor.getColumnIndex("protein_g")),
                        cursor.getInt(cursor.getColumnIndex("sodium_mg")),
                        cursor.getInt(cursor.getColumnIndex("potassium_mg")),
                        cursor.getInt(cursor.getColumnIndex("cholesterol_mg")),
                        cursor.getFloat(cursor.getColumnIndex("carbohydrates_total_g")),
                        cursor.getFloat(cursor.getColumnIndex("fiber_g")),
                        cursor.getFloat(cursor.getColumnIndex("sugar_g"))
                );
                meals.add(meal);

                // Log each meal item as it's created
                Log.d("MealItem", "Processing meal: " + meal.getName() + ", Row Index: " + cursor.getPosition());
                Log.d("MealItem", "Calories: " + meal.getCalories() + ", Serving Size: " + meal.getServingSizeG());
                Log.d("MealItem", "Added meal: " + meal.getName() + ", Calories: " + meal.getCalories());
            } while (cursor.moveToNext());
        }
        if (cursor!= null) {
            cursor.close();
        }
        return meals;
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
