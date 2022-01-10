package com.example.laundryfinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    TextView TvStatus;
    EditText TxID, TxNama, TxLatitude, TxLongitude, TxStatus;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper = new DatabaseHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxID = (EditText)findViewById(R.id.txID);
        TxNama = (EditText)findViewById(R.id.txNamaLaundry);
        TxLatitude = (EditText)findViewById(R.id.txLatitude);
        TxLongitude = (EditText)findViewById(R.id.txLongitude);
        TxStatus = (EditText)findViewById(R.id.txStatus);

        TvStatus = (TextView)findViewById(R.id.tVStatus);

        getData();

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

    }

    private void getData() {
        Cursor cur = dbHelper.oneData(id);
        if(cur.moveToFirst()){
            String idpinjam = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.row_id));
            String nama = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.row_nama));
            String latitude = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.row_latitude));
            String longitude = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.row_longitude));
            String status = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.row_status));

            TxID.setText(idpinjam);
            TxNama.setText(nama);
            TxLatitude.setText(latitude);
            TxLongitude.setText(longitude);
            TxStatus.setText(status);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        String idpinjam = TxID.getText().toString().trim();

        MenuItem itemDelete = menu.findItem(R.id.action_delete);
        MenuItem itemClear = menu.findItem(R.id.action_clear);
        MenuItem itemSave = menu.findItem(R.id.action_save);

        if (idpinjam.equals("")){
            itemDelete.setVisible(false);
            itemClear.setVisible(true);
        }else {
            itemDelete.setVisible(true);
            itemClear.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                insertAndUpdate();
        }
        switch (item.getItemId()){
            case R.id.action_clear:
                TxNama.setText("");
                TxLatitude.setText("");
                TxLongitude.setText("");
        }
        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setMessage("Data ini akan dihapus");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(id);
                        Toast.makeText(AddActivity.this, "Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertAndUpdate(){
        String idpinjam = TxID.getText().toString().trim();
        String nama = TxNama.getText().toString().trim();
        String latitude = TxLatitude.getText().toString().trim();
        String longitude = TxLongitude.getText().toString().trim();
        String status = "Dipinjam";

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.row_nama, nama);
        values.put(DatabaseHelper.row_latitude, latitude);
        values.put(DatabaseHelper.row_longitude, longitude);
        values.put(DatabaseHelper.row_status, status);

        if (nama.equals("") || latitude.equals("") || longitude.equals("")){
            Toast.makeText(AddActivity.this, "Isi Data Dengan Lengkap", Toast.LENGTH_SHORT).show();
        }else {
            if(idpinjam.equals("")){
                dbHelper.insertData(values);
            }else {
                dbHelper.updateData(values, id);
            }

            Toast.makeText(AddActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}