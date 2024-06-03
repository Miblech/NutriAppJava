package com.example.nutriappjava;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void clearFoodTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL("DROP TABLE IF EXISTS food");

            String CREATE_FOOD_TABLE = "CREATE TABLE " + "food" + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL," + "calories REAL," + "serving_size_g REAL,"
                    + "fat_total_g REAL," + "fat_saturated_g REAL," + "protein_g REAL,"
                    + "sodium_mg INTEGER," + "potassium_mg INTEGER," + "cholesterol_mg INTEGER,"
                    + "carbohydrates_total_g REAL," + "fiber_g REAL," + "sugar_g REAL"
                    + ")";

            db.execSQL(CREATE_FOOD_TABLE);
            onCreate(db);
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            if (!tableExists(db, "users")) {
                String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
                        "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "user_name VARCHAR, " +
                        "user_password VARCHAR), " +
                        "user_email VARCHAR, " +
                        "user_salt VARCHAR, " +
                        "user_dob DATE, " +
                        "user_gender INT, " +
                        "user_location VARCHAR, " +
                        "user_height REAL, " +
                        "user_weight REAL, " +
                        "user_target_weight REAL, " +
                        "user_activity_level INT, " +
                        "user_notes VARCHAR, " +
                        "user_last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP);" +
                        "";
                db.execSQL(CREATE_USER_TABLE);
            }

            if (!tableExists(db, "food_diary_cal_eaten")) {
                String CREATE_FOOD_CAL_EATEN = "CREATE TABLE IF NOT EXISTS food_diary_cal_eaten (" +
                        "cal_eaten_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "cal_eaten_date DATE, " +
                        "cal_eaten_meal_no REAL, " +
                        "cal_eaten_energy REAL, " +
                        "cal_eaten_proteins REAL, " +
                        "cal_eaten_carbs REAL, " +
                        "cal_eaten_fat REAL); ";
                        db.execSQL(CREATE_FOOD_CAL_EATEN);
            }

            if (!tableExists(db, "food_diary")) {
                String CREATE_FOOD_DIARY = "CREATE TABLE IF NOT EXISTS food_diary (" +
                        "fd_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "fd_date DATE, " +
                        "fd_meal_number INT, " +
                        "fd_food_id INT, " +
                        "fd_serving_size REAL, " +
                        "fd_serving_mesurment VARCHAR, " +
                        "fd_energy_calculated REAL, " +
                        "fd_protein_calculated REAL, " +
                        "fd_calories_calculated REAL, " +
                        "fd_fat_calculated REAL);";
                        db.execSQL(CREATE_FOOD_DIARY);
            }

            if (!tableExists(db, "food")){
                String CREATE_FOOD_TABLE = "CREATE TABLE " + "food" + "("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "name TEXT NOT NULL," + "calories REAL," + "serving_size_g REAL,"
                        + "fat_total_g REAL," + "fat_saturated_g REAL," + "protein_g REAL,"
                        + "sodium_mg INTEGER," + "potassium_mg INTEGER," + "cholesterol_mg INTEGER,"
                        + "carbohydrates_total_g REAL," + "fiber_g REAL," + "sugar_g REAL"
                        + ")";
                db.execSQL(CREATE_FOOD_TABLE);
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

    public void insertWrittenData(SQLiteDatabase db, String name, double calories, double servingSizeG,
                                  double fatTotalG, double fatSaturatedG, double proteinG,
                                  int sodiumMg, int potassiumMg, int cholesterolMg,
                                  double carbohydratesTotalG, double fiberG, double sugarG) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("calories", calories);
        contentValues.put("serving_size_g", servingSizeG);
        contentValues.put("fat_total_g", fatTotalG);
        contentValues.put("fat_saturated_g", fatSaturatedG);
        contentValues.put("protein_g", proteinG);
        contentValues.put("sodium_mg", sodiumMg);
        contentValues.put("potassium_mg", potassiumMg);
        contentValues.put("cholesterol_mg", cholesterolMg);
        contentValues.put("carbohydrates_total_g", carbohydratesTotalG);
        contentValues.put("fiber_g", fiberG);
        contentValues.put("sugar_g", sugarG);

        long newRowId = db.insert("food", null, contentValues);
        if (newRowId!= -1) {
            Log.d("DatabaseHelper", "Insertion successful");
        } else {
            Log.e("DatabaseHelper", "Insertion failed");
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

        long newRowId = db.insert("food", null, values);

        Log.d("DatabaseHelper", "Inserted food item: " + foodItem.getName() +
                        ", ROW ID: " + newRowId +
                        ", Calories: " + foodItem.getCalories() +
                        ", Serving Size g: " + foodItem.getServingSizeG()
        );

        db.close();
    }

    public int countRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM food", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}
