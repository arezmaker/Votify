package com.example.votify.model;

public class Admin {
    private String name;
    private String pass;
    private String feedback;

    public Admin()
    {
    }
    public Admin(String name,String pass,String feedback)
    {
        this.name=name;
        this.pass=pass;
        this.feedback=feedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
