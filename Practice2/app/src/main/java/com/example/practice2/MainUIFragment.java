package com.example.practice2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Locale;


public class MainUIFragment extends Fragment {
    private String[] dataset = new String[20];
    private View mView = null;


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
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.userRecyclerView);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(rLayoutManager);

        //todo 画像とほかのデータももらってきていれる
        for(int i=0; i < dataset.length; i++) {
            dataset[i] = String.format(Locale.ENGLISH, "うさまる_0%d", i);
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






        return v;
    }






    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}