package com.example.careme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {

    private ArrayList<String> mData;

    // ViewHolder: itemView 저장
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            // define click listener for the ViewHolder's View

            // 뷰 객체에 대한 참조
            textView = itemView.findViewById(R.id.tv_pitem);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    // adapter의 dataset 초기화
    public PrescriptionAdapter(ArrayList<String> list) {
        mData = list;
    }

    // 새로운 view 생성 (layout manager에 의해 호출)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // list item의 UI를 정의하는 새로운 view 생성
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.prescription_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // view의 내용을 바꿈 (layout manager에 의해 호출)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // dataset에서 element를 position으로 가져오고 내용을 변경한다.
        viewHolder.getTextView().setText(mData.get(position));
    }

    // dataset의 크기를 return (layout manager에 의해 호출)
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
