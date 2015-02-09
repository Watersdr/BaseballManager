package edu.rosehulman.baseballmanager;

public class PlayerStats {
	private long id, playerID, gameID;
	private int battingOrder, abs, h, k, bb, e, ip, p_k, p_bb, p_r, p_er;
	
	public PlayerStats(long playerID, long gameID, int battingOrder, int abs, int h, int k, int bb, int e, int ip, int p_k, int p_bb, int p_r, int p_er) {
		this.playerID = playerID;
		this.gameID = gameID;
		this.battingOrder = battingOrder;
		this.abs = abs;
		this.h = h;
		this.k = k;
		this.bb = bb;
		this.e = e;
		this.ip = ip;
		this.p_k = p_k;
		this.p_bb = bb;
		this.p_r = p_r;
		this.p_er = p_er;
	}
	
	public long getID() { return id; }
    
    public void setID(long id) { this.id = id; }

	public long getPlayerID() {	return playerID; }

	public long getGameID() { return gameID; }

	public int getBattingOrder() { return battingOrder; }

	public int getAbs() { return abs; }

	public int getH() { return h; }

	public int getK() { return k; }

	public int getBb() { return bb; }

	public int getE() { return e; }

	public int getIp() { return ip; }

	public int getP_k() { return p_k; }

	public int getP_bb() { return p_bb; }

	public int getP_r() { return p_r; }

	public int getP_er() { return p_er; }

}