package com.example.careme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PrescriptionActivity extends Fragment {
    View view;
    ArrayList<String> list = new ArrayList<>();
    TextView tvPrescription;

    final static int TAKE_PICTURE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_prescription, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        tvPrescription = view.findViewById(R.id.tv_prescription);

        // recycler view에 LinearLayoutManager 객체 지정
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // 리사이클러뷰에 PrescriptionAdapter 객체 지정
        PrescriptionAdapter adapter = new PrescriptionAdapter(list);
        recyclerView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("log", "권한 설정 완료");
            }
            else {
                Log.d("log", "권한 설정 요청");
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        // 약봉투 추가 버튼 클릭
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrescription.setText("약 봉투");
                switch (view.getId()) {
                    case R.id.fab:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);

                        // 리사이클러뷰에 표시할 데이터 리스트 생성
                        list.add(String.format("약 봉투 추가"));
                        adapter.notifyDataSetChanged();

                        break;
                }
            }
        });
        return view;
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("log", "onRequestPermissionResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d("log", "Permission: " +permissions[0] + "was " + grantResults[0]);
        }
    }
}
