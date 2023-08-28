package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotifFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    RowAdapter customAdapter;
    List<ModalClass> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notif, container, false);
        recyclerView = v.findViewById(R.id.recyclerView2);
        Collections.shuffle(mList);

        customAdapter = new RowAdapter(mList, getContext());
        recyclerView.setAdapter(customAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>(); // Cambiado a ArrayList

        mList.add(new ModalClass(R.drawable.icon_1, "Pensamos que estas 13 ideas podrían gustarte  3h"));
        mList.add(new ModalClass(R.drawable.icon_2, "Hay nuevos Pines en tu feed de inicio  22h"));
        mList.add(new ModalClass(R.drawable.icon_3, "Tatuaje, Arte y más Pines en tendencia en Pinterest  1d"));
        mList.add(new ModalClass(R.drawable.icon_4, "Ropa, Ropa grunge y otros Pines populares en Pinterest  1d"));
        mList.add(new ModalClass(R.drawable.icon_5, "Pines que has inspirado 2d"));
        mList.add(new ModalClass(R.drawable.icon_6, "50 Pines sobre Casual Outfit que podrían gustarte  2d"));
        mList.add(new ModalClass(R.drawable.icon_7, "Hay nuevos Pines en tu feed de inicio  3d"));
        mList.add(new ModalClass(R.drawable.icon_8, "Pines que has inspirado  4d"));
        mList.add(new ModalClass(R.drawable.icon_9, "49 Pines sobre Ropa de trabajo casual que podrían gustarte  5d"));
        mList.add(new ModalClass(R.drawable.icon_10, "Ropa, Ropa grunge y otros Pines populares en Pinterest  5d"));
    }
}
