package com.example.votify.model;

public class Party {

    private String name;

    public Party(String name, String symbol, int noOfCandidates, int totalVote, String chairman) {
        this.name = name;
        this.symbol = symbol;
        this.noOfCandidates = noOfCandidates;
        this.totalVote = totalVote;
        Chairman = chairman;
    }

    private String symbol;
    private int noOfCandidates;
    private int totalVote;
    private String Chairman;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getNoOfCandidates() {
        return noOfCandidates;
    }

    public void setNoOfCandidates(int noOfCandidates) {
        this.noOfCandidates = noOfCandidates;
    }

    public int getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(int totalVote) {
        this.totalVote = totalVote;
    }

    public String getChairman() {
        return Chairman;
    }

    public void setChairman(String chairman) {
        Chairman = chairman;
    }

}
