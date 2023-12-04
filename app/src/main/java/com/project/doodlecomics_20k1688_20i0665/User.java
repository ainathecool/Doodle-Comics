package com.project.doodlecomics_20k1688_20i0665;

public class User {

        public String emailORNum;
        public String additionalInfo;

        public String number;

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
