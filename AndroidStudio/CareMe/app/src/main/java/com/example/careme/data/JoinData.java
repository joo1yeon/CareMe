package com.example.careme.data;

import com.google.gson.annotations.SerializedName;

public class JoinData { // 회원가입 요청 시 보낼 데이터
    @SerializedName("userName")
    private String userName;

    @SerializedName("userId")
    private String userId;

    @SerializedName("userPwd")
    private String userPwd;

    public JoinData(String userName, String userId, String userPwd) {
        this.userName = userName;
        this.userId = userId;
        this.userPwd = userPwd;
    }
}
