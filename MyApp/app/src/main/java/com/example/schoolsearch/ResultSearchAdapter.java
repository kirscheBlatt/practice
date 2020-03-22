package com.example.schoolsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ResultSearchViewHolder> {

    private String[] mDataset;


     static class ResultSearchViewHolder extends RecyclerView.ViewHolder {
         TextView mTextView;
         ResultSearchViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.result_list_text);
        }
    }


    public ResultSearchAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public ResultSearchAdapter.ResultSearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_result_search, parent, false);

        ResultSearchViewHolder vh = new ResultSearchViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ResultSearchViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}




