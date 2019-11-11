package com.example.a81903.practice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a81903.practice.fragment.Tab2Fragment;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<String> mData;
    private Context mContext;

    public RecyclerAdapter(Context context, ArrayList<String> data, Tab2Fragment tab2Fragment){

        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_1,viewGroup,false);
       ViewHolder viewHolder = new ViewHolder(inflate);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (mData != null && mData.size() > i && mData.get(i) != null) {
            ViewHolder holder = (ViewHolder)viewHolder;
            holder.textView.setText(mData.get(i));
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null){
            return mData.size();
        }else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView textView;
         CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
            mCheckBox= (CheckBox) itemView.findViewById(R.id.checkbox);

        }
    }


}

