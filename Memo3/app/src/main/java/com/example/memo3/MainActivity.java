package com.example.memo3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    MemoOpenHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // データベースから値を取得する
        if(helper == null){
            helper = new MemoOpenHelper(MainActivity.this);
        }
        // メモリストデータを格納する変数
        final ArrayList<HashMap<String, String>> memoList = new ArrayList<>();
        // データベースを取得する
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor c = db.rawQuery("select uuid, body from MEMO_TABLE order by id", null);
            // Cursorの先頭行があるかどうか確認
            boolean next = c.moveToFirst();

            // 取得した全ての行を取得
            while (next) {
                HashMap<String,String> data = new HashMap<>();
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                String uuid = c.getString(0);
                String body = c.getString(1);
                if(body.length() > 10){
                    // リストに表示するのは10文字まで
                    body = body.substring(0, 11) + "...";
                }
                // 引数には、(名前,実際の値)という組合せで指定します　名前はSimpleAdapterの引数で使用します
                data.put("body",body);
                data.put("id",uuid);
                memoList.add(data);
                // 次の行が存在するか確認
                next = c.moveToNext();
            }
        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }
        //Adapter
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this,memoList,android.R.layout.simple_list_item_activated_2,new String[]{"body","id"},new int[]{android.R.id.text1,android.R.id.text2}
        );
        ListView listView = findViewById(R.id.memoList);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this,CreateMemoActivity.class);
                TwoLineListItem two = (TwoLineListItem)view;
                TextView idTextView = (TextView)two.getText2();
                String isStr = (String) idTextView.getText();
                intent.putExtra("id", isStr);
                startActivity(intent);

            }


        });

        // リスト項目を長押しクリックした時の処理
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            /**
             * @param parent ListView
             * @param view 選択した項目
             * @param position 選択した項目の添え字
             * @param id 選択した項目のID
             */
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択されたビューを取得 TwoLineListItemを取得した後、text2の値を取得する
                TwoLineListItem two = (TwoLineListItem)view;
                TextView idTextView = (TextView)two.getText2();
                String idStr = (String) idTextView.getText();

                // 長押しした項目をデータベースから削除
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    db.execSQL("DELETE FROM MEMO_TABLE WHERE uuid = '"+ idStr +"'");
                } finally {
                    db.close();
                }
                // 長押しした項目を画面から削除
                memoList.remove(position);
                simpleAdapter.notifyDataSetChanged();

                // trueにすることで通常のクリックイベントを発生させない
                return true;
            }
        });
        /**
         * 新規作成するボタン処理
         */
        // idがnewButtonのボタンを取得
        Button newButton = (Button) findViewById(R.id.newButton);
        // clickイベント追加
        newButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // CreateMemoActivityへ遷移
                Intent intent = new Intent(MainActivity.this,CreateMemoActivity.class);
                intent.putExtra("id", "");
                startActivity(intent);
            }
        });

    }
}
