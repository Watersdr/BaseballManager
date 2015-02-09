package edu.rosehulman.baseballmanager;


public class Team {
	private String name;
	private byte[] logo;
	private long id;
	
	public Team(){}
	
	public Team(String name, byte[] logo) {
		this.name = name;
		this.logo = logo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte[] getLogo() {
		return logo;
	}
	
	public void setLogo(byte[] bs) {
		this.logo = bs;
	}
	
	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }
}
