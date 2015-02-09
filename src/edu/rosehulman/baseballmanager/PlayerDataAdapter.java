package edu.rosehulman.baseballmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerDataAdapter {
	public static final String TABLE_NAME = "players";

	public static final String KEY_ID = "_id";
	public static final String KEY_TEAM_ID = "player_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_DC_C = "dc_c";
	public static final String KEY_DC_P = "dc_p";
	public static final String KEY_DC_1B = "dc_1b";
	public static final String KEY_DC_2B = "dc_2b";
	public static final String KEY_DC_3B = "dc_3b";
	public static final String KEY_DC_SS = "dc_ss";
	public static final String KEY_DC_LF = "dc_lf";
	public static final String KEY_DC_CF = "dc_cf";
	public static final String KEY_DC_RF = "dc_rf";
	public static final String KEY_BATTING_ORDER = "batting_order";

	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;

	public boolean removePlayer(long id) {
	 	return mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null) > 0;
	}
	 
	public boolean removePlayer(Player p) {
	 	return removePlayer(p.getID());
	}
	
	public void updateScore(Player player) {
		ContentValues row = getContentValuesFromPlayer(player);
		String selection = KEY_ID + " = " + player.getID();
		mDatabase.update(TABLE_NAME, row, selection, null);
	}
	
	public Player getPlayer(long id) {
	 	String[] projection = new String[] { KEY_ID, KEY_TEAM_ID, KEY_NAME, KEY_NUMBER, KEY_DC_C, KEY_DC_P, KEY_DC_1B, KEY_DC_2B, KEY_DC_3B, KEY_DC_SS, KEY_DC_LF, KEY_DC_CF, KEY_DC_RF, KEY_BATTING_ORDER };
	 	String selection = KEY_ID + " = " + id;
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_NAME + " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return getPlayerFromCursor(c);
	 	}
	 	return null;
	}
	 
	private Player getPlayerFromCursor(Cursor c) {
		Player p = new Player();
	 	p.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
	 	p.setTeamID(c.getInt(c.getColumnIndexOrThrow(KEY_TEAM_ID)));
	 	p.setName(c.getString(c.getColumnIndexOrThrow(KEY_NAME)));
	 	p.setNumber(c.getInt(c.getColumnIndexOrThrow(KEY_NUMBER)));
	 	p.setDc_C(c.getInt(c.getColumnIndexOrThrow(KEY_DC_C)));
	 	p.setDc_P(c.getInt(c.getColumnIndexOrThrow(KEY_DC_P)));
	 	p.setDc_1B(c.getInt(c.getColumnIndexOrThrow(KEY_DC_1B)));
	 	p.setDc_2B(c.getInt(c.getColumnIndexOrThrow(KEY_DC_2B)));
	 	p.setDc_3B(c.getInt(c.getColumnIndexOrThrow(KEY_DC_3B)));
	 	p.setDc_SS(c.getInt(c.getColumnIndexOrThrow(KEY_DC_SS)));
	 	p.setDc_LF(c.getInt(c.getColumnIndexOrThrow(KEY_DC_LF)));
	 	p.setDc_CF(c.getInt(c.getColumnIndexOrThrow(KEY_DC_CF)));
	 	p.setDc_RF(c.getInt(c.getColumnIndexOrThrow(KEY_DC_RF)));
	 	p.setBattingOrder(c.getInt(c.getColumnIndexOrThrow(KEY_BATTING_ORDER)));
	 	return p;
	}
	
	public PlayerDataAdapter(Context context) {
		mOpenHelper = new DatabaseHelper(context);
	}
	
	public void open() {
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public long addPlayer(Player player) {
	 	ContentValues row = getContentValuesFromPlayer(player);
	 	long rowId = mDatabase.insert(TABLE_NAME, null, row);
	 	player.setID(rowId);
	 	return rowId;
	}
	
	private ContentValues getContentValuesFromPlayer(Player player) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_TEAM_ID, player.getTeamID());
	 	row.put(KEY_NAME, player.getName());
	 	row.put(KEY_NUMBER, player.getNumber());
	 	row.put(KEY_DC_C, player.getDc_C());
	 	row.put(KEY_DC_P, player.getDc_P());
	 	row.put(KEY_DC_1B, player.getDc_1B());
	 	row.put(KEY_DC_2B, player.getDc_2B());
	 	row.put(KEY_DC_3B, player.getDc_3B());
	 	row.put(KEY_DC_SS, player.getDc_SS());
	 	row.put(KEY_DC_LF, player.getDc_LF());
	 	row.put(KEY_DC_CF, player.getDc_CF());
	 	row.put(KEY_DC_RF, player.getDc_RF());
	 	row.put(KEY_BATTING_ORDER, player.getBattingOrder());
	 	return row;
	}

	public Cursor getPlayersCursor() {
	 	String[] projection = new String[] { KEY_ID, KEY_TEAM_ID, KEY_NAME, KEY_NUMBER, KEY_DC_C, KEY_DC_P, KEY_DC_1B, KEY_DC_2B, KEY_DC_3B, KEY_DC_SS, KEY_DC_LF, KEY_DC_CF, KEY_DC_RF, KEY_BATTING_ORDER };
	 	return mDatabase.query(TABLE_NAME, projection, null, null, null, null, KEY_NAME + " DESC");
	}

}