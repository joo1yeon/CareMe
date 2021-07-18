package com.example.careme.network;

import com.example.careme.data.JoinData;
import com.example.careme.data.JoinResponse;
import com.example.careme.data.LoginData;
import com.example.careme.data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    // 인터페이스를 만들어 서버에 어떤 식으로 요청을 보내고 응답을 받을 것인지 정의
    @POST("/user/login") // POST 방식을 이용한 통신
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);
}
