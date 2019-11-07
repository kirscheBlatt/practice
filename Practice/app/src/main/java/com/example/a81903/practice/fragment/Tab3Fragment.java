package com.example.a81903.practice.fragment;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a81903.practice.R;
import com.example.a81903.practice.TestOpenHelper;


public class Tab3Fragment extends Fragment {

    private TextView textView;
    private EditText editTextKey, editTextValue;
    private TestOpenHelper helper;
    private SQLiteDatabase db;

    public Tab3Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3,container,false);
        editTextKey = v.findViewById(R.id.edit_text_key);
        editTextValue = v.findViewById(R.id.edit_text_value);

        textView = v.findViewById(R.id.text_view);

        Button insertButton = v.findViewById(R.id.button_insert);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(helper == null){
                    helper = new TestOpenHelper(getContext());
                }

                if(db == null){
                    db = helper.getWritableDatabase();
                }

                String key = editTextKey.getText().toString();
                String value = editTextValue.getText().toString();

                insertData(db, key, Integer.valueOf(value));
            }
        });

        Button readButton = v.findViewById(R.id.button_read);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });

        return v;
    }

    private void readData(){
        if(helper == null){
            helper = new TestOpenHelper(getContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }
        Log.d("debug","**********Cursor");

        Cursor cursor = db.query(
                "testdb",
                new String[] { "company", "stockprice" },
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++) {
            sbuilder.append(cursor.getString(0));
            sbuilder.append(": ");
            sbuilder.append(cursor.getInt(1));
            sbuilder.append("\n");
            cursor.moveToNext();
        }

        // 忘れずに！
        cursor.close();

        Log.d("debug","**********"+sbuilder.toString());
        textView.setText(sbuilder.toString());
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView,parent);
        CheckBox ch = view.findViewById(R.id.)
        return super.getView();
    }

    private void insertData(SQLiteDatabase db, String com, int price){

        ContentValues values = new ContentValues();
        values.put("company", com);
        values.put("stockprice", price);

        db.insert("testdb", null, values);
    }

}
