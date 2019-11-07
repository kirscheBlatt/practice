package com.example.a81903.practice.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a81903.practice.R;
import com.example.a81903.practice.RecyclerAdapter;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Tab2Fragment extends Fragment {


    public Tab2Fragment() {
    }

    private Activity mActivity =null;
    private View mView;
    private Tab2FragmentListener mFragmentListener = null;

    private RecyclerView mRecyclerView = null;
    private RecyclerView.Adapter mAdapter =null;

    public interface Tab2FragmentListener{
        void onRecyclerEvent();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab2,container,false);

        mRecyclerView =  mView.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));



        return mView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 適当にデータ作成
        ArrayList<String> array = new ArrayList<>();
        array.add("aaaaaa");
        array.add("B");
        array.add("C");

        mAdapter = new RecyclerAdapter(mActivity,array,this);
        mRecyclerView.setAdapter(mAdapter);

    }
}

