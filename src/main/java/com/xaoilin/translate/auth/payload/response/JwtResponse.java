package com.xaoilin.translate.auth.payload.response;

public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String email;

    public JwtResponse(String accessToken, Long id, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
