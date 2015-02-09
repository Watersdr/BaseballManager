package edu.rosehulman.baseballmanager;


public class Team {
	private String name, logoURL;
	private long id;
	
	public Team(){}
	
	public Team(String name, String logoURL) {
		this.name = name;
		this.logoURL = logoURL;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogoURL() {
		return logoURL;
	}
	
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}
	
	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }
}
