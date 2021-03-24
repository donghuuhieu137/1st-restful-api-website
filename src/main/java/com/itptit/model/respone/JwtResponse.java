package com.itptit.model.respone;

public class JwtResponse {
    private String jwt;

    private String gmail;

    private String name;

    private String role;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JwtResponse(String jwt, String gmail, String name, String role) {
        this.jwt = jwt;
        this.gmail = gmail;
        this.name = name;
        this.role = role;
    }

    public JwtResponse() {
    }
}

