
package com.example.laundryfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DatabaseHelper db;
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnTambah = (Button) findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, AddActivity.class));
            }
        });

        Button btnMaps = (Button) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MapsActivity.class));
            }
        });

        db = new DatabaseHelper(this);

        ls = (ListView) findViewById(R.id.list_data);
        ls.setOnItemClickListener(this);
        setupListView();


    }


    private void setupListView() {

        Cursor cursor = db.allData();
        Custom_Adapter customCursorAdapter = new Custom_Adapter(this, cursor, 1);
        ls.setAdapter(customCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getID = (TextView) view.findViewById(R.id.listID);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();
        Intent idpinjam = new Intent(LoginActivity.this, AddActivity.class);
        idpinjam.putExtra(DBHelper.row_id, id);
        startActivity(idpinjam);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}