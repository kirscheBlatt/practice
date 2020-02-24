package com.example.practice2;


import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<Map<String, Object>> dataset;


    private final List<ClipData.Item> mList = new ArrayList<>();

    private RecyclerView mRecycler;

    private View.OnClickListener mListener;

    private View v;

    int p;

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



        TextView nameTextView;
        TextView ageTextView;


        ViewHolder(View v) {
            super(v);
            nameTextView = (TextView)v.findViewById(R.id.name);
            ageTextView = v.findViewById(R.id.age);
        }
    }



    MyAdapter(List<Map<String, Object>> myDataset) {
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
        Map<String, Object> item = dataset.get(position);
        String menuName = (String)item.get("名前");
        String  menuAge = (String)item.get("年齢");
        String menuAgeStr = String.valueOf(menuAge);
        holder.nameTextView.setText(menuName);
        holder.ageTextView.setText(menuAgeStr);
        final  ViewHolder viewHolder = holder;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = viewHolder.getAdapterPosition();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public int getPosition(){
        return p;
    }

}
