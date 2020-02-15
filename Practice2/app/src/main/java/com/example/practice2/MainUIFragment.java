package com.example.practice2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.FileObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainUIFragment extends Fragment {

    List<Map<String, Object>> dataList = new ArrayList<>();
    Data mData = Data.getInstance();
    private View mView = null;
    private String filePath = Environment.getExternalStorageDirectory().toString() + "/Android/data/com/.example.practice2/setting.json";
    static int p = 99;



    public static MainUIFragment newInstance() {
        MainUIFragment fragment = new MainUIFragment();
        return fragment;
    }

    public MainUIFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_ui,container,false);
        mView = v;

        //RecyclerViewの設置
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.userRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(rLayoutManager);


        if (mData.jMap !=null) {
            String s = mData.readFile(filePath);
            try {
                dataList.add(mData.parseJsonToMap(s));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            //todo 画像とほかのデータももらってきていれる
           //  dataList = mData.getPersonalDataList();
        }
        final MyAdapter rAdapter = new MyAdapter(dataList){
            @Override
            public void clickEvent(){
            }
        };
        recyclerView.setAdapter(rAdapter);

        Button addButton = v.findViewById(R.id.add);
        Button modifyButton = v.findViewById(R.id.modify);
        //追加ボタンの処理
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = rAdapter.getPosition();
              showPersonalFragment(true,p);
            }
        });
        //更新ボタンの処理
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p= rAdapter.getPosition();
                if (99 != p){
                    showPersonalFragment(false,p);
                }

            }
        });

        TextView logText = v.findViewById(R.id.logData);
        int n = dataList.size();
        logText.setText(String.format(Locale.ENGLISH,"現在のリストは%d件です",n));

        return v;
    }

    private void showPersonalFragment(boolean addMode, int pos){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, PersonalFragment.newInstance(addMode,pos));
        fragmentTransaction.commit();
    }





}