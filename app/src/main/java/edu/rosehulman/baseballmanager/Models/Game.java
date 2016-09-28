package edu.rosehulman.baseballmanager.Models;

import java.text.SimpleDateFormat;
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
	public String getGameDate() { 
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return f.format(gameDate);
	}
	
	public Date getGameDateAsDate() {
		return gameDate;
	}

	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }

	public long getHomeID() { return homeID; }

	public long getAwayID() { return awayID; }

	@SuppressWarnings("deprecation")
	public void setGameDate(String gameDate) { 
		String[] fullDate = gameDate.split(" ");
		String[] date = fullDate[0].split("-");
		String[] time = fullDate[1].split(":");
		this.gameDate = new Date(Integer.parseInt(date[0]) - 1900, Integer.parseInt(date[1]), Integer.parseInt(date[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1])); 
	}

	public void setHomeID(long homeID) { this.homeID = homeID; }

	public void setAwayID(long awayID) { this.awayID = awayID; }
}
