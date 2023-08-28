package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.GridHolder> {

    List<ModalClass> mList; // Cambiado a List<ModalClass>
    Context context;


    public RowAdapter(List<ModalClass> mList, Context context) { // Cambiado el tipo del parámetro
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public GridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view= layoutInflater.inflate(R.layout.each_row, parent, false);
            return new GridHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GridHolder holder, int position) {
        final ModalClass modalItem = mList.get(position); // Cambiado el acceso al elemento
        holder.imageView.setImageResource(modalItem.getImage());
        holder.imageView2.setImageResource(modalItem.getImage()); // Nueva imagen
        holder.imageView3.setImageResource(modalItem.getImage()); // Nueva imagen

        holder.textView.setText(modalItem.getText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //está creando y mostrando un mensaje emergente (Toast)
                Toast.makeText(context, modalItem.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
        public int getItemCount() {
            return mList.size();
        }


    public class GridHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView imageView2; // Nueva ImageView
        ImageView imageView3; // Nueva ImageView
        TextView textView;

        public GridHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text2);

            imageView = itemView.findViewById(R.id.imageview);
            imageView2 = itemView.findViewById(R.id.imageview); // Nuevo ImageView
            imageView3 = itemView.findViewById(R.id.imageview); // Nuevo ImageView
        }
    }
}
