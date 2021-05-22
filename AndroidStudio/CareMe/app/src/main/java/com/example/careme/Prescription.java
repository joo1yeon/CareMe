package com.example.careme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Prescription extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prescription, container, false);

        // 리사이클러뷰에 표시할 데이터 리스트 생성
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.format("text %d", i));
        }

        // recycler view에 LinearLayoutManager 객체 지정
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // 리사이클러뷰에 PrescriptionAdapter 객체 지정
        PrescriptionAdapter adapter = new PrescriptionAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
