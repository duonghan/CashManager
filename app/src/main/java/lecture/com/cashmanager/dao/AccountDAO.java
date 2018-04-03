package lecture.com.cashmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import lecture.com.cashmanager.model.Account;

import static android.content.ContentValues.TAG;

public class AccountDAO extends SQLiteOpenHelper{

    private static final String DB_NAME = "cash_manager" ;
    private static final String TABLE_NAME = "user_account" ;
    private static final String ID = "id" ;
    private static final String USERNAME = "username" ;
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final int    VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE "+
                                                TABLE_NAME+ "(" +
                                                ID + "integer primary key autoincrement, "+
                                                USERNAME + " text, "+
                                                PASSWORD + " text, "+
                                                NAME + " text, "+
                                                EMAIL + " text)";

    private Context context;

    public AccountDAO(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;

        Log.d(TAG, "AccountDAO: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "On Create Database: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(TAG, "On Upgrade Database: ");
    }

    public void addAccount(Account account){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME, account.getUsername());
        values.put(PASSWORD, account.getPassword());
        values.put(NAME, account.getName());
        values.put(EMAIL, account.getEmail());

        database.insert(TABLE_NAME, null, values);
        database.close();

        Log.d(TAG, "addAccount: Successful ");
    }

    public Account getAccount(String username){

        Account account = new Account();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + "=" + username;
        SQLiteDatabase db = this.getWritableDatabase();
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

    public boolean isValid(String username, String password){
        String query =  "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + USERNAME + "=" + username +
                        " AND " + PASSWORD + " = " + password;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() == 0) return false;

        return true;
    }

}
