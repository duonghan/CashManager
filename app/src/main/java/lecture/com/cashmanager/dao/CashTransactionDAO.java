package lecture.com.cashmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import lecture.com.cashmanager.entity.CashInfo;
import lecture.com.cashmanager.model.CashTransaction;
import static android.content.ContentValues.TAG;

public class CashTransactionDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "cash_manager" ;
    private static final String TABLE_NAME = "cash_transaction" ;
    private static final String ID = "id" ;
    private static final String USERID = "userid" ;
    private static final String CATEGORYID = "categoryid" ;
    private static final String VALUE = "value";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";
    private static final String MODIFIED = "modified";
    private static final int    VERSION = 1;

    private static final String TABLE_CATEGORY_NAME = "cash_category" ;
    private static final String CATEGORY_NAME = "name" ;

    private static final String CREATE_TABLE = "CREATE TABLE "+
            TABLE_NAME+ "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            USERID + " INTEGER, "+
            CATEGORYID + " INTEGER, "+
            VALUE + " INTEGER, "+
            DESCRIPTION+ " TEXT, "+
            CREATED+ " TEXT, "+
            MODIFIED+ " TEXT) ";

    private Context context;

    public CashTransactionDAO(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;

        Log.d(TAG, "CashTransactionDAO: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "On Create Database: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(TAG, "On Upgrade Database: ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private int getCTCount() {
        Log.d(TAG, "get Cash Transaction Count.");
        int count = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        count = cursor.getCount();
        db.close();

        return count;
    }

    public void addCT(CashTransaction cashTransaction){
        Log.d(TAG, "Add cash transaction to database.");
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERID, cashTransaction.getUserid());
        values.put(CATEGORYID, cashTransaction.getCategoryid());
        values.put(VALUE, cashTransaction.getValue());
        values.put(DESCRIPTION, cashTransaction.getDescription());
        values.put(CREATED, cashTransaction.getCreated().toString());
        values.put(MODIFIED, cashTransaction.getModified().toString());

        if(database.insert(TABLE_NAME, null, values) != -1){
            Log.d(TAG, "add Cash Transaction: Successful ");
        }else{
            Log.d(TAG, "add Cash Transaction: Failed ");
        }

        database.close();
    }

    public CashTransaction getCT(int id){
        Log.d(TAG, "get Transaction with id = " + id);
        CashTransaction cashTransaction;

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        db.close();

        if(cursor.moveToFirst()){
            return new CashTransaction(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6));
        }
        db.close();
        return null;
    }

    public List<CashInfo> getCTMonthInfo(int month){
        List<CashInfo> cashTransactionList = new ArrayList<>();

        String query =  "SELECT ct." + VALUE + ",ct." + DESCRIPTION + ", cc." + CATEGORY_NAME + ", ct." + CREATED +
                        " FROM " + TABLE_NAME + " , " + TABLE_CATEGORY_NAME +
                        " WHERE " + CREATED + " LIKE " + "%/" + month + "/% AND ct." + CATEGORYID + " = cc." + ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                CashInfo cashInfo = new CashInfo();

                cashInfo.setValue(cursor.getInt(0));
                cashInfo.setCategory(cursor.getString(1));
                cashInfo.setDescription(cursor.getString(2));
                cashInfo.setDate(cursor.getString(3));

                cashTransactionList.add(cashInfo);
            }while (cursor.moveToNext());
        }

        db.close();
        return cashTransactionList;
    }

    public int updateCT(CashTransaction cashTransaction){
        Log.d(TAG, "update cash transaction " + cashTransaction.getId());
        int resullt = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERID, cashTransaction.getUserid());
        values.put(CATEGORYID, cashTransaction.getCategoryid());
        values.put(VALUE, cashTransaction.getValue());
        values.put(DESCRIPTION, cashTransaction.getDescription());
        values.put(CREATED, cashTransaction.getCreated());
        values.put(MODIFIED, cashTransaction.getModified());

        resullt = db.update(TABLE_NAME, values, ID + " =? ",
                new String[]{String.valueOf(cashTransaction.getId())});
        db.close();

        return resullt;
    }

    public void deleteCT(CashTransaction cashTransaction){
        Log.d(TAG, "delete cash transaction with id = " + cashTransaction.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " =? ",
                new String[] { String.valueOf(cashTransaction.getId())});
        db.close();
    }

}
