package edu.rosehulman.baseballmanager;

public class Player {
	private String fName, lName;
	private int number, dc_C, dc_P, dc_1B, dc_2B, dc_3B, dc_SS, dc_LF, dc_CF, dc_RF, battingOrder;
	private long id, teamID;
	
	public Player() {}
	
	public Player(String fName, String lName, int number, long teamID) {
		this.fName = fName;
		this.lName = lName;
		this.number = number;
		this.teamID = teamID;
	}
	
	public String getFName() { return fName; }
	
	public String getLName() { return lName; }
	
	public int getDc_C() { return dc_C; }

	public void setDc_C(int dc_C) { this.dc_C = dc_C; }

	public int getDc_P() { return dc_P; }

	public void setDc_P(int dc_P) { this.dc_P = dc_P; }

	public int getDc_1B() { return dc_1B; }

	public void setDc_1B(int dc_1b) { dc_1B = dc_1b; }

	public int getDc_2B() { return dc_2B; }

	public void setDc_2B(int dc_2b) { dc_2B = dc_2b; }

	public int getDc_3B() { return dc_3B; }

	public void setDc_3B(int dc_3b) { dc_3B = dc_3b; }

	public int getDc_SS() { return dc_SS; }

	public void setDc_SS(int dc_SS) { this.dc_SS = dc_SS; }

	public int getDc_LF() { return dc_LF; }

	public void setDc_LF(int dc_LF) { this.dc_LF = dc_LF; }

	public int getDc_CF() { return dc_CF; }

	public void setDc_CF(int dc_CF) { this.dc_CF = dc_CF; }

	public int getDc_RF() { return dc_RF; }

	public void setDc_RF(int dc_RF) { this.dc_RF = dc_RF; }

	public int getBattingOrder() { return battingOrder; }

	public void setBattingOrder(int battingOrder) { this.battingOrder = battingOrder; }

	public void setTeamID(long teamID) { this.teamID = teamID; }

	public void setFName(String name) { this.fName = name; }

	public void setLName(String name) { this.lName = name; }

	public int getNumber() { return this.number; }
    
    public void setNumber(int number) { this.number = number; }

	public long getTeamID() { return teamID; }
	
	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }

}
