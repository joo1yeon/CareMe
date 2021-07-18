package com.example.careme.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse { // 로그인 요청에 대한 응답으로 돌아올 데이터

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private int userId;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }
}
