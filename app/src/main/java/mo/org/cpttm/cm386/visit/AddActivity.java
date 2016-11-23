package mo.org.cpttm.cm386.visit;


import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static java.lang.Double.parseDouble;


public class AddActivity extends FragmentActivity {
    FriendOpenHelper mydb = new FriendOpenHelper(this);
    EditText fname;
    EditText location;
    EditText memo;
    EditText longitude;
    EditText latitude;
    Bitmap   picture;
    private static final int PICK_REQUEST=1733;
    final static int REQUEST_CODE=1033;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("");
         fname=(EditText) findViewById(R.id.edName);
         location=(EditText) findViewById(R.id.edLocation);
         memo=(EditText) findViewById(R.id.edMemo);
         longitude=(EditText) findViewById(R.id.edLongitude);
         latitude=(EditText) findViewById(R.id.edLatitude);

        Button btnOK=(Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (picture != null) {
                    mydb.insertCar(fname.getText().toString(), location.getText().toString(), memo.getText().toString(),
                            parseDouble(longitude.getText().toString()), parseDouble(latitude.getText().toString()), DbBitmapUtility.getBytes(picture));
                } else {
                    mydb.insertCar(fname.getText().toString(), location.getText().toString(), memo.getText().toString(),
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
                ImageView image=(ImageView) findViewById(R.id.photoResultView);
                image.setImageBitmap(picture);
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
