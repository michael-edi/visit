package mo.org.cpttm.cm386.sqlitedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    CarOpenHelper mydb = new CarOpenHelper(this);
    EditText brand;
    EditText model;
    EditText seat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("");
         brand=(EditText) findViewById(R.id.edBrand);
         model=(EditText) findViewById(R.id.edModel);
         seat=(EditText) findViewById(R.id.edSeat);
        Button btnOK=(Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mydb.insertCar(brand.getText().toString(), model.getText().toString(), seat.getText().toString());
                //Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                finish();
            }});
    }
}
