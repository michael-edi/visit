package mo.org.cpttm.cm386.visit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cm386-08-2016-c on 17/9/2016.
 */
public class FriendOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "friend.db";
    public static final String CONTACTS_TABLE_NAME = "FRIEND";
    public static final String CONTACTS_COLUMN_ID = "id";

    private static String DB_NAME = "friend.db";
    private static int VERSION = 300;

    public FriendOpenHelper(Context context){
        this (context, DB_NAME, null, VERSION);
    }


    public FriendOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
    }
//ID, NAME, LOCATION, MEMO FROM FRIEND
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FRIEND ( ID INTEGER PRIMARY KEY, NAME TEXT, LOCATION TEXT, MEMO TEXT, PICTURE BLOB, MAP TEXT,  LONGITUDE REAL, LATITUDE REAL, DATETIME NUMERIC)");
        db.execSQL("INSERT INTO FRIEND(ID, NAME, LOCATION, MEMO) VALUES (1, 'JOHNNY', 'BIG SUN BLD BK1 10H', 'ok')");
        db.execSQL("INSERT INTO FRIEND(ID, NAME, LOCATION, MEMO) VALUES (2, 'MICHAEL', 'ONE HOUSE BLD BK2 38A', 'ok')");
        Log.d("SQLite", "onCreate!");
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from FRIEND where id=" + id + "", null);
        return res;
    }
    public Cursor getNearData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from FRIEND where id=" + id + "", null);
        return res;
    }
    public boolean insertCar(String name, String location, String memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("LOCATION", location);
        contentValues.put("MEMO", memo);
        db.insert("FRIEND", null, contentValues);
        return true;
    }

    public boolean insertCar(String name, String location, String memo, double longitude, double latitude, byte[] picture){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("LOCATION", location);
        contentValues.put("MEMO", memo);
        contentValues.put("LONGITUDE", longitude);
        contentValues.put("LATITUDE", latitude);
        contentValues.put("PICTURE", picture);
        db.insert("FRIEND", null, contentValues);
        return true;
    }

    public boolean updateDetails(long rowId,String name, String location, String memo, double longitude, double latitude, byte[] picture)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("LOCATION", location);
        contentValues.put("MEMO", memo);
        contentValues.put("LONGITUDE", longitude);
        contentValues.put("LATITUDE", latitude);
        contentValues.put("PICTURE", picture);
        int i =  db.update("FRIEND", contentValues, "ID="+ rowId, null);
        return i > 0;
    }

    public boolean deleteEntry(int entryID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("FRIEND","ID="+entryID, null);
        db.close();
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("SQLite", "onUpgrade!");
        if (oldVersion == 200 && newVersion == 300){
            db.execSQL("DROP TABLE FRIEND");
            onCreate(db);
        }
    }
}
