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
        v=inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=v.findViewById(R.id.rvMain);

        customAdapter=new CustomAdapter(mList,getContext());
        recyclerView.setAdapter(customAdapter);

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
       /* setContentView(R.layout.activity_staggered_view);

        recyclerView = (RecyclerView) findViewById(R.id.rvMain);


        recyclerView.setAdapter(new GridLayoutAdapter(bitmaps));
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);*/
    }
/*
    private Bitmap[] setUpBitmaps() {
        Bitmap[] bitmaps = new Bitmap[10];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_1);
        bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_2);
        bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_3);
        bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_4);
        bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_5);
        bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_6);
        bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_7);
        bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_8);
        bitmaps[8] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_9);
        bitmaps[9] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_10);
        return bitmaps;
    }*/


}