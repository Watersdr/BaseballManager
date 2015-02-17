package edu.rosehulman.baseballmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDataAdapter {
	public static final String TABLE_NAME = "games";

	public static final String KEY_ID = "_id";
	public static final String KEY_GAME_DATE = "game_date";
	public static final String KEY_HOME_ID = "home_id";
	public static final String KEY_AWAY_ID = "away_id";

	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;

	public boolean removeGame(long id) {
	 	return mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null) > 0;
	}
	 
	public boolean removeGame(Game g) {
	 	return removeGame(g.getID());
	}
	
	public void updateScore(Game game) {
		ContentValues row = getContentValuesFromGame(game);
		String selection = KEY_ID + " = " + game.getID();
		mDatabase.update(TABLE_NAME, row, selection, null);
	}
	
	public Game getGame(long id) {
	 	String[] projection = new String[] { KEY_ID, KEY_GAME_DATE, KEY_HOME_ID, KEY_AWAY_ID };
	 	String selection = KEY_ID + " = " + id;
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_GAME_DATE + " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return getGameFromCursor(c);
	 	}
	 	return null;
	}
	 
	private Game getGameFromCursor(Cursor c) {
		Game g = new Game();
	 	g.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
	 	g.setHomeID(c.getInt(c.getColumnIndexOrThrow(KEY_HOME_ID)));
	 	g.setAwayID(c.getInt(c.getColumnIndexOrThrow(KEY_AWAY_ID)));
	 	g.setGameDate(c.getString(c.getColumnIndexOrThrow(KEY_GAME_DATE)));
	 	return g;
	}
	
	public GameDataAdapter(Context context) {
		mOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	public void open() {
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public long addGame(Game game) {
	 	ContentValues row = getContentValuesFromGame(game);
	 	long rowId = mDatabase.insert(TABLE_NAME, null, row);
	 	game.setID(rowId);
	 	return rowId;
	}
	
	private ContentValues getContentValuesFromGame(Game game) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_HOME_ID, game.getHomeID());
	 	row.put(KEY_AWAY_ID, game.getAwayID());
	 	row.put(KEY_GAME_DATE, game.getGameDate());
	 	return row;
	}

	public Cursor getGamesCursor() {
		String[] projection = new String[] { KEY_ID, KEY_GAME_DATE, KEY_HOME_ID, KEY_AWAY_ID };
	 	return mDatabase.query(TABLE_NAME, projection, null, null, null, null, KEY_GAME_DATE + " DESC");
	}
	
	public Cursor getGamesCursor(long teamID) {
		String[] projection = new String[] { KEY_ID, KEY_GAME_DATE, KEY_HOME_ID, KEY_AWAY_ID };
		String selection = KEY_AWAY_ID + " = " + teamID + " OR " + KEY_HOME_ID + " = " + teamID;
	 	return mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_GAME_DATE + " DESC");
	}
	
	public Cursor getUpcomingGamesCursor(long teamID) {
		String[] projection = new String[] { KEY_ID, KEY_GAME_DATE, KEY_HOME_ID, KEY_AWAY_ID };
		String selection = KEY_GAME_DATE + " > date(\'now\') AND (" + KEY_AWAY_ID + " = " + teamID + " OR " + KEY_HOME_ID + " = " + teamID + ")";
		return mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_GAME_DATE + " DESC");
	}
	public Cursor getPreviousGamesCursor(long teamID) {
		String[] projection = new String[] { KEY_ID, KEY_GAME_DATE, KEY_HOME_ID, KEY_AWAY_ID };
		String selection = KEY_GAME_DATE + " < date(\'now\') AND (" + KEY_AWAY_ID + " = " + teamID + " OR " + KEY_HOME_ID + " = " + teamID + ")";
		return mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_GAME_DATE + " DESC");
	}
}
