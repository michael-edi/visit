package mo.org.cpttm.cm386.visit;

import android.app.Activity;
import android.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.Cursor;

public class ItemActivity extends Activity {
    FriendOpenHelper mydb = new FriendOpenHelper(this);
    int id_To_Update ;
    TextView textName;
    TextView textLocation;
    TextView textMemo;
    TextView textLONGITUDE;
    TextView textLATITUDE;
    ImageView picture;

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                textName.setText("NAME: " + rs.getString(rs.getColumnIndex("NAME")));
                textLocation.setText("LOCATION: " + rs.getString(rs.getColumnIndex("LOCATION")));
                textMemo.setText("MEMO: " + rs.getString(rs.getColumnIndex("MEMO")));
                textLONGITUDE.setText("LONGITUDE: " + rs.getString(rs.getColumnIndex("LONGITUDE")));
                textLATITUDE.setText("LATITUDE: " + rs.getString(rs.getColumnIndex("LATITUDE")));
                byte[] image = rs.getBlob(rs.getColumnIndex("PICTURE"));
                if (image != null) {
                    Bitmap bitmap = DbBitmapUtility.getImage(image);
                    if (bitmap != null) {
                        picture.setImageBitmap(bitmap);
                    }
                }

                if (!rs.isClosed()) {
                    rs.close();
                }

                textName.setFocusable(false);
                textName.setClickable(false);
                textLocation.setFocusable(false);
                textLocation.setClickable(false);
                textMemo.setFocusable(false);
                textMemo.setClickable(false);
                textLONGITUDE.setFocusable(false);
                textLONGITUDE.setClickable(false);
                textLATITUDE.setFocusable(false);
                textLATITUDE.setClickable(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setTitle("");

        picture=(ImageView) findViewById(R.id.photoView);
        textName=(TextView) findViewById(R.id.textName);
        textLocation=(TextView) findViewById(R.id.textLocation);
        textMemo=(TextView) findViewById(R.id.textMemo);
        textLONGITUDE=(TextView) findViewById(R.id.textLONGITUDE);
        textLATITUDE=(TextView) findViewById(R.id.textLATITUDE);

        Button editButton = (Button) findViewById(R.id.btnEdit);
        Button deleteButton = (Button) findViewById(R.id.btnDelete);

        editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editActivity = new Intent(getBaseContext(), EditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id_To_Update);
                    editActivity.putExtras(bundle);
                    startActivity(editActivity);
                }});

        deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               new AlertDialog.Builder(ItemActivity.this)
                        .setMessage("Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteEntry(id_To_Update);
                                ItemActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                }
            });

        }
}
