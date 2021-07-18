package com.example.careme.data;

import com.google.gson.annotations.SerializedName;

public class LoginData { // 로그인 요청 시 보낼 데이터

    @SerializedName("userId")
    String userId;

    @SerializedName("userPwd")
    String userPwd;

    public LoginData(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }
}
