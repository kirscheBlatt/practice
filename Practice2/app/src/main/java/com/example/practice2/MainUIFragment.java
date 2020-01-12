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

import android.os.FileObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.Locale;


public class MainUIFragment extends Fragment {
    private String[] dataset = new String[20];
    private View mView = null;
    private ImageView imageView;

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

        //todo 画像とほかのデータももらってきていれる
        for(int i=0; i < dataset.length; i++) {
            dataset[i] = String.format(Locale.ENGLISH, "①　住所_0%d", i);
        }
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // BackStackを設定
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.replace(R.id.container, PersonalFragment.newInstance());
                fragmentTransaction.commit();
            }
        });

        MyAdapter rAdapter = new MyAdapter(dataset){
            @Override
            public void clickEvent(){
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, PersonalFragment.newInstance());
                fragmentTransaction.commit();
            }
        };
        recyclerView.setAdapter(rAdapter);

        Button addButton = v.findViewById(R.id.add);
        Button modifyButton = v.findViewById(R.id.modify);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showPersonalFragment();
            }
        });
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPersonalFragment();
            }
        });



        return v;
    }

    private void showPersonalFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, PersonalFragment.newInstance());
        fragmentTransaction.commit();
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}