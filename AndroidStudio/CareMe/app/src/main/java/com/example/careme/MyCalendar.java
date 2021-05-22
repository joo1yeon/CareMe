package com.example.careme;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.careme.caldecorators.EventDecorator;
import com.example.careme.caldecorators.OneDayDecorator;
import com.example.careme.caldecorators.SaturdayDecorator;
import com.example.careme.caldecorators.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class MyCalendar extends Fragment {
    View view;
    TextView tv_date;
    String time, kcal, menu;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;
    MaterialCalendarView materialCalendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mycalendar, container, false);
        materialCalendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);
        tv_date = (TextView)view.findViewById(R.id.tv_date);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(java.util.Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator
        );

        String[] result = {"2021,03,18", "2021,04,18", "2021,05,18", "2021,06,18"};

        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth() + 1;
                int day = date.getDay();

                Log.i("Year test", year + "");
                Log.i("Month test", month + "");
                Log.i("Day test", day + "");

                String shot_Day = year + "년 " + month + "월 " + day + "일";

                Log.i("shot_Day test", shot_Day + "");
                //materialCalendarView.clearSelection();

                //Toast.makeText(getContext(), shot_Day, Toast.LENGTH_SHORT).show();
                tv_date.setText(shot_Day);
            }
        });

        return view;
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
        String[] time_result;

        ApiSimulator(String[] time_result) {
            this.time_result = time_result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            // 특정 날짜 달력에 점 표시
            // String 문자열인 time_result를 받아와서 ,를 기준으로 자르고 string을 int로 반환
            for (int i = 0; i < time_result.length; i++) {

                String[] time = time_result[i].split(",");
                int year2 = Integer.parseInt(time[0]);
                int month2 = Integer.parseInt(time[1]);
                int day2 = Integer.parseInt(time[2]);

                calendar.set(year2, month2 - 1, day2);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);

            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(R.color.design_default_color_on_primary, calendarDays, getActivity()));
        }
    }
}
