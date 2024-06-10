package com.example.nutriappjava;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.nutriappjava.classes.DailyLogSummary;
import com.example.nutriappjava.classes.FoodItem;
import com.example.nutriappjava.classes.DailyLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint("Range")
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 25;

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
            "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "meal_type TEXT NOT NULL, " +
            "food_item_id INTEGER, " +
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

    public long insertFoodItem(FoodItem foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO food (name, calories, serving_size_g, fat_total_g, fat_saturated_g, protein_g, sodium_mg, potassium_mg, cholesterol_mg, carbohydrates_total_g, fiber_g, sugar_g) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement stmt = db.compileStatement(sql);

        try {
            // Debugging each field
            Log.d("DatabaseHelper", "Food item details - Name: " + foodItem.getName() + ", Calories: " + foodItem.getCalories() + ", Serving Size: " + foodItem.getServingSizeG() + ", Fat Total: " + foodItem.getFatTotalG() + ", Fat Saturated: " + foodItem.getFatSaturatedG() + ", Protein: " + foodItem.getProteinG() + ", Sodium: " + foodItem.getSodiumMg() + ", Potassium: " + foodItem.getPotassiumMg() + ", Cholesterol: " + foodItem.getCholesterolMg() + ", Carbohydrates: " + foodItem.getCarbohydratesTotalG() + ", Fiber: " + foodItem.getFiberG() + ", Sugar: " + foodItem.getSugarG());

            ContentValues cv = new ContentValues();
            cv.put("name", foodItem.getName());
            cv.put("calories", foodItem.getCalories());
            cv.put("serving_size_g", foodItem.getServingSizeG());
            cv.put("fat_total_g", foodItem.getFatTotalG());
            cv.put("fat_saturated_g", foodItem.getFatSaturatedG());
            cv.put("protein_g", foodItem.getProteinG());
            cv.put("sodium_mg", foodItem.getSodiumMg());
            cv.put("potassium_mg", foodItem.getPotassiumMg());
            cv.put("cholesterol_mg", foodItem.getCholesterolMg());
            cv.put("carbohydrates_total_g", foodItem.getCarbohydratesTotalG());
            cv.put("fiber_g", foodItem.getFiberG());
            cv.put("sugar_g", foodItem.getSugarG());

            long newRowId = db.insert("food", null, cv);
            db.close();

            return newRowId;
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Failed to insert food item: " + foodItem.getName(), e);
        } finally {
            stmt.clearBindings();
        }
        return 0;
    }

    public void printUniqueFoodItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM food", null); // Using DISTINCT to avoid duplicates

        if (cursor.moveToFirst()) {
            do {
                // Assuming the column names exactly match those used in the FoodItem constructor
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double calories = cursor.getDouble(cursor.getColumnIndex("calories"));
                double servingSizeG = cursor.getDouble(cursor.getColumnIndex("serving_size_g"));
                double fatTotalG = cursor.getDouble(cursor.getColumnIndex("fat_total_g"));
                double fatSaturatedG = cursor.getDouble(cursor.getColumnIndex("fat_saturated_g"));
                double proteinG = cursor.getDouble(cursor.getColumnIndex("protein_g"));
                int sodiumMg = cursor.getInt(cursor.getColumnIndex("sodium_mg"));
                int potassiumMg = cursor.getInt(cursor.getColumnIndex("potassium_mg"));
                int cholesterolMg = cursor.getInt(cursor.getColumnIndex("cholesterol_mg"));
                double carbohydratesTotalG = cursor.getDouble(cursor.getColumnIndex("carbohydrates_total_g"));
                double fiberG = cursor.getDouble(cursor.getColumnIndex("fiber_g"));
                double sugarG = cursor.getDouble(cursor.getColumnIndex("sugar_g"));

                FoodItem foodItem = new FoodItem(
                        id,
                        name,
                        calories,
                        servingSizeG,
                        fatTotalG,
                        fatSaturatedG,
                        proteinG,
                        sodiumMg,
                        potassiumMg,
                        cholesterolMg,
                        carbohydratesTotalG,
                        fiberG,
                        sugarG
                );

                System.out.println(foodItem.toString()); // Assuming you have overridden toString() in FoodItem class
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }


    public List<FoodItem> getAllFoods() {
        List<FoodItem> foodItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM food", null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double calories = cursor.getDouble(cursor.getColumnIndex("calories"));
                double servingSizeG = cursor.getDouble(cursor.getColumnIndex("serving_size_g"));
                double fatTotalG = cursor.getDouble(cursor.getColumnIndex("fat_total_g"));
                double fatSaturatedG = cursor.getDouble(cursor.getColumnIndex("fat_saturated_g"));
                double proteinG = cursor.getDouble(cursor.getColumnIndex("protein_g"));
                int sodiumMg = cursor.getInt(cursor.getColumnIndex("sodium_mg"));
                int potassiumMg = cursor.getInt(cursor.getColumnIndex("potassium_mg"));
                int cholesterolMg = cursor.getInt(cursor.getColumnIndex("cholesterol_mg"));
                double carbohydratesTotalG = cursor.getDouble(cursor.getColumnIndex("carbohydrates_total_g"));
                double fiberG = cursor.getDouble(cursor.getColumnIndex("fiber_g"));
                double sugarG = cursor.getDouble(cursor.getColumnIndex("sugar_g"));

                FoodItem foodItem = new FoodItem(
                        id,
                        name,
                        calories,
                        servingSizeG,
                        fatTotalG,
                        fatSaturatedG,
                        proteinG,
                        sodiumMg,
                        potassiumMg,
                        cholesterolMg,
                        carbohydratesTotalG,
                        fiberG,
                        sugarG
                );

                foodItems.add(foodItem);

                System.out.println(foodItem.toString()); // Assuming you have overridden toString() in FoodItem class
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return foodItems;
    }

    public long insertDailyLog(int userId, String date, String time, String mealType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("date", date);
        values.put("timestamp", time);
        values.put("meal_type", mealType);
        values.put("food_item_id", 0);
        long newRowId = db.insert("daily_log", null, values);
        db.close();
        return newRowId;
    }

    public int getFoodId(String foodName, Double foodServing, Double foodCalories) {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("SELECTING FOOD FROM TABLE");
        Cursor cursor = db.rawQuery("SELECT id FROM food WHERE name=? AND serving_size_g=? AND calories=?", new String[]{foodName, String.valueOf(foodServing), String.valueOf(foodCalories)});

        int foodId = -1;
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                foodId = cursor.getInt(cursor.getColumnIndex("id"));
                System.out.println("Food ID: " + foodId + " - " + foodName + " - " + foodServing + " - " + foodCalories);
            }
            cursor.close();
        }
        return foodId;
    }

    public void insertMealItem(int logId, int foodItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("log_id", logId);
        System.out.println("Daily Log ID: " + logId);
        values.put("food_item_id", foodItemId);
        System.out.println("Food Item ID: " + foodItemId);
        db.insert("meal_items", null, values);
        db.close();
    }

    public List<DailyLog> getDailyLogsForUser(int userId, String date) {
        printAllDailyLogsForUserAndDate(userId, date);
        List<DailyLog> dailyLogs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM daily_log WHERE user_id = ? AND date = ?";
        Cursor cursor = db.rawQuery(query, new String[] { String.valueOf(userId), date });

        if (cursor.moveToFirst()) {
            do {
                int logId = cursor.getInt(cursor.getColumnIndex("log_id"));
                String mealType = cursor.getString(cursor.getColumnIndex("meal_type"));
                String time = cursor.getString(cursor.getColumnIndex("timestamp"));

                DailyLog log = new DailyLog(logId, userId, date, time, mealType);
                dailyLogs.add(log);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return dailyLogs;
    }

    public void printAllDailyLogsForUserAndDate(int userId, String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM daily_log WHERE user_id = ? AND date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), date});

        if (cursor.moveToFirst()) {
            do {
                int logId = cursor.getInt(cursor.getColumnIndex("log_id"));
                String mealType = cursor.getString(cursor.getColumnIndex("meal_type"));

                System.out.println("Log ID: " + logId);
                List<FoodItem> foodItems = getFoodItemsForLogId(logId);
                System.out.println("FOODS ASSOCIATED TO LOG ID: " + logId);
                for (FoodItem foodItem : foodItems) {
                    System.out.println("Food Name: " + foodItem.getName());
                    System.out.println("Calories: " + foodItem.getCalories());
                }
                System.out.println("User ID: " + userId);
                System.out.println("Date: " + date);
                System.out.println("Meal Type: " + mealType);
                System.out.println();
            } while (cursor.moveToNext());
        } else {
            System.out.println("No daily logs found for user ID: " + userId + " and date: " + date);
        }
        cursor.close();
        db.close();
    }

    private List<FoodItem> getFoodItemsForLogId(int logId) {
        List<FoodItem> foodItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT f.* FROM food f JOIN meal_items mi ON f.id = mi.food_item_id WHERE mi.log_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(logId)});

        if (cursor.moveToFirst()) {
            do {

                FoodItem foodItem = new FoodItem(
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
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodItems;
    }

    public double getTotalCaloriesByLogId(int logId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("DatabaseHelper", "Calculating total calories for log ID: " + logId);

        String query = "SELECT SUM(f.calories) AS total_calories FROM daily_log dl JOIN meal_items mi ON dl.log_id = mi.log_id JOIN food f ON mi.food_item_id = f.id WHERE dl.log_id =?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(logId)});

        double totalCalories = 0;

        if (cursor.moveToFirst()) {
            totalCalories = cursor.getDouble(cursor.getColumnIndex("total_calories"));
            Log.d("DatabaseHelper", "Total calories for log ID " + logId + ": " + totalCalories);
        } else {
            Log.d("DatabaseHelper", "No calories found for log ID: " + logId);
        }

        cursor.close();
        db.close();

        return totalCalories;
    }

    public List<DailyLogSummary> getDailyLogSummaries(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("DatabaseHelper", "Fetching daily log summaries for user ID: " + userId);

        String query = "SELECT DATE(dl.date) AS date, COUNT(*) AS log_count, SUM(f.calories) AS total_calories " +
                "FROM daily_log dl JOIN meal_items mi ON dl.log_id = mi.log_id JOIN food f ON mi.food_item_id = f.id " +
                "WHERE dl.user_id =? AND strftime('%Y-%m', dl.date) = strftime('%Y-%m', CURRENT_DATE) " +
                "GROUP BY DATE(dl.date)";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        List<DailyLogSummary> summaries = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                DailyLogSummary summary = new DailyLogSummary(
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getInt(cursor.getColumnIndex("log_count")),
                        cursor.getDouble(cursor.getColumnIndex("total_calories"))
                );
                summaries.add(summary);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return summaries;
    }
    public double getTotalCalories(int currentUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String currentDate = getCurrentDate();

        String query = "SELECT SUM(food.calories) AS total_calories " +
                "FROM daily_log " +
                "JOIN meal_items ON daily_log.log_id = meal_items.log_id " +
                "JOIN food ON meal_items.food_item_id = food.id " +
                "WHERE daily_log.user_id =? AND daily_log.date =?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(currentUserId), currentDate});
        double totalCalories = 0;

        if (cursor!= null && cursor.moveToFirst()) {
            totalCalories = cursor.getDouble(cursor.getColumnIndex("total_calories"));
        }

        if (cursor!= null) {
            cursor.close();
        }

        db.close();

        return totalCalories;
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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

    public String getStoredSalt(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT salt FROM user WHERE id=?", new String[]{String.valueOf(userId)});
        String salt = "";
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                salt = cursor.getString(0);
            }
            cursor.close();
        }
        return salt;
    }

    public String getStoredHashedPassword(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT hashed_password FROM user WHERE id=?", new String[]{String.valueOf(userId)});
        String hashedPassword = "";
        if (cursor!= null) {
            if (cursor.moveToFirst()) {
                hashedPassword = cursor.getString(0);
            }
            cursor.close();
        }
        return hashedPassword;
    }

    public void updatePassword(int userId, String hashedNewPassword, String salt) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE user SET hashed_password=?, salt=? WHERE id=?", new String[]{hashedNewPassword, salt, String.valueOf(userId)});
    }

    public boolean hasLogForToday(int currentUserId, String currentDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daily_log WHERE user_id=? AND date=?", new String[]{String.valueOf(currentUserId), currentDate});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void purgeLogs(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM daily_log WHERE user_id=?", new String[]{String.valueOf(userId)});
        db.execSQL("DELETE FROM meal_items WHERE log_id IN (SELECT log_id FROM daily_log WHERE user_id=?)", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void deleteUserWithLogs(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM daily_log WHERE user_id=?", new String[]{String.valueOf(userId)});
            db.execSQL("DELETE FROM meal_items WHERE log_id IN (SELECT log_id FROM daily_log WHERE user_id=?)", new String[]{String.valueOf(userId)});
            db.execSQL("DELETE FROM users WHERE user_id=?", new String[]{String.valueOf(userId)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
