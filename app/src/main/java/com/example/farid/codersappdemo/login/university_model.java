package com.example.farid.codersappdemo.login;

public class university_model {
    private String universityName;
    private String universityKey;

    public university_model() {
    }

    public university_model(String universityName, String universityKey) {
        this.universityName = universityName;
        this.universityKey = universityKey;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityKey() {
        return universityKey;
    }

    public void setUniversityKey(String universityKey) {
        this.universityKey = universityKey;
    }
}
