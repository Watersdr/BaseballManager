package edu.rosehulman.baseballmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerStatsDataAdapter {
	public static final String TABLE_NAME = "playerstats";

	public static final String KEY_ID = "_id";
	public static final String KEY_PLAYER_ID = "player_id";
	public static final String KEY_GAME_ID = "game_id";
	public static final String KEY_BATTING_ORDER = "batting_order";
	public static final String KEY_ABS = "abs";
	public static final String KEY_H = "h";
	public static final String KEY_K = "k";
	public static final String KEY_BB = "b";
	public static final String KEY_E = "e";
	public static final String KEY_IP = "ip";
	public static final String KEY_P_K = "p_k";
	public static final String KEY_P_BB = "p_bb";
	public static final String KEY_P_R = "p_r";
	public static final String KEY_P_ER = "p_er";
	
	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;

	public boolean removePlayerStats(long id) {
	 	return mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null) > 0;
	}
	 
	public boolean removePlayerStats(PlayerStats ps) {
	 	return removePlayerStats(ps.getID());
	}
	
	public void updateScore(PlayerStats playerStats) {
		ContentValues row = getContentValuesFromPlayerStats(playerStats);
		String selection = KEY_ID + " = " + playerStats.getID();
		mDatabase.update(TABLE_NAME, row, selection, null);
	}
	
	public PlayerStats getPlayerStats(long id) {
	 	String[] projection = new String[] { KEY_ID, KEY_PLAYER_ID, KEY_GAME_ID, KEY_BATTING_ORDER, KEY_ABS, KEY_H, KEY_K, KEY_BB, KEY_E, KEY_IP, KEY_P_K, KEY_P_BB, KEY_P_R, KEY_P_ER };
	 	String selection = KEY_ID + " = " + id;
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_PLAYER_ID + " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return getPlayerStatsFromCursor(c);
	 	}
	 	return null;
	}
	 
	private PlayerStats getPlayerStatsFromCursor(Cursor c) {
		PlayerStats ps = new PlayerStats();
	 	ps.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
	 	ps.setPlayerID(c.getInt(c.getColumnIndexOrThrow(KEY_PLAYER_ID)));
	 	ps.setGameID(c.getInt(c.getColumnIndexOrThrow(KEY_GAME_ID)));
	 	ps.setBattingOrder(c.getInt(c.getColumnIndexOrThrow(KEY_BATTING_ORDER)));
	 	ps.setAbs(c.getInt(c.getColumnIndexOrThrow(KEY_ABS)));
	 	ps.setH(c.getInt(c.getColumnIndexOrThrow(KEY_H)));
	 	ps.setK(c.getInt(c.getColumnIndexOrThrow(KEY_K)));
	 	ps.setBb(c.getInt(c.getColumnIndexOrThrow(KEY_BB)));
	 	ps.setE(c.getInt(c.getColumnIndexOrThrow(KEY_E)));
	 	ps.setIp(c.getInt(c.getColumnIndexOrThrow(KEY_IP)));
	 	ps.setP_k(c.getInt(c.getColumnIndexOrThrow(KEY_P_K)));
	 	ps.setP_bb(c.getInt(c.getColumnIndexOrThrow(KEY_P_BB)));
	 	ps.setP_r(c.getInt(c.getColumnIndexOrThrow(KEY_P_R)));
	 	ps.setP_er(c.getInt(c.getColumnIndexOrThrow(KEY_P_ER)));
	 	return ps;
	}
	
	public PlayerStatsDataAdapter(Context context) {
		mOpenHelper = new DatabaseHelper(context);
	}
	
	public void open() {
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public long addPlayerStats(PlayerStats playerStats) {
	 	ContentValues row = getContentValuesFromPlayerStats(playerStats);
	 	long rowId = mDatabase.insert(TABLE_NAME, null, row);
	 	playerStats.setID(rowId);
	 	return rowId;
	}
	
	private ContentValues getContentValuesFromPlayerStats(PlayerStats playerStats) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_PLAYER_ID, playerStats.getPlayerID());
	 	row.put(KEY_GAME_ID, playerStats.getGameID());
	 	row.put(KEY_BATTING_ORDER, playerStats.getBattingOrder());
	 	row.put(KEY_ABS, playerStats.getAbs());
	 	row.put(KEY_H, playerStats.getH());
	 	row.put(KEY_K, playerStats.getK());
	 	row.put(KEY_BB, playerStats.getBb());
	 	row.put(KEY_E, playerStats.getE());
	 	row.put(KEY_IP, playerStats.getIp());
	 	row.put(KEY_P_K, playerStats.getP_k());
	 	row.put(KEY_P_BB, playerStats.getP_bb());
	 	row.put(KEY_P_R, playerStats.getP_r());
	 	row.put(KEY_P_ER, playerStats.getP_er());
	 	return row;
	}

	public Cursor getPlayerStatsCursor() {
	 	String[] projection = new String[] { KEY_ID, KEY_PLAYER_ID, KEY_GAME_ID, KEY_BATTING_ORDER, KEY_ABS, KEY_H, KEY_K, KEY_BB, KEY_E, KEY_IP, KEY_P_K, KEY_P_BB, KEY_P_R, KEY_P_ER };
	 	return mDatabase.query(TABLE_NAME, projection, null, null, null, null, KEY_PLAYER_ID + " DESC");
	}

}
