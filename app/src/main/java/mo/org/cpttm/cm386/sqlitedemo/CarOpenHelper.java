package mo.org.cpttm.cm386.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cm386-08-2016-c on 17/9/2016.
 */
public class CarOpenHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "CAR";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_MODEL = "MODEL";
    public static final String CONTACTS_COLUMN_BRAND = "BRAND";
    public static final String CONTACTS_COLUMN_NUM_SEAT = "NUM_SEAT";

    private static String DB_NAME = "car.db";
    private static int VERSION = 200;

    public CarOpenHelper(Context context){
        this (context, DB_NAME, null, VERSION);
    }


    public CarOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CAR ( ID INTEGER PRIMARY KEY, BRAND TEXT, MODEL TEXT, NUM_SEAT INTEGER)");
        db.execSQL("INSERT INTO CAR VALUES (1, 'BENZ', 'A100', 4)");
        db.execSQL("INSERT INTO CAR VALUES (2, 'BMW', '118i', 4)");
        db.execSQL("INSERT INTO CAR VALUES (3, 'AUDI', 'A3', 5)");
        Log.d("SQLite", "onCreate!");
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from CAR where id=" + id + "", null);
        return res;
    }

    public boolean insertCar(String brand, String model, String num_seat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BRAND", brand);
        contentValues.put("MODEL", model);
        contentValues.put("NUM_SEAT", num_seat);
        db.insert("car", null, contentValues);
        return true;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("SQLite", "onUpgrade!");
        if (oldVersion == 100 && newVersion == 200){
            db.execSQL("DROP TABLE CAR");
            onCreate(db);
        }
    }
}
