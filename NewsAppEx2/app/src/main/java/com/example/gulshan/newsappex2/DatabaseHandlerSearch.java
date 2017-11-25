package com.example.gulshan.newsappex2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sada on 1/31/2017.
 */
public class DatabaseHandlerSearch extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SearchNEWS";

    // Contacts table name
    private static final String TABLE_CONTACTS = "searchnews";


    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMG_URL = "imgurl";
    private static final String KEY_TIME = "time";
    private static final String KEY_DIS = "dis";
    // private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandlerSearch(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_TITLE +" TEXT,"
                + KEY_IMG_URL +" TEXT,"
                + KEY_TIME +" TEXT,"
                + KEY_DIS  +" TEXT" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    public void addContacts(Contact contact){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_TITLE, contact.get_title());
        values.put(KEY_IMG_URL, contact.get_imgurl() );
        values.put(KEY_TIME, contact.get_time());
        values.put(KEY_DIS, contact.get_dis() );
        // values.put(KEY_PH_NO, contact.getPhoneNumber());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_title(cursor.getString(1));
                contact.set_imgurl(cursor.getString(2));
                contact.set_time(cursor.getString(3));
                contact.set_dis(cursor.getString(4));
                // contact.setPhoneNumber(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    public int updateContact(Contact contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, contact.get_title());
        values.put(KEY_IMG_URL, contact.get_imgurl());
        values.put(KEY_TIME, contact.get_time());
        values.put(KEY_DIS, contact.get_dis());
        // values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }
         public  void deleteall(){
             SQLiteDatabase db = this.getWritableDatabase();
             db.execSQL("delete from "+ TABLE_CONTACTS);

             db.close();
         }



    public Cursor searchTasks(SQLiteDatabase db, String searchTxt) {
        Cursor cursor;
        String q = "select * from "+TABLE_CONTACTS+" where "+KEY_TITLE+" Like '"+searchTxt+"%'";
        cursor = db.rawQuery(q, null);
        Log.e("Database Op", q);
        return cursor;

    }






}
