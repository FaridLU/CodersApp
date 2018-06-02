package com.example.farid.codersappdemo.Model;


public class UserModel {
    private String userName;
    private String universityName;
    private String userId;
    private String profilePic;
    private String uvaHandle;
    private String cfHandle;
    private String codechefHandle;

    public UserModel() {
    }

    public UserModel(String userName, String universityName, String userId, String profilePic, String uvaHandle, String cfHandle, String codechefHandle) {
        this.userName = userName;
        this.universityName = universityName;
        this.userId = userId;
        this.profilePic = profilePic;
        this.uvaHandle = uvaHandle;
        this.cfHandle = cfHandle;
        this.codechefHandle = codechefHandle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUvaHandle() {
        return uvaHandle;
    }

    public void setUvaHandle(String uvaHandle) {
        this.uvaHandle = uvaHandle;
    }

    public String getCfHandle() {
        return cfHandle;
    }

    public void setCfHandle(String cfHandle) {
        this.cfHandle = cfHandle;
    }

    public String getCodechefHandle() {
        return codechefHandle;
    }

    public void setCodechefHandle(String codechefHandle) {
        this.codechefHandle = codechefHandle;
    }
}