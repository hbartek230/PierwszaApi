package com.example.bhren.myapplication.Model;

public class Users {
    private String email;
    private String password;
    private String chmod;

    public Users(){

    }

    public Users(String email, String password, String chmod) {
        this.email = email;
        this.password = password;
        this.chmod = chmod;
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

    public String getChmod(){
        return chmod;
    }

    public void setChmod(String chmod){
        this.chmod = chmod;
    }
}
