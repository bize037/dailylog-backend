package com.project.dailylog.common.constants;

public enum Authorization {
    AUTHORIZE_USER("USER"),
    FORMAT_TYPE("Bearer");

    private final String auth;

    Authorization(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }
}
