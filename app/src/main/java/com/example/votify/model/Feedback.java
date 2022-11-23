package com.example.votify.model;

public class Feedback {

    int id;
    String Subject;
    String Message;

    public Feedback(int id, String subject, String message) {
        this.id = id;
        Subject = subject;
        Message = message;
    }

    public Feedback() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
