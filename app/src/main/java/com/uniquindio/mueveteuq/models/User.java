package com.uniquindio.mueveteuq.models;

public class User {

    private String email, password, nickname;
    private int accumPoints;

    public User(){

    }

    public User(String email, String password, String nickname, int accumPoints) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.accumPoints = accumPoints;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAccumPoints() {
        return accumPoints;
    }

    public void setAccumPoints(int accumPoints) {
        this.accumPoints = accumPoints;
    }
}
