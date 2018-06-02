package com.example.farid.codersappdemo.Model;


public class UniversityModel {
    private String universityName;
    private String universityKey;

    public UniversityModel() {
    }

    public UniversityModel(String universityName, String universityKey) {
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
