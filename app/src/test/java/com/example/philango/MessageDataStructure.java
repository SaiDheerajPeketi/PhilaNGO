package com.example.philango;

public class MessageDataStructure {
    String UserID;
    String Name;
    String Goal;

    public MessageDataStructure(String userID,String name,String goal) {
        UserID = userID;
        Name = name;
        Goal = goal;
    }


    public String getGoal() {
        return Goal;
    }

    public void setGoal(String goal) {
        Goal = goal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
