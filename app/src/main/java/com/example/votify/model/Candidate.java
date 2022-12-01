package com.example.votify.model;

import java.time.LocalDate;

public class Candidate {

    private String votingArea;
    private LocalDate expiryDate;
    private LocalDate birthDate;
    private String party;
    private int candidateNo;
    private String typeofVote;
    private int castVote;
    private String CNIC;
    private String symbol;
    private String name;
    private int totalVote;
    private String Address;

    public Candidate(){

    }

    public Candidate(String name,String CNIC,String votingArea,LocalDate expiryDate,LocalDate birthDate,String party,int candidateNo,String typeofVote,int castVote, String symbol,int totalVote,String address) {
        this.name = name;
        this.CNIC=CNIC;
        this.votingArea=votingArea;
        this.expiryDate=expiryDate;
        this.birthDate=birthDate;
        this.party=party;
        this.candidateNo=candidateNo;
        this.typeofVote=typeofVote;
        this.castVote=castVote;
        this.symbol = symbol;
        this.totalVote=totalVote;
        this.Address=address;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(int totalVote) {
        this.totalVote = totalVote;
    }

    public String getVotingArea() {
        return votingArea;
    }

    public void setVotingArea(String votingArea) {
        this.votingArea = votingArea;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getCandidateNo() {
        return candidateNo;
    }

    public void setCandidateNo(int candidateNo) {
        this.candidateNo = candidateNo;
    }

    public String getTypeofVote() {
        return typeofVote;
    }

    public void setTypeofVote(String typeofVote) {
        this.typeofVote = typeofVote;
    }

    public int getCastVote() {
        return castVote;
    }

    public void setCastVote(int castVote) {
        this.castVote = castVote;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
