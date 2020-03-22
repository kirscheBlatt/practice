package com.example.schoolsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {


    HttpManager mHttpManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mHttpManager = HttpManager.getInstance();
        final TextView textView = findViewById(R.id.no_list_text);
        Button button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo リストの更新
                mHttpManager.requestGet();
            }
        });

        Button button1 =findViewById(R.id.setText);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(mHttpManager.ret);
            }
        });

    }
}
