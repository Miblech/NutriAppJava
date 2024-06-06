package com.example.nutriappjava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.nutriappjava.classes.FoodItem;

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

    @SuppressLint("Range")
    public void insertFoodItem(FoodItem foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO food (name, calories, serving_size_g, fat_total_g, fat_saturated_g, protein_g, sodium_mg, potassium_mg, cholesterol_mg, carbohydrates_total_g, fiber_g, sugar_g) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement stmt = db.compileStatement(sql);

        try {
            // Debugging each field
            Log.d("DatabaseHelper", "Food item details - Name: " + foodItem.getName() + ", Calories: " + foodItem.getCalories() + ", Serving Size: " + foodItem.getServingSizeG() + ", Fat Total: " + foodItem.getFatTotalG() + ", Fat Saturated: " + foodItem.getFatSaturatedG() + ", Protein: " + foodItem.getProteinG() + ", Sodium: " + foodItem.getSodiumMg() + ", Potassium: " + foodItem.getPotassiumMg() + ", Cholesterol: " + foodItem.getCholesterolMg() + ", Carbohydrates: " + foodItem.getCarbohydratesTotalG() + ", Fiber: " + foodItem.getFiberG() + ", Sugar: " + foodItem.getSugarG());

            stmt.bindString(1, foodItem.getName());
            stmt.bindDouble(2, foodItem.getCalories());
            stmt.bindDouble(3, foodItem.getServingSizeG());
            stmt.bindDouble(4, foodItem.getFatTotalG());
            stmt.bindDouble(5, foodItem.getFatSaturatedG());
            stmt.bindDouble(6, foodItem.getProteinG());
            stmt.bindDouble(7, foodItem.getSodiumMg());
            stmt.bindDouble(8, foodItem.getPotassiumMg());
            stmt.bindDouble(9, foodItem.getCholesterolMg());
            stmt.bindDouble(10, foodItem.getCarbohydratesTotalG());
            stmt.bindDouble(11, foodItem.getFiberG());
            stmt.bindDouble(12, foodItem.getSugarG());

            stmt.executeInsert();
            Log.d("DatabaseHelper", "Successfully inserted food item: " + foodItem.getName());
            Cursor cursor = db.rawQuery("SELECT * FROM food WHERE name = ?", new String[]{foodItem.getName()});
            if (cursor.moveToFirst()) {
                do {
                    // Extraer y mostrar los datos para verificar
                    Log.d("DatabaseHelper", "Retrieved food item - Name: " + cursor.getString(cursor.getColumnIndex("name")) + ", Calories: " + cursor.getDouble(cursor.getColumnIndex("calories")) + ", Serving Size: " + cursor.getDouble(cursor.getColumnIndex("serving_size_g")) + ", Fat Total: " + cursor.getDouble(cursor.getColumnIndex("fat_total_g")) + ", Fat Saturated: " + cursor.getDouble(cursor.getColumnIndex("fat_saturated_g")) + ", Protein: " + cursor.getDouble(cursor.getColumnIndex("protein_g")) + ", Sodium: " + cursor.getDouble(cursor.getColumnIndex("sodium_mg")) + ", Potassium: " + cursor.getDouble(cursor.getColumnIndex("potassium_mg")) + ", Cholesterol: " + cursor.getDouble(cursor.getColumnIndex("cholesterol_mg")) + ", Carbohydrates: " + cursor.getDouble(cursor.getColumnIndex("carbohydrates_total_g")) + ", Fiber: " + cursor.getDouble(cursor.getColumnIndex("fiber_g")) + ", Sugar: " + cursor.getDouble(cursor.getColumnIndex("sugar_g")));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Failed to insert food item: " + foodItem.getName(), e);
        } finally {
            stmt.clearBindings();
        }
    }

    public List<FoodItem> getAllFoods() {
        List<FoodItem> FoodItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food", null);
        if (cursor!= null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") FoodItem foodItem = new FoodItem(
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
                FoodItems.add(foodItem);
            }
            cursor.close();
        }
        return FoodItems;
    }
    @SuppressLint("Range")
    public FoodItem getFoodItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        FoodItem foodItem = null;

        // Corrected SQL query to select by ID instead of name
        Cursor cursor = db.rawQuery("SELECT * FROM food WHERE id=?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            String foodName = cursor.getString(cursor.getColumnIndex("name"));
            double calories = cursor.getDouble(cursor.getColumnIndex("calories"));
            double servingSizeG = cursor.getDouble(cursor.getColumnIndex("serving_size_g"));
            double fatTotalG = cursor.getDouble(cursor.getColumnIndex("fat_total_g"));
            double fatSaturatedG = cursor.getDouble(cursor.getColumnIndex("fat_saturated_g"));
            double proteinG = cursor.getDouble(cursor.getColumnIndex("protein_g"));
            double sodiumMg = cursor.getDouble(cursor.getColumnIndex("sodium_mg"));
            double potassiumMg = cursor.getDouble(cursor.getColumnIndex("potassium_mg"));
            double cholesterolMg = cursor.getDouble(cursor.getColumnIndex("cholesterol_mg"));
            double carbohydratesTotalG = cursor.getDouble(cursor.getColumnIndex("carbohydrates_total_g"));
            double fiberG = cursor.getDouble(cursor.getColumnIndex("fiber_g"));
            double sugarG = cursor.getDouble(cursor.getColumnIndex("sugar_g"));
            Log.d("DatabaseHelper", "Retrieved food item - Name: " + foodName + ", Calories: " + calories + ", Serving Size: " + servingSizeG + ", Fat Total: " + fatTotalG + ", Fat Saturated: " + fatSaturatedG + ", Protein: " + proteinG + ", Sodium: " + sodiumMg + ", Potassium: " + potassiumMg + ", Cholesterol: " + cholesterolMg + ", Carbohydrates: " + carbohydratesTotalG + ", Fiber: " + fiberG + ", Sugar: " + sugarG);

            foodItem = new FoodItem(
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
            Log.d("FoodDetailFragment", "Food item displayed: " + foodItem.getName() + ", Calories: " + foodItem.getCalories() + ", Serving Size: " + foodItem.getServingSizeG());

        } else {
            Log.d("DatabaseHelper", "No food item found with name: " + id);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return foodItem;
    }

    public List<FoodItem> getAllMeals() {
        List<FoodItem> meals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food", null);

        if (cursor!= null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") FoodItem meal = new FoodItem(
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

    public List<FoodItem> SearchFoodsByName(String name) {
        List<FoodItem> foodItems = new ArrayList<>();
        System.out.println("Searching for " + name);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food WHERE name LIKE?", new String[]{"%" + name + "%"});
        if (cursor!= null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") FoodItem foodItem = new FoodItem(
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
                System.out.println(foodItem.getName());
                System.out.println(foodItem.getCalories());
                System.out.println(foodItem.getServingSizeG());
                System.out.println(foodItem.getFatTotalG());
                System.out.println(foodItem.getFatSaturatedG());
                System.out.println(foodItem.getProteinG());
                System.out.println(foodItem.getSodiumMg());
                System.out.println(foodItem.getPotassiumMg());
                System.out.println(foodItem.getCholesterolMg());
                System.out.println(foodItem.getCarbohydratesTotalG());
                System.out.println(foodItem.getFiberG());
                System.out.println(foodItem.getSugarG());
                foodItems.add(foodItem);
            }
            cursor.close();
        }
        return foodItems;
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
