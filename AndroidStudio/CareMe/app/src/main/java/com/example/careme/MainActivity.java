package com.example.careme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.map.MapFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    MyCalendar fragCal = new MyCalendar();
    Chatbot fragChat = new Chatbot();
    Map fragMap = new Map();
    Prescription fragPres = new Prescription();
    //ConfirmReservation fragConfirmRes = new ConfirmReservation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로그인?
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavi);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame, fragCal);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                changeFragment(item.getItemId());
                return true;
            }
        });
    }

    public void changeFragment(int title_id) { // fragment를 교체하는 메소드
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (title_id) {
            case R.id.bottomnavi_calendar: // 달력
                fragmentTransaction.replace(R.id.main_frame, fragCal); // (fragment를 담을 영역 id, 달력 fragment 객체)
                fragmentTransaction.commit();
                break;
            case R.id.bottomnavi_chatbot: // 챗봇
                fragmentTransaction.replace(R.id.main_frame, fragChat); // (fragment를 담을 영역 id, 챗봇 fragment 객체)
                fragmentTransaction.commit();
                break;
            case R.id.bottomnavi_map: // 지도
                fragmentTransaction.replace(R.id.main_frame, fragMap); // (fragment를 담을 영역 id, 지도 fragment 객체)
                fragmentTransaction.commit();
                break;
            case R.id.bottomnavi_prescription: // 약봉투
                fragmentTransaction.replace(R.id.main_frame, fragPres); // (fragment를 담을 영역 id, 약봉투 fragment 객체)
                fragmentTransaction.commit();
                break;
//          case 1: // 예약확인
//              fragmentTransaction.replace(R.id.main_frame, fragConfirmRes); // (fragment를 담을 영역 id, 예약 확인 fragment 객체)
//              fragmentTransaction.commit();
//              break;
        }
        return;
    }
}