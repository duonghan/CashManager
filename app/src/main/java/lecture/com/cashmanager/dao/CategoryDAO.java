package lecture.com.cashmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import lecture.com.cashmanager.model.Category;
import static android.content.ContentValues.TAG;

public class CategoryDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "cash_manager" ;
    private static final String TABLE_NAME = "cash_category" ;
    private static final String ID = "id" ;
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final int    VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE "+
                                                TABLE_NAME+ "(" +
                                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                NAME + " TEXT, "+
                                                TYPE + " INTEGER)"; // 1 as Income and -1 as Expense

    private Context context;

    public CategoryDAO(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;

        Log.d(TAG, ">>>CategoryDAO: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "On Create Category Database: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(TAG, "On Upgrade Database: ");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createDefaultCategory(){
        int count = this.getCategoryCount();
        if(count == 0){

            String [] incomeEntries = {"Award","Gifts","InterestMoney","Salary","Selling","Others"};
            String [] expenseEntries = {"Bills and Utilities","Entertainment","Food and Beverage","Business"
                ,"Gifts and Donations","Health and Fitness","Holidays","Family","Pets","Shopping","Educations"
                ,"Friends and Lovers","Insurances","Investment","Transportation","Travel"
                ,"Withdrawal","Fees and Charges","Repayment","Debt Collection"};

            // Add income entries to database
            for (String entry: incomeEntries) {
                this.addCategory(new Category(entry, 1));
            }

            // Add expense entries to database
            for (String entry: incomeEntries) {
                this.addCategory(new Category(entry, -1));
            }
        }
    }

    private int getCategoryCount() {
        Log.d(TAG, "get Category Count.");
        int count = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        count = cursor.getCount();
        db.close();

        return count;
    }

    public void addCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, category.getName());
        values.put(TYPE, category.getType());

        if(db.insert(TABLE_NAME, null, values) != -1){
            Log.d(TAG, "addCategory: Successful ");
            Log.d(TAG, "Name: " + category.getName());
            Log.d(TAG, "Type: " + category.getType());
        }else{
            Log.d(TAG, "addCategory: Failed ");
        }

        db.close();
    }

    public Category getCategory(int id){
        Log.d(TAG, "get Category " + id);

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.moveToFirst()){
            return new Category(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getInt(2));
        }
        db.close();

        Log.d(TAG, "get Category: category is not found with id: " + id);
        return null;
    }

    public List<Category> getAllCategoryByType(int type){
        Log.d(TAG, "get All Category with type is " + type);
        List<Category> lstCategory = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TYPE + " = " + type;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setType(cursor.getInt(2));

                lstCategory.add(category);
            }while (cursor.moveToNext());
        }

        db.close();
        return lstCategory;
    }

    public List<String> getAllStringCategory(int type){
        List<String> lstCategory = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TYPE + " = " + type;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                lstCategory.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        db.close();
        return lstCategory;
    }

    public int updateCategory(Category category){
        Log.d(TAG, "update category " + category.getName());
        int resullt = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, category.getName());
        values.put(TYPE, category.getType());

        resullt = db.update(TABLE_NAME, values, ID + " =? ",
                new String[]{String.valueOf(category.getId())});
        db.close();

        return resullt;
    }

    public void deleteCategory(Category category){
        Log.d(TAG, "delete category " + category.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " =? ",
                new String[] { String.valueOf(category.getId())});
        db.close();
    }
}
