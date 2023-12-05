package com.project.doodlecomics_20k1688_20i0665;

public class User {

        public String emailORNum;
        public String additionalInfo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String emailORNum, String additionalInfo, String username, String number, String password, String bio, String socialMedia) {
        this.emailORNum = emailORNum;
        this.additionalInfo = additionalInfo;
        this.username = username;
        this.number = number;
        this.password = password;
        this.bio = bio;
        this.socialMedia = socialMedia;
    }

    public String username;

    public User(String emailORNum, String additionalInfo, String number, String password, String bio, String socialMedia) {
        this.emailORNum = emailORNum;
        this.additionalInfo = additionalInfo;
        this.number = number;
        this.password = password;
        this.bio = bio;
        this.socialMedia = socialMedia;
    }

    public String number;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String password;
        public String bio;
        public String socialMedia;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String emailORNum, String additionalInfo) {
            this.emailORNum = emailORNum;
            this.additionalInfo = additionalInfo;
        }

        public User (String additionalInfo)
        {
            this.additionalInfo = additionalInfo;
        }

}
