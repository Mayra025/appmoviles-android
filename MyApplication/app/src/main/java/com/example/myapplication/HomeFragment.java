package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    List<ModalClass> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        v=inflater.inflate(R.layout.fragment_home,container,false); //inflando, preparando, el diseño de un fragmento
        recyclerView=v.findViewById(R.id.rvMain);

        customAdapter=new CustomAdapter(mList,getContext());
        recyclerView.setAdapter(customAdapter); //para mostrar elementos en una lista o cuadrícula en función del diseño configurado en el adaptador.

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //Staggered para cuadriculas desiguales,cómo se mostrarán los elementos en forma de cuadrícula con dos columnas y orientación vertical.
        recyclerView.setLayoutManager(mLayoutManager);
        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList=new ArrayList<>();

        mList.add(new ModalClass(R.drawable.icon_1, "❤ 44 mil"));
        mList.add(new ModalClass( R.drawable.icon_2,""));
        mList.add(new ModalClass( R.drawable.icon_3,""));
        mList.add(new ModalClass( R.drawable.icon_4,"Akzentz Luxio - Coy"));
        mList.add(new ModalClass(R.drawable.icon_5,"Tattos"));
        mList.add(new ModalClass( R.drawable.icon_6,""));
        mList.add(new ModalClass( R.drawable.icon_7,""));
        mList.add(new ModalClass(R.drawable.icon_8,"View Picture (516 x 701)"));
        mList.add(new ModalClass( R.drawable.icon_9,"❤ 63 mil"));
        mList.add(new ModalClass( R.drawable.icon_10,"Burgundy Outfits"));
        mList.add(new ModalClass(R.drawable.icon_1, "❤ 44 mil"));
        mList.add(new ModalClass( R.drawable.icon_2,""));
        mList.add(new ModalClass( R.drawable.icon_3,""));
        mList.add(new ModalClass( R.drawable.icon_4,"Akzentz Luxio - Coy"));
        mList.add(new ModalClass(R.drawable.icon_5,"Tattos"));
        mList.add(new ModalClass( R.drawable.icon_6,""));
        mList.add(new ModalClass( R.drawable.icon_7,""));
        mList.add(new ModalClass(R.drawable.icon_8,"View Picture (516 x 701)"));
        mList.add(new ModalClass( R.drawable.icon_9,"❤ 63 mil"));
        mList.add(new ModalClass( R.drawable.icon_10,"Burgundy Outfits"));

    }
}