package mo.org.cpttm.cm386.visit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static java.lang.Double.parseDouble;

public class EditActivity extends Activity {
    CarOpenHelper mydb = new CarOpenHelper(this);
    int id_To_Update ;
    EditText fname;
    EditText location;
    EditText memo;
    EditText longitude;
    EditText latitude;
    Bitmap picture;
    ImageView pictureView;
    private static final int PICK_REQUEST=1738;
    final static int REQUEST_CODE=1038;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        fname=(EditText) findViewById(R.id.edName);
        location=(EditText) findViewById(R.id.edLocation);
        memo=(EditText) findViewById(R.id.edMemo);
        longitude=(EditText) findViewById(R.id.edLongitude);
        latitude=(EditText) findViewById(R.id.edLatitude);
        pictureView=(ImageView) findViewById(R.id.photoResultView);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();


                fname.setText(rs.getString(rs.getColumnIndex("NAME")));
                location.setText(rs.getString(rs.getColumnIndex("LOCATION")));
                memo.setText(rs.getString(rs.getColumnIndex("MEMO")));
                latitude.setText(rs.getString(rs.getColumnIndex("LATITUDE")));
                longitude.setText(rs.getString(rs.getColumnIndex("LONGITUDE")));
                byte[] image = rs.getBlob(rs.getColumnIndex("PICTURE"));
                if (image != null) {
                    Bitmap bitmap = DbBitmapUtility.getImage(image);
                    if (bitmap != null) {
                        pictureView.setImageBitmap(bitmap);
                    }
                }

                if (!rs.isClosed())
                {
                    rs.close();
                }
            }
        }

        Button btnOK=(Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (latitude.getText().toString().length()==0) latitude.setText("0");
                if (longitude.getText().toString().length()==0) longitude.setText("0");
                if (picture != null) {
                    mydb.updateDetails(id_To_Update, fname.getText().toString(), location.getText().toString(), memo.getText().toString(),
                            parseDouble(longitude.getText().toString()), parseDouble(latitude.getText().toString()), DbBitmapUtility.getBytes(picture));
                } else {
                    mydb.updateDetails(id_To_Update, fname.getText().toString(), location.getText().toString(), memo.getText().toString(),
                            parseDouble(longitude.getText().toString()), parseDouble(latitude.getText().toString()), null);

                }
                //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                finish();
            }});

        Button btnPicture=(Button) findViewById(R.id.btnPicture);
        btnPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent i=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, PICK_REQUEST);
            }});

        Button btnLocation=(Button) findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);  //getBaseContext()
                startActivityForResult(intent, REQUEST_CODE);
            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_REQUEST) {
                Toast.makeText(getApplicationContext(), "PICK_REQUEST RESULT_OK", Toast.LENGTH_SHORT).show();
                picture=(Bitmap) data.getExtras().get("data");
                pictureView.setImageBitmap(picture);
            } else
            if (requestCode == REQUEST_CODE)
            {
                //Toast.makeText(getApplicationContext(), "onActivityResult reached", Toast.LENGTH_LONG).show();
                String Latitude=data.getStringExtra("Latitude");
                String Longitude=data.getStringExtra("Longitude");
                latitude.setText(Latitude);
                longitude.setText(Longitude);
                Toast.makeText(getApplicationContext(), "Location="+Latitude+":"+Longitude, Toast.LENGTH_LONG).show();
            }
        }
    }
}
