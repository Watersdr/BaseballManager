package edu.rosehulman.baseballmanager.Models;

public class Player {
    private String firstName, lastName;
    private int number, depthChartC, depthChartP, depthChart1B, depthChart2B,
            depthChart3B, depthChartSS, depthChartLF, depthChartCF, depthChartRF,
            depthChartDH, battingOrder;
    private long id, teamID;

    public Player() {
    }

    public Player(String firstName, String lastName, int number, long teamID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.teamID = teamID;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDepthChartC() {
        return depthChartC;
    }

    public void setDepthChartC(int depthChartC) {
        this.depthChartC = depthChartC;
    }

    public int getDepthChartP() {
        return depthChartP;
    }

    public void setDepthChartP(int depthChartP) {
        this.depthChartP = depthChartP;
    }

    public int getDepthChart1B() {
        return depthChart1B;
    }

    public void setDepthChart1B(int depthChart1b) {
        depthChart1B = depthChart1b;
    }

    public int getDepthChart2B() {
        return depthChart2B;
    }

    public void setDepthChart2B(int depthChart2b) {
        depthChart2B = depthChart2b;
    }

    public int getDepthChart3B() {
        return depthChart3B;
    }

    public void setDepthChart3B(int depthChart3b) {
        depthChart3B = depthChart3b;
    }

    public int getDepthChartSS() {
        return depthChartSS;
    }

    public void setDepthChartSS(int depthChartSS) {
        this.depthChartSS = depthChartSS;
    }

    public int getDepthChartLF() {
        return depthChartLF;
    }

    public void setDepthChartLF(int depthChartLF) {
        this.depthChartLF = depthChartLF;
    }

    public int getDepthChartCF() {
        return depthChartCF;
    }

    public void setDepthChartCF(int depthChartCF) {
        this.depthChartCF = depthChartCF;
    }

    public int getDepthChartRF() {
        return depthChartRF;
    }

    public void setDepthChartRF(int depthChartRF) {
        this.depthChartRF = depthChartRF;
    }

    public int getDepthChartDH() {
        return depthChartDH;
    }

    public void setDepthChartDH(int depthChartDH) {
        this.depthChartDH = depthChartDH;
    }

    public int getBattingOrder() {
        return battingOrder;
    }

    public void setBattingOrder(int battingOrder) {
        this.battingOrder = battingOrder;
    }

    public void setTeamID(long teamID) {
        this.teamID = teamID;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTeamID() {
        return teamID;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }
}
