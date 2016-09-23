package mo.org.cpttm.cm386.sqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.database.Cursor;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setTitle("");
        TextView textMODEL=(TextView) findViewById(R.id.textMODEL);
        TextView textBRAND=(TextView) findViewById(R.id.textBRAND);
        TextView textNUM_SEAT=(TextView) findViewById(R.id.textNUM_SEAT);

        CarOpenHelper mydb = new CarOpenHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                Cursor rs = mydb.getData(Value);
                int id_To_Update = Value;
                rs.moveToFirst();

                String brand = "BRAND: "+rs.getString(rs.getColumnIndex("BRAND"));
                String model = "MODEL: "+rs.getString(rs.getColumnIndex("MODEL"));
                String seat = "NUMBER OF SEAT: "+rs.getString(rs.getColumnIndex("NUM_SEAT"));

                if (!rs.isClosed())
                {
                    rs.close();
                }

                textBRAND.setText((CharSequence)brand);
                textBRAND.setFocusable(false);
                textBRAND.setClickable(false);
                textMODEL.setText((CharSequence)model);
                textMODEL.setFocusable(false);
                textMODEL.setClickable(false);
                textNUM_SEAT.setText((CharSequence)seat);
                textNUM_SEAT.setFocusable(false);
                textNUM_SEAT.setClickable(false);
            }
        }
    }
}
