package com.example.asvenugo.bottomtabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by asvenugo on 7/1/2016.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="DateDB";

    private static final String TABLE_DATE="DATE";

    private static final String DAY="Day";
    private static final String MONTH="Month";
    private static final String YEAR="Year";

    private static final String COLUMNS[]={DAY, MONTH, YEAR};

    public MySQLiteOpenHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_DATEDB_TABLE = "CREATE TABLE " + TABLE_DATE +" ( " +
                DAY +" INTEGER, " +
                MONTH +" INTEGER, " +
                YEAR +" INTEGER)";
        db.execSQL(CREATE_DATEDB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS DATE");
        this.onCreate(db);
    }

    public void addDate(Integer day, Integer month, Integer year) {
        Log.d("addDate", day.toString());

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DAY, day);
        values.put(MONTH, month);
        values.put(YEAR, year);

        db.insert(TABLE_DATE, null, values);

        db.close();
    }

    public String getDate() {
        String dateString;
        Integer day = 0, month = 0, year = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DATE, null);

        if (cursor != null&&cursor.moveToFirst()) {


            day = cursor.getInt(cursor.getColumnIndex(DAY));
            month = cursor.getInt(cursor.getColumnIndex(MONTH));
            year = cursor.getInt(cursor.getColumnIndex(YEAR));

//            day = cursor.getInt(0);
//            month = cursor.getInt(1);
//            year = cursor.getInt(2);

            Log.d("Day in getdate", day.toString());
        }
        else {
            return null;
        }

        dateString = day.toString() + "/" + month.toString() + "/" + year.toString();

        return dateString;
    }

    public void removeDate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM DATE");
    }


}
