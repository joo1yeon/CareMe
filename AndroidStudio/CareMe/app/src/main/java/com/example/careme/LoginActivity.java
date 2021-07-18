package com.example.careme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.careme.data.LoginData;
import com.example.careme.data.LoginResponse;
import com.example.careme.network.RetrofitClient;
import com.example.careme.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btn_login;
    TextView tv_join;
    EditText et_id, et_pwd;
    //ProgressBar progressBar;
    private ServiceApi serviceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        tv_join = findViewById(R.id.tv_join);
        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceApi = RetrofitClient.getClient().create(ServiceApi.class);
                attemptLogin();
            }
        });

        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    private void attemptLogin() {

        //et_id.setError(null);
        //et_pwd.setError(null);

        String inputId = et_id.getText().toString();
        String inputPwd = et_pwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (inputPwd.isEmpty()) {
            et_pwd.setError("비밀번호를 입력해주세요.");
            focusView = et_pwd;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(inputId, inputPwd));
            //showProgress(true);
        }
    }

    private void startLogin(LoginData data) {
        serviceApi.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                //showProgress(false);
                finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("login", t.getMessage());
                //showProgress(false);
                t.printStackTrace(); // 에러 발생 시 에러 발생 원인 단계별로 출력해줌
            }
        });
    }

//    private void showProgress(boolean show) {
//        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
//    }
}
