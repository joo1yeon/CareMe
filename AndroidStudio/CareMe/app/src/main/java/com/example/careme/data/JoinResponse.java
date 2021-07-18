package com.example.careme.data;

import com.google.gson.annotations.SerializedName;

public class JoinResponse { // 회원가입 요청에 대한 응답으로 돌아올 데이터

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
