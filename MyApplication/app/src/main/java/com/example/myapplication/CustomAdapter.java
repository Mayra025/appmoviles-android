package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.GridHolder> {

        List<ModalClass> mList;
        Context context;

    public CustomAdapter(List<ModalClass> mList, Context context) {
        this.mList = mList;
        this.context=context;

    }
    @NonNull
    @Override
    public GridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view= layoutInflater.inflate(R.layout.staggered_row, parent, false);
            return new GridHolder(view);
    }


    @Override
        public void onBindViewHolder(@NonNull GridHolder holder, int position) {
            //holder.imageView.requestLayout();
            holder.imageView.setImageResource(mList.get(position).getImage());
            holder.textView.setText(mList.get(position).getText());
    }

        @Override
        public int getItemCount() {
            return mList.size();
        }


    public class GridHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public GridHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            textView = itemView.findViewById(R.id.tvCaption);
        }
    }
}
