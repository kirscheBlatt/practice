package com.example.practice2;


import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] dataset = new String[20];


    private final List<ClipData.Item> mList = new ArrayList<>();

    private RecyclerView mRecycler;

    private View.OnClickListener mListener;

    private View v;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecycler= recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecycler = null;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {



        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.text_view);
        }
    }



    MyAdapter(String[] myDataset) {
        dataset = myDataset;

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dummy_user_list, parent, false);
        //todo　ここであってるかふめい
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent();
            }
        });

        return new ViewHolder(v);
    }

public void clickEvent(){
}



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(dataset[position]);
        final  ViewHolder viewHolder = holder;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.getAdapterPosition();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }

}
