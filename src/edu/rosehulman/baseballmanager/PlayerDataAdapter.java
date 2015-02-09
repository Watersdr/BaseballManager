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
	public static final String KEY_F_NAME = "f_name";
	public static final String KEY_L_NAME = "l_name";
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
	private Player mPlayer;

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
	 	String[] projection = new String[] { KEY_ID, KEY_TEAM_ID, KEY_F_NAME, KEY_L_NAME, KEY_NUMBER, KEY_DC_C, KEY_DC_P, KEY_DC_1B, KEY_DC_2B, KEY_DC_3B, KEY_DC_SS, KEY_DC_LF, KEY_DC_CF, KEY_DC_RF, KEY_BATTING_ORDER };
	 	String selection = KEY_ID + " = " + id;
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_L_NAME + " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return getPlayerFromCursor(c);
	 	}
	 	return null;
	}
	
	public Cursor getTeamPlayers(long teamID) {
	 	String[] projection = new String[] { KEY_ID, KEY_TEAM_ID, KEY_F_NAME, KEY_L_NAME, KEY_NUMBER, KEY_DC_C, KEY_DC_P, KEY_DC_1B, KEY_DC_2B, KEY_DC_3B, KEY_DC_SS, KEY_DC_LF, KEY_DC_CF, KEY_DC_RF, KEY_BATTING_ORDER };
	 	String selection = KEY_TEAM_ID + " = " + teamID;
	 	return mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_BATTING_ORDER + " DESC");
	}
	 
	private Player getPlayerFromCursor(Cursor c) {
		Player p = new Player();
	 	p.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
	 	p.setTeamID(c.getInt(c.getColumnIndexOrThrow(KEY_TEAM_ID)));
	 	p.setFName(c.getString(c.getColumnIndexOrThrow(KEY_F_NAME)));
	 	p.setLName(c.getString(c.getColumnIndexOrThrow(KEY_L_NAME)));
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
		mOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	public void open() {
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public long addPlayer(Player player) {
		mPlayer = player;
		setPositions();
	 	ContentValues row = getContentValuesFromPlayer(player);
	 	long rowId = mDatabase.insert(TABLE_NAME, null, row);
	 	player.setID(rowId);
	 	return rowId;
	}
	
	private ContentValues getContentValuesFromPlayer(Player player) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_TEAM_ID, player.getTeamID());
	 	row.put(KEY_F_NAME, player.getFName());
	 	row.put(KEY_L_NAME, player.getLName());
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
	
	private void setPositions() {
		Cursor c = mDatabase.rawQuery(
				"SELECT MAX(" + KEY_DC_P + ") + MAX(" + KEY_DC_C + ") + MAX(" + KEY_DC_1B + ") + MAX(" + KEY_DC_2B + ") + MAX(" + KEY_DC_3B + ") + MAX(" + KEY_DC_SS + ") + MAX(" + KEY_DC_LF + ") + MAX(" + KEY_DC_CF + ") + MAX(" + KEY_DC_RF + ") + MAX(" + KEY_BATTING_ORDER + ") FROM " + TABLE_NAME + " WHERE " + KEY_TEAM_ID + "=?", 
				new String[] { mPlayer.getTeamID()+"" }
		);
		mPlayer.setDc_P(mPlayer.getDc_P() == 0 ? (c.isNull(0) ? 0 : c.getInt(0)) : mPlayer.getDc_P() );
		mPlayer.setDc_C(mPlayer.getDc_C() == 0 ? (c.isNull(1) ? 0 : c.getInt(1)) : mPlayer.getDc_C() );
		mPlayer.setDc_1B(mPlayer.getDc_1B() == 0 ? (c.isNull(2) ? 0 : c.getInt(2)) : mPlayer.getDc_1B() );
		mPlayer.setDc_2B(mPlayer.getDc_2B() == 0 ? (c.isNull(3) ? 0 : c.getInt(3)) : mPlayer.getDc_2B() );
		mPlayer.setDc_3B(mPlayer.getDc_3B() == 0 ? (c.isNull(4) ? 0 : c.getInt(4)) : mPlayer.getDc_3B() );
		mPlayer.setDc_SS(mPlayer.getDc_SS() == 0 ? (c.isNull(5) ? 0 : c.getInt(5)) : mPlayer.getDc_SS() );
		mPlayer.setDc_LF(mPlayer.getDc_LF() == 0 ? (c.isNull(6) ? 0 : c.getInt(6)) : mPlayer.getDc_LF() );
		mPlayer.setDc_CF(mPlayer.getDc_CF() == 0 ? (c.isNull(7) ? 0 : c.getInt(7)) : mPlayer.getDc_CF() );
		mPlayer.setDc_RF(mPlayer.getDc_RF() == 0 ? (c.isNull(8) ? 0 : c.getInt(8)) : mPlayer.getDc_RF() );
		mPlayer.setBattingOrder(mPlayer.getBattingOrder() == -1 ? c.getInt(9) : mPlayer.getBattingOrder() );
		
	}

	public Cursor getPlayersCursor() {
		String[] projection = new String[] { KEY_ID, KEY_TEAM_ID, KEY_F_NAME, KEY_L_NAME, KEY_NUMBER, KEY_DC_C, KEY_DC_P, KEY_DC_1B, KEY_DC_2B, KEY_DC_3B, KEY_DC_SS, KEY_DC_LF, KEY_DC_CF, KEY_DC_RF, KEY_BATTING_ORDER };
	 	return mDatabase.query(TABLE_NAME, projection, null, null, null, null, KEY_L_NAME + " DESC");
	}

}