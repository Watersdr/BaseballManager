package edu.rosehulman.baseballmanager;

public class Player {
	private String name;
	private int number, dc_C, dc_P, dc_1B, dc_2B, dc_3B, dc_SS, dc_LF, dc_CF, dc_RF, battingOrder;
	private long id, teamID;
	
	public Player(String name, int number, long teamID) {
		this.name = name;
		this.number = number;
		this.teamID = teamID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() { return this.number; }
    
    public void setNumber(int number) { this.number = number; }

	public long getTeamID() { return teamID; }
	
	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }

}
