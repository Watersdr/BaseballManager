package edu.rosehulman.baseballmanager;

import java.util.Date;

public class Game {
	private Date gameDate;
	private long id, homeID, awayID;
	
	public Game(){}
	
	public Game(Date gameDate, long homeID, long awayID) {
		this.gameDate = gameDate;
		this.homeID = homeID;
		this.awayID = awayID;
	}

	@SuppressWarnings("deprecation")
	public String getGameDate() { return gameDate.toLocaleString(); }

	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }

	public long getHomeID() { return homeID; }

	public long getAwayID() { return awayID; }

	@SuppressWarnings("deprecation")
	public void setGameDate(String gameDate) { this.gameDate = new Date(Date.parse(gameDate)); }

	public void setHomeID(long homeID) { this.homeID = homeID; }

	public void setAwayID(long awayID) { this.awayID = awayID; }
}
