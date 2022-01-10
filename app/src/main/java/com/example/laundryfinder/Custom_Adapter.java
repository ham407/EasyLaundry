package com.example.laundryfinder;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class Custom_Adapter extends CursorAdapter {

    private LayoutInflater ly;
    private SparseBooleanArray mSelectedItems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Custom_Adapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_data, viewGroup, false);
        MyHolder holder = new MyHolder();

        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListNama = (TextView)v.findViewById(R.id.listNama);
        holder.ListLokasi = (TextView)v.findViewById(R.id.listLokasi);
        holder.ListStatus = (TextView)v.findViewById(R.id.listStatus);

        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.row_id)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.row_nama)));
        holder.ListLokasi.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.row_latitude)) +
                " - " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.row_longitude)));
        holder.ListStatus.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.row_status)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListNama;
        TextView ListLokasi;
        TextView ListStatus;
    }
}
