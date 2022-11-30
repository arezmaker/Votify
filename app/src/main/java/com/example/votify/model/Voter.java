package com.example.votify.model;

import java.time.LocalDate;

public class Voter {

    private String name;
    private String cnic;
    private LocalDate expire_date;
    private LocalDate birth_date;
    private String typeofVote;
    private String votingArea;
    private String Address;

    public Voter() {
    }

    public Voter(String name, String cnic, LocalDate expire_date, LocalDate birth_date, String typeofVote, String votingArea, String address) {
        this.name = name;
        this.cnic = cnic;
        this.expire_date = expire_date;
        this.birth_date = birth_date;
        this.typeofVote = typeofVote;
        this.votingArea = votingArea;
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTypeofVote() {
        return typeofVote;
    }

    public void setTypeofVote(String typeofVote) {
        this.typeofVote = typeofVote;
    }

    public String getVotingArea() {
        return votingArea;
    }

    public void setVotingArea(String votingArea) {
        this.votingArea = votingArea;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public LocalDate getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(LocalDate expire_date) {
        this.expire_date = expire_date;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }
}
