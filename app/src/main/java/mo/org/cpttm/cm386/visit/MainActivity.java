package mo.org.cpttm.cm386.visit;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    List<String> data;
    final static int REQUEST_CODE=1033;
    String Latitude="0";
    String Longitude="0";
    CheckBox cbNearOnly;
    private int[] friendIds;
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Log.d("Location", "My Location Lat:"+Latitude+" Long:"+Longitude);
        //init to circumference of the Earth
        float smallest = 40008000.0f; //m

        //locations to calculate difference with
        Location me = new Location("");
        Location dest = new Location("");

        if (cbNearOnly.isChecked()) {
            //set lat and long of comparison obj
            me.setLatitude(Double.valueOf(Latitude));
            me.setLongitude(Double.valueOf(Longitude));
        }

        data= new ArrayList<String>();
        FriendOpenHelper carOpenHelper = new FriendOpenHelper(this);
        SQLiteDatabase db = carOpenHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ID, NAME, LOCATION, MEMO,LATITUDE,LONGITUDE FROM FRIEND", null);
        friendIds=new int[c.getCount()];
        //Toast.makeText(getApplicationContext(), "Count="+c.getCount(), Toast.LENGTH_LONG).show();
        int n=0;
        while (c.moveToNext()){

            Integer id = c.getInt(c.getColumnIndex("ID"));
            String fname = c.getString(c.getColumnIndex("NAME"));
            String location = c.getString(c.getColumnIndex("LOCATION"));
            String memo = c.getString(c.getColumnIndex("MEMO"));
            Float latitude=c.getFloat(c.getColumnIndexOrThrow("LATITUDE"));
            Float longitude=c.getFloat(c.getColumnIndexOrThrow("LONGITUDE"));


            if (cbNearOnly.isChecked()) {
                //set lat and long of destination obj
                dest.setLatitude(latitude);
                dest.setLongitude(longitude);

                //grab distance between me and the destination
                float dist = me.distanceTo(dest);

                //within 200m?
                if (dist < 200) {
                    data.add( fname +  "  " + location );
                    friendIds[n]=id;
                    n++;
                }
                Log.d("SQLite", "ID: " + id + " Name: " + fname + " Loc: " + location + " Lat:" + latitude + " Long:" + longitude + " dist:" + dist);
            } else {
                data.add( fname +  "  " + location );
                friendIds[n]=id;
                n++;
                Log.d("SQLite", "ID: " + id + " Name: " + fname + " Loc: " + location + " Lat:" + latitude + " Long:" + longitude);
            }
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

        Button btnLocation=(Button) findViewById(R.id.btnFind);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);  //getBaseContext()
                startActivityForResult(intent, REQUEST_CODE);
            }});

        cbNearOnly=(CheckBox) findViewById(R.id.cbNearOnly);
        cbNearOnly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cbNearOnly.isChecked()) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                } else {
                    onResume();
                }
            }});
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("onListItemClick", "ID: " + id + " pos: " + position);

        super.onListItemClick(l, v, position, id);
            Intent itemActivity = new Intent(this, ItemActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putInt("id", position+1);  //ERROR after filter applied
            bundle.putInt("id", friendIds[position]);
            itemActivity.putExtras(bundle);
            startActivity(itemActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE)
            {
                //Toast.makeText(getApplicationContext(), "onActivityResult reached", Toast.LENGTH_LONG).show();
                Latitude=data.getStringExtra("Latitude");
                Longitude=data.getStringExtra("Longitude");
                Toast.makeText(getApplicationContext(), "Location="+Latitude+":"+Longitude, Toast.LENGTH_LONG).show();
            }
        }
    }
}
