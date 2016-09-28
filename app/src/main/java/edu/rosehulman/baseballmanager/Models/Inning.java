package edu.rosehulman.baseballmanager.Models;

public class Inning {
	private long id, gameID;
	private int inning, homeRuns, awayRuns, homeHits, awayHits, homeErrors, awayErrors;
	
	public Inning() {}
	
	public Inning(long gameID, int inning) {
		this.gameID = gameID;
		this.inning = inning;
		this.homeRuns = 0;
		this.homeHits = 0;
		this.homeErrors = 0;
		this.awayRuns = 0;
		this.awayHits = 0; 
		this.awayErrors = 0;
	}
	
	public long getID() { return id; }
	
	public void setID(long id) { this.id = id; }
	
	public long getGameID() { return gameID; }
	
	public void setGameID(long gameID) { this.gameID = gameID; }
	
	public int getInning() { return inning; }
	
	public void setInning(int inning) { this.inning = inning; }
	
	public int getHomeRuns() { return homeRuns; }
	
	public void setHomeRuns(int homeRuns) { this.homeRuns = homeRuns; }
	
	public int getAwayRuns() { return awayRuns; }
	
	public void setAwayRuns(int awayRuns) { this.awayRuns = awayRuns; }
	
	public int getHomeHits() { return homeHits; }
	
	public void setHomeHits(int homeHits) { this.homeHits = homeHits; }
	
	public int getAwayHits() { return awayHits; }
	
	public void setAwayHits(int awayHits) { this.awayHits = awayHits; }
	
	public int getHomeErrors() { return homeErrors; }
	
	public void setHomeErrors(int homeErrors) { this.homeErrors = homeErrors; }
	
	public int getAwayErrors() { return awayErrors; }
	
	public void setAwayErrors(int awayErrors) { this.awayErrors = awayErrors; }
}
