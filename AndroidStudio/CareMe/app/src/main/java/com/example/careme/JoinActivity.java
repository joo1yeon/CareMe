package com.example.careme;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.careme.data.JoinData;
import com.example.careme.data.JoinResponse;
import com.example.careme.network.RetrofitClient;
import com.example.careme.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    Button btn_join;
    EditText et_id, et_pwd, et_pwdConfirm, et_name;
    private ServiceApi serviceApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        et_pwdConfirm = findViewById(R.id.et_pwdConfirm);
        et_name = findViewById(R.id.et_name);
        btn_join = findViewById(R.id.btn_join);


        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
                startJoin(new JoinData(et_name.getText().toString(), et_id.getText().toString(), et_pwdConfirm.getText().toString()));
            }
        });
    }

    private void startJoin(JoinData data) {
        serviceApi.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    Toast.makeText(JoinActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("join", "회원가입 에러 " + t.getMessage());
            }
        });
    }
}
