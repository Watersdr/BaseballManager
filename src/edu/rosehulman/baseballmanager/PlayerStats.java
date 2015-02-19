package edu.rosehulman.baseballmanager;

public class PlayerStats {
	private long id, playerID, gameID;
	private int battingOrder, abs, h, k, bb, e, ip, p_k, p_bb, p_r, p_er;
	
	public PlayerStats() {}
	
	public PlayerStats(long playerID, long gameID, int battingOrder) {
		this(playerID, gameID, battingOrder, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

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
	
	public void setPlayerID(long playerID) { this.playerID = playerID; }

	public void setGameID(long gameID) { this.gameID = gameID; }

	public void setBattingOrder(int battingOrder) { this.battingOrder = battingOrder; }

	public void setAbs(int abs) { this.abs = abs; }

	public void setH(int h) { this.h = h; }

	public void setK(int k) { this.k = k; }

	public void setBb(int bb) { this.bb = bb; }

	public void setE(int e) { this.e = e; }

	public void setIp(int ip) { this.ip = ip; }

	public void setP_k(int p_k) { this.p_k = p_k; }

	public void setP_bb(int p_bb) { this.p_bb = p_bb; }

	public void setP_r(int p_r) { this.p_r = p_r; }

	public void setP_er(int p_er) { this.p_er = p_er;}
	
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