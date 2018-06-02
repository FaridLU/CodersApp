package com.example.farid.codersappdemo;

import java.io.Serializable;

public class user_profile_activity implements Serializable {
    public String cf_rating = "null", cf_totalSubmissions = "null", cf_typeOfCoder = "null", cf_maxRank = "null";
    public String cc_rating = "null", cc_totalSolved = "null", cc_typeOfCoder = "null", cc_countryRank = "null";

    public user_profile_activity() {

    }

    public user_profile_activity(String cf_rating, String cf_totalSubmissions, String cf_typeOfCoder, String cf_maxRank, String cc_rating, String cc_totalSolved, String cc_typeOfCoder, String cc_countryRank) {
        this.cf_rating = cf_rating;
        this.cf_totalSubmissions = cf_totalSubmissions;
        this.cf_typeOfCoder = cf_typeOfCoder;
        this.cf_maxRank = cf_maxRank;
        this.cc_rating = cc_rating;
        this.cc_totalSolved = cc_totalSolved;
        this.cc_typeOfCoder = cc_typeOfCoder;
        this.cc_countryRank = cc_countryRank;
    }

    public String getCf_rating() {
        return cf_rating;

    }

    public void setCf_rating(String cf_rating) {
        this.cf_rating = cf_rating;
    }

    public void setCf_totalSubmissions(String cf_totalSubmissions) {
        this.cf_totalSubmissions = cf_totalSubmissions;
    }

    public void setCf_typeOfCoder(String cf_typeOfCoder) {
        this.cf_typeOfCoder = cf_typeOfCoder;
    }

    public void setCf_maxRank(String cf_maxRank) {
        this.cf_maxRank = cf_maxRank;
    }

    public void setCc_rating(String cc_rating) {
        this.cc_rating = cc_rating;
    }

    public void setCc_totalSolved(String cc_totalSolved) {
        this.cc_totalSolved = cc_totalSolved;
    }

    public void setCc_typeOfCoder(String cc_typeOfCoder) {
        this.cc_typeOfCoder = cc_typeOfCoder;
    }

    public void setCc_countryRank(String cc_countryRank) {
        this.cc_countryRank = cc_countryRank;
    }

    public String getCf_totalSubmissions() {

        return cf_totalSubmissions;
    }

    public String getCf_typeOfCoder() {
        return cf_typeOfCoder;
    }

    public String getCf_maxRank() {
        return cf_maxRank;
    }

    public String getCc_rating() {
        return cc_rating;
    }

    public String getCc_totalSolved() {
        return cc_totalSolved;
    }

    public String getCc_typeOfCoder() {
        return cc_typeOfCoder;
    }

    public String getCc_countryRank() {
        return cc_countryRank;
    }
}
