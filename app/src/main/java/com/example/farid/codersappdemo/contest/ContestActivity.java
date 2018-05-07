package com.example.farid.codersappdemo.contest;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ContestActivity implements Serializable{
    public String start_date, start_information, end_date, end_information, contest_name, contest_link, Judge;
    public int contest_image, contest_type;

    public ContestActivity(String start_date, String start_information, String end_date, String end_information, String contest_name, String contest_link, int contest_image, int contest_type, String Judge) {
        this.start_date = start_date;
        this.start_information = start_information;
        this.end_date = end_date;
        this.end_information = end_information;
        this.contest_name = contest_name;
        this.contest_image = contest_image;
        this.contest_type = contest_type;
        this.contest_link = contest_link;
        this.Judge = Judge;
    }

    public String getJudge() {
        return Judge;
    }

    public void setContest_name(String contest_name) {
        this.contest_name = contest_name;
    }

    public void setContest_link(String contest_link) {
        this.contest_link = contest_link;
    }

    public void setContest_type(int contest_type) {
        this.contest_type = contest_type;
    }

    public String getContest_link() {

        return contest_link;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getStart_information() {
        return start_information;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getEnd_information() {
        return end_information;
    }

    public int getContest_type() {
        return contest_type;
    }

    public int getContest_image() {
        return contest_image;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setStart_information(String start_information) {
        this.start_information = start_information;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setEnd_information(String end_information) {
        this.end_information = end_information;
    }

    public void setContest_image(int contest_image) {
        this.contest_image = contest_image;
    }

    public String getContest_name() {
        return contest_name;
    }

}
