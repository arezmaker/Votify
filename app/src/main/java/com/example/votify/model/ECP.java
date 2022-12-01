package com.example.votify.model;

import java.time.LocalDate;

public class ECP {
    private String name;
    private String pass;
    private int noofVotes;
    private int noofParty;
    private LocalDate electionDate;

    public ECP()
    {
    }
//    public ECP(String name,String pass,int noofVotes,int noofParty,LocalDate electionDate)
//    {
//        this.name=name;
//        this.pass=pass;
//        this.noofParty=noofParty;
//        this.noofVotes=noofVotes;
//        this.electionDate=electionDate;
//    }

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

    public int getNoofVotes() {
        return noofVotes;
    }

    public void setNoofVotes(int noofVotes) {
        this.noofVotes = noofVotes;
    }

    public int getNoofParty() {
        return noofParty;
    }

    public void setNoofParty(int noofParty) {
        this.noofParty = noofParty;
    }

    public LocalDate getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(LocalDate electionDate) {
        this.electionDate = electionDate;
    }
}
