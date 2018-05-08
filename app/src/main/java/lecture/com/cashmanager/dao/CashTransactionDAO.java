package lecture.com.cashmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lecture.com.cashmanager.model.CashTransaction;

import static android.content.ContentValues.TAG;

public class CashTransactionDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "cash_manager" ;
    private static final String TABLE_NAME = "cash_transaction" ;
    private static final String ID = "id" ;
    private static final String USERID = "userid" ;
    private static final String CATEGORYID = "categoryid" ;
    private static final String VALUE = "value";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String CREATED = "created";
    private static final String MODIFIED = "modified";
    private static final int    VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE "+
            TABLE_NAME+ "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            USERID + " INTEGER, "+
            CATEGORYID + " INTEGER, "+
            VALUE + " INTEGER, "+
            DESCRIPTION+ " TEXT, "+
            CREATED+ " TEXT, "+
            MODIFIED+ " TEXT, "+
            TYPE + " TEXT)";

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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "On Upgrade Database: ");
    }

    public void addCategory(CashTransaction cashTransaction){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERID, cashTransaction.getUserid());
        values.put(CATEGORYID, cashTransaction.getCategoryid());
        values.put(VALUE, cashTransaction.getValue());
        values.put(DESCRIPTION, cashTransaction.getDescription());
        values.put(CREATED, cashTransaction.getCreated().toString());
        values.put(MODIFIED, cashTransaction.getModified().toString());
        values.put(TYPE, cashTransaction.getType());

        database.insert(TABLE_NAME, null, values);
        database.close();

        Log.d(TAG, "addCategory: Successful ");
    }

    public List<CashTransaction> getCashTransaction(int id){
        List<CashTransaction> cashTransactionList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                CashTransaction cashTransaction = new CashTransaction();
                cashTransaction.setId(cursor.getInt(0));
                // TODO: add more information about cash transaction
                cashTransactionList.add(cashTransaction);
            }while (cursor.moveToNext());
        }

        db.close();
        return cashTransactionList;
    }

}
