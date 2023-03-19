package com.example.philango;

import java.io.Serializable;


public class UserDetails implements Serializable {
    String email;
    String displayName;
    String role;

    public UserDetails(String email, String displayName, String role) {
        this.email = email;
        this.displayName = displayName;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
