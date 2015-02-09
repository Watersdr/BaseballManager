package edu.rosehulman.baseballmanager;

import android.graphics.Bitmap;


public class Team {
	private String name;
	private Bitmap logoBitmap;
	private long id;
	
	public Team(){}
	
	public Team(String name, Bitmap logoBitmap) {
		this.name = name;
		this.logoBitmap = logoBitmap;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Bitmap getLogoBitmap() {
		return logoBitmap;
	}
	
	public void setLogoBitmap(Bitmap logoBitmap) {
		this.logoBitmap = logoBitmap;
	}
	
	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }
}
