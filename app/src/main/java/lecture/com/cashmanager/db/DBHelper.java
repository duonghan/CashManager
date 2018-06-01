package lecture.com.cashmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.R;
import lecture.com.cashmanager.SHA1Hash;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.entity.CashInfoMonth;
import lecture.com.cashmanager.entity.OverviewInfo;
import lecture.com.cashmanager.model.Account;
import lecture.com.cashmanager.model.CashTransaction;
import lecture.com.cashmanager.model.Category;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DBHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cash_manager";

    // Table Names
    private static final String TABLE_ACCOUNT = "cash_account";
    private static final String TABLE_CATEGORY = "cash_category";
    private static final String TABLE_TRANSACTION = "cash_transaction";

    // Common column names
    private static final String ID = "id";
    private static final String NAME = "name";

    // Account Table - column names
    private static final String USERNAME = "username" ;
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    // Category Table - column names
    private static final String TYPE = "type";
    private static final String NAME_EN = "name_en";

    // Transaction Table - column names
    private static final String USERID = "userid" ;
    private static final String CATEGORYID = "categoryid" ;
    private static final String VALUE = "value";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";
    private static final String MODIFIED = "modified";

    // Table Create Statements
    // Account table create statement
    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE "+
                        TABLE_ACCOUNT+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        USERNAME + " TEXT, "+ PASSWORD + " TEXT, "+ NAME + " TEXT, "+
                        EMAIL + " TEXT)";

    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE "+
                        TABLE_CATEGORY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        NAME + " TEXT, "+ NAME_EN + " TEXT, "+TYPE + " INTEGER)"; // 1 as Income and -1 as Expense

    // Transaction table create statement
    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE "+
                        TABLE_TRANSACTION+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        USERID + " INTEGER, "+ CATEGORYID + " INTEGER, "+ VALUE + " INTEGER, "+
                        DESCRIPTION+ " TEXT, "+ CREATED+ " TEXT, "+ MODIFIED+ " TEXT) ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "On constructor... ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);

        // Create new tables
        onCreate(db);
    }

    // ------------------------ "ACCOUNT" table methods ----------------//

    /**
     * Add an account to DB
     * @param account
     */
    public void addAccount(Account account){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, account.getUsername());
        values.put(PASSWORD, account.getPassword());
        values.put(NAME, account.getName());
        values.put(EMAIL, account.getEmail());

        database.insert(TABLE_ACCOUNT, null, values);
        database.close();

        Log.d(TAG, "addAccount: Successful ");
    }

    /**
     * Get an account
     * @param username
     * @return
     */
    public Account getAccount(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Account account = new Account();

        String query = "SELECT * FROM " +
                TABLE_ACCOUNT + " WHERE " + USERNAME + " = \'" + username +
                "\'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            account.setId(cursor.getInt(0));
            account.setUsername(cursor.getString(1));
            account.setPassword(cursor.getString(2));
            account.setName(cursor.getString(3));
            account.setEmail(cursor.getString(4));
        }

        db.close();
        return account;
    }

    public boolean checkAccount(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " +
                TABLE_ACCOUNT + " WHERE " + USERNAME + " = \'" + username +
                "\' AND " + PASSWORD + " = \'" + SHA1Hash.SHA1(password) + "\'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    /**
     * Update an account
     * @param account
     * @return
     */
    public int updateAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, account.getName());
        values.put(PASSWORD, SHA1Hash.SHA1(account.getPassword()));
        values.put(EMAIL, account.getEmail());

        // Updating row
        int result = db.update(TABLE_ACCOUNT, values, ID + " =? ",
                new String[]{String.valueOf(account.getId())});
        db.close();

        return result;
    }

    /**
     * Delete an account
     * @param id
     */
    public void deleteAccount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, ID + " =? ",
                new String[] { String.valueOf(id)});
        db.close();
    }

    // ------------------------ "CATEGORY" table methods ----------------//

    /**
     * Add a category into db
     * @param category
     */
    public void addCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, category.getName());
        values.put(NAME_EN, category.getName());
        values.put(TYPE, category.getType());

        db.insert(TABLE_CATEGORY, null, values);

        db.close();
    }

    /**
     * Get number of category column
     */
    private int getCategoryCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORY;
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        db.close();

        return count;
    }

    /**
     * Auto add default category value into table if it is empty
     */
    public void createDefaultCategory(){
        int count = this.getCategoryCount();
        if(count == 0){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            String [] incomeEntriesEn = {"Award","Gifts","InterestMoney","Salary","Selling",
                    "Others","Debt", "Debt Collection"};

            String [] expenseEntriesEn = {"Bills and Utilities","Entertainment","Food and Beverage",
                    "Business","Gifts and Donations","Health and Fitness","Holidays","Family",
                    "Pets","Shopping","Educations","Friends and Lovers","Insurances","Investment",
                    "Transportation","Travel","Withdrawal","Fees and Charges","Repayment",
                    "Debt Collection", "Loan", "Repayment"};

            String [] incomeEntriesVi = {"Thưởng","Được tặng","Tiền lãi","Lương","Bán đồ","Đi vay", "Thu nợ", "Khác"};

            String [] expenseEntriesVi = {"Hóa đơn và Tiện ích","Giải trí","Ăn uống",
                    "Kinh doanh","Quà tăng và Từ thiện","Sức khỏe","Nghỉ lễ","Gia đình",
                    "Vật nuôi","Mua sắm","Giáo dục","Người yêu và Bạn bè","Bảo hiểm","Đầu tư",
                    "Di chuyển","Du lịch","Rút tiền","Chi phí","Trả nợ", "Cho vay"};

            int incomeLength = incomeEntriesVi.length;
            int expenseLength = expenseEntriesVi.length;

            // Add income entries to database
            for(int i = 0; i< incomeLength; i++){
                values.put(NAME, incomeEntriesVi[i]);
                values.put(NAME_EN, incomeEntriesEn[i]);
                values.put(TYPE, 1);
                db.insert(TABLE_CATEGORY, null, values);
            }

            // Add expense entries to database
            for(int i = 0; i< expenseLength; i++){
                values.put(NAME, expenseEntriesVi[i]);
                values.put(NAME_EN, expenseEntriesEn[i]);
                values.put(TYPE, -1);
                db.insert(TABLE_CATEGORY, null, values);
            }

            db.close();
        }
    }

    /**
     * Get category value by ID
     * @param id
     */
    public Category getCategory(int id, String lang){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            Category category = new Category();
            category.setId(cursor.getInt(0));

            if(!lang.equals("en"))
                category.setName(cursor.getString(1));  // vietnamese
            else
                category.setName(cursor.getString(2));  //english

            category.setType(cursor.getInt(3));
            return category;
        }
        db.close();

        return null;
    }

    /**
     * Get list of category by type
     * @param type
     * @return
     */
    public List<Category> getAllCategoryByType(int type, String lang){
        List<Category> lstCategory = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + TYPE + " = " + type;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Category category = new Category();
                category.setId(cursor.getInt(0));

                if(!lang.equals("en"))
                    category.setName(cursor.getString(1));  // vietnamese
                else
                    category.setName(cursor.getString(2));  //english

                category.setType(cursor.getInt(3));

                lstCategory.add(category);
            }while (cursor.moveToNext());
        }

        db.close();
        return lstCategory;
    }

    public int updateCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, category.getName());
        values.put(NAME_EN, category.getName());
        values.put(TYPE, category.getType());

        int resullt = db.update(TABLE_CATEGORY, values, ID + " =? ",
                new String[]{String.valueOf(category.getId())});
        db.close();

        return resullt;
    }

    public void deleteCategory(int id, boolean isDeleteCategoryTransaction){
        SQLiteDatabase db = this.getWritableDatabase();

        // Before deleting category
        // Check if transaction under this category should also be deleted
        if(isDeleteCategoryTransaction){
            // Get all transaction under this category
            List<CashTransaction> allCategoryTransaction = getAllTransactionByCategory(id);

            // Delete all transaction
            for (CashTransaction cashTransaction : allCategoryTransaction) {
                // Delete transaction
                deleteCT(cashTransaction.getId());
            }
        }
        db.delete(TABLE_CATEGORY, ID + " =? ",
                new String[] { String.valueOf(id)});
        db.close();
    }

    // ------------------------ "TRANSACTION" table methods ----------------//

    /**
     * Add transaction into db
     * @param cashTransaction
     */
    public void addCT(CashTransaction cashTransaction){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERID, cashTransaction.getUserid());
        values.put(CATEGORYID, cashTransaction.getCategoryid());
        values.put(VALUE, cashTransaction.getValue());
        values.put(DESCRIPTION, cashTransaction.getDescription());
        values.put(CREATED, cashTransaction.getCreated());
        values.put(MODIFIED, cashTransaction.getModified());

        database.insert(TABLE_TRANSACTION, null, values);
        database.close();
    }

    /**
     * Get number of transaction in db
     * @return
     */
    private int getCTCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTION;
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();
        db.close();

        return count;
    }

    /**
     * Get single transaction
     * @param id
     * @return
     */
    public CashTransaction getCT(int id){
        CashTransaction cashTransaction = new CashTransaction();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cashTransaction.setId(cursor.getInt(0));
            cashTransaction.setUserid(cursor.getInt(1));
            cashTransaction.setCategoryid(cursor.getInt(2));
            cashTransaction.setValue(cursor.getInt(3));
            cashTransaction.setDescription(cursor.getString(4));
            cashTransaction.setCreated(cursor.getString(5));
            cashTransaction.setModified(cursor.getString(6));
        }
        db.close();
        return null;
    }

    public List<CashTransaction> getAllTransactionByCategory(int categoryID){
        List<CashTransaction> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_TRANSACTION +
                " WHERE " + CATEGORYID + " = " + categoryID;
        Cursor cursor = db.rawQuery(query, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CashTransaction cashTransaction = new CashTransaction();
                cashTransaction.setId(cursor.getInt(0));
                cashTransaction.setUserid(cursor.getInt(1));
                cashTransaction.setCategoryid(cursor.getInt(2));
                cashTransaction.setValue(cursor.getInt(3));
                cashTransaction.setDescription(cursor.getString(4));
                cashTransaction.setCreated(cursor.getString(5));
                cashTransaction.setModified(cursor.getString(6));

                // Adding to transaction list
                list.add(cashTransaction);
            } while (cursor.moveToNext());
        }

        return list;
    }

    /**
     * Get transaction information by month
     * @param month
     * @return list of transaction in month
     */
    public CashInfoMonth getCTMonthInfo(int month){
        List<String> lstDay = new ArrayList<>();

        String strMonth = month < 10 ? "0" + month: month+"";

        String query =  "SELECT " + CREATED +
                " FROM " + TABLE_TRANSACTION + " WHERE strftime(\'%m\'," + CREATED + " ) = \"" + strMonth + "\" GROUP BY " + CREATED;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                lstDay.add(cursor.getString(0));
            }while (cursor.moveToNext());

            return new CashInfoMonth(lstDay);
        }

        db.close();
        return null;
    }

    public List<CashInfo> getCTDayInfo (String day){
        List<CashInfo> cashTransactionList = new ArrayList<>();

        String query =  "SELECT ct." + VALUE + ",ct." +
                DESCRIPTION + ", cc." + NAME + ", ct." + CREATED + ", cc." + TYPE +
                " FROM " + TABLE_TRANSACTION + " AS ct, " + TABLE_CATEGORY +
                " AS cc WHERE ct." + CREATED + " = \"" + day+ "\" AND ct." +
                CATEGORYID + " = cc." + ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                CashInfo cashInfo = new CashInfo();
                cashInfo.setValue(cursor.getInt(0));
                cashInfo.setDescription(cursor.getString(1));
                cashInfo.setCategory(cursor.getString(2));
                cashInfo.setDate(cursor.getString(3));
                cashInfo.setType(cursor.getInt(4));

                cashTransactionList.add(cashInfo);
            }while (cursor.moveToNext());
        }

        db.close();
        return cashTransactionList;
    }

    public OverviewInfo getOverviewInfoByMonth(int month){
        OverviewInfo overviewInfo = new OverviewInfo();
        int inflow = 0;
        int outflow = 0;

        String strMonth = month < 10 ? "0" + month: month+"";

        String query =  "SELECT ct." + VALUE + ", cc." + TYPE +
                " FROM " + TABLE_TRANSACTION + " AS ct, " + TABLE_CATEGORY +
                " AS cc WHERE strftime(\'%m\'," + CREATED + " ) = \"" + strMonth+ "\" AND ct." +
                CATEGORYID + " = cc." + ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int amount = cursor.getInt(0);
                int type = cursor.getInt(1);

                if(type == 1)
                    inflow += amount;
                else
                    outflow += amount;
            }while (cursor.moveToNext());
        }

        db.close();

        overviewInfo.setInflow(inflow);
        overviewInfo.setOutflow(outflow);
        overviewInfo.setSummary(inflow - outflow);
        return overviewInfo;
    }

    public int updateCT(CashTransaction cashTransaction){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERID, cashTransaction.getUserid());
        values.put(CATEGORYID, cashTransaction.getCategoryid());
        values.put(VALUE, cashTransaction.getValue());
        values.put(DESCRIPTION, cashTransaction.getDescription());
        values.put(CREATED, cashTransaction.getCreated());
        values.put(MODIFIED, cashTransaction.getModified());

        int result = db.update(TABLE_TRANSACTION, values, ID + " =? ",
                new String[]{String.valueOf(cashTransaction.getId())});
        db.close();

        return result;
    }

    public void deleteCT(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTION, ID + " =? ",
                new String[] { String.valueOf(id)});
        db.close();
    }

}
