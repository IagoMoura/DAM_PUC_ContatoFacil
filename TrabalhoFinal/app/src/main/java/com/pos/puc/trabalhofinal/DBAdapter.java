package com.pos.puc.trabalhofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 58399 on 14/12/2015.
 */
public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_EMAIL = "email";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE_USERS3 = "users3";
    private static final String DATABASE_TABLE_CONTACTS3 = "contacts3";
    private static final int DATABASE_VERSION = 10;

    private static final String DATABASE_CREATE_USERS =
            "create table users3 (_id integer primary key autoincrement, "
                    + "name text not null, password text not null);";
    private static final String DATABASE_CREATE_CONTACTS =
                    "create table contacts3 (_id integer primary key autoincrement, " +
                    "email text not null, name text not null, telephone text not null);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE_USERS);
                db.execSQL(DATABASE_CREATE_CONTACTS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users3");
            db.execSQL("DROP TABLE IF EXISTS contacts3");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertUser(String name, String password)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PASSWORD, password);
        return db.insert(DATABASE_TABLE_USERS3, null, initialValues);
    }

    //---insert a contact into the database---
    public long insertContact(String name, String telephone, String email)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_TELEPHONE, telephone);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE_CONTACTS3, null, initialValues);
    }

    //---deletes a particular contact---
    public boolean deleteUser(long rowId)
    {
        return db.delete(DATABASE_TABLE_USERS3, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE_CONTACTS3, new String[] {KEY_ROWID, KEY_NAME,
                KEY_TELEPHONE, KEY_EMAIL}, null, null, null, null, null);
    }
    public Cursor getAllUsers()
    {
        return db.query(DATABASE_TABLE_USERS3, new String[] {KEY_ROWID, KEY_NAME,
                KEY_PASSWORD}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getUser(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE_USERS3, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_PASSWORD}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getUserByNamePassword(String name, String password) throws SQLException
    {
        Cursor mCursor =
                db.query(DATABASE_TABLE_USERS3, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_PASSWORD}, KEY_NAME + " = '" + name +"' AND " + KEY_PASSWORD + " = '" + password +"'", null,
                        null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateUser(long rowId, String name, String password)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_PASSWORD, password);
        return db.update(DATABASE_TABLE_USERS3, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
