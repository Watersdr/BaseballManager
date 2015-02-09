package edu.rosehulman.baseballmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InningDataAdapter {
	public static final String TABLE_NAME = "innings";

	public static final String KEY_ID = "_id";
	public static final String KEY_GAME_ID = "game_id";
	public static final String KEY_INNING = "inning";
	public static final String KEY_HOME_RUNS = "home_runs";
	public static final String KEY_AWAY_RUNS = "away_runs";
	public static final String KEY_HOME_HITS = "home_hits";
	public static final String KEY_AWAY_HITS = "away_hits";
	public static final String KEY_HOME_ERRORS = "home_errors";
	public static final String KEY_AWAY_ERRORS = "away_errors";
	
	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;

	public boolean removeInning(long id) {
	 	return mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null) > 0;
	}
	 
	public boolean removeInning(Inning i) {
	 	return removeInning(i.getID());
	}
	
	public void updateScore(Inning inning) {
		ContentValues row = getContentValuesFromInning(inning);
		String selection = KEY_ID + " = " + inning.getID();
		mDatabase.update(TABLE_NAME, row, selection, null);
	}
	
	public Inning getInning(long id) {
	 	String[] projection = new String[] { KEY_ID, KEY_GAME_ID, KEY_INNING, KEY_HOME_RUNS, KEY_AWAY_RUNS, KEY_HOME_HITS, KEY_AWAY_HITS, KEY_HOME_ERRORS, KEY_AWAY_ERRORS };
	 	String selection = KEY_ID + " = " + id;
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_GAME_ID + " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return getInningFromCursor(c);
	 	}
	 	return null;
	}
	 
	private Inning getInningFromCursor(Cursor c) {
		Inning i = new Inning();
	 	i.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
	 	i.setGameID(c.getInt(c.getColumnIndexOrThrow(KEY_GAME_ID)));
	 	i.setInning(c.getInt(c.getColumnIndexOrThrow(KEY_INNING)));
	 	i.setHomeRuns(c.getInt(c.getColumnIndexOrThrow(KEY_HOME_RUNS)));
	 	i.setAwayRuns(c.getInt(c.getColumnIndexOrThrow(KEY_AWAY_RUNS)));
	 	i.setHomeHits(c.getInt(c.getColumnIndexOrThrow(KEY_HOME_HITS)));
	 	i.setAwayHits(c.getInt(c.getColumnIndexOrThrow(KEY_AWAY_HITS)));
	 	i.setHomeErrors(c.getInt(c.getColumnIndexOrThrow(KEY_HOME_ERRORS)));
	 	i.setAwayErrors(c.getInt(c.getColumnIndexOrThrow(KEY_AWAY_ERRORS)));
	 	return i;
	}
	
	public InningDataAdapter(Context context) {
		mOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	public void open() {
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public long addInning(Inning inning) {
	 	ContentValues row = getContentValuesFromInning(inning);
	 	long rowId = mDatabase.insert(TABLE_NAME, null, row);
	 	inning.setID(rowId);
	 	return rowId;
	}
	
	private ContentValues getContentValuesFromInning(Inning inning) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_GAME_ID, inning.getGameID());
	 	row.put(KEY_INNING, inning.getInning());
	 	row.put(KEY_HOME_RUNS, inning.getHomeErrors());
	 	row.put(KEY_AWAY_RUNS, inning.getAwayRuns());
	 	row.put(KEY_HOME_HITS, inning.getAwayHits());
	 	row.put(KEY_AWAY_HITS, inning.getHomeHits());
	 	row.put(KEY_HOME_ERRORS, inning.getHomeErrors());
	 	row.put(KEY_AWAY_ERRORS, inning.getAwayErrors());
	 	return row;
	}

	public Cursor getInningsCursor() {
	 	String[] projection = new String[] { KEY_ID, KEY_GAME_ID, KEY_INNING, KEY_HOME_RUNS, KEY_AWAY_RUNS, KEY_HOME_HITS, KEY_AWAY_HITS, KEY_HOME_ERRORS, KEY_AWAY_ERRORS };
	 	return mDatabase.query(TABLE_NAME, projection, null, null, null, null, KEY_GAME_ID + " DESC");
	}
}
