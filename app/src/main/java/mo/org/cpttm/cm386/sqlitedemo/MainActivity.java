package mo.org.cpttm.cm386.sqlitedemo;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    List<String> data;

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        data= new ArrayList<String>();
        CarOpenHelper carOpenHelper = new CarOpenHelper(this);
        SQLiteDatabase db = carOpenHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ID, BRAND, MODEL, NUM_SEAT FROM CAR", null);
        while (c.moveToNext()){
            Integer id = c.getInt(c.getColumnIndex("ID"));
            String brand = c.getString(c.getColumnIndex("BRAND"));
            String model = c.getString(c.getColumnIndex("MODEL"));
            Integer numOfSeat = c.getInt(c.getColumnIndex("NUM_SEAT"));
            data.add( brand +  "  " + model );
            Log.d("SQLite", "ID: "+ id + " BRAND: "+ brand +  " MODEL: " + model + " NUM OF SEAT:"+ numOfSeat);
        }
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_item, data);
        setListAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAdd=(Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addActivity = new Intent(getBaseContext(), AddActivity.class);
                startActivity(addActivity);
            }});
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
            Intent itemActivity = new Intent(this, ItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", position+1);
            itemActivity.putExtras(bundle);
            startActivity(itemActivity);
    }
}
