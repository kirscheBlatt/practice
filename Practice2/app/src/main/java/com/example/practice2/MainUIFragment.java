package com.example.practice2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;


public class MainUIFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String[] dataset = new String[20];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.userRecyclerView);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(rLayoutManager);

        for(int i=0; i < dataset.length; i++) {
            dataset[i] = String.format(Locale.ENGLISH, "Data_0%d", i);
        }


        RecyclerView.Adapter rAdapter = new MyAdapter(dataset);
        recyclerView.setAdapter(rAdapter);

        return v;
    }




    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
