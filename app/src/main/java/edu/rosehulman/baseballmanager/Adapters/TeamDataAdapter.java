package edu.rosehulman.baseballmanager.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.rosehulman.baseballmanager.DatabaseHelper;
import edu.rosehulman.baseballmanager.Team;

public class TeamDataAdapter {
	public static final String TABLE_NAME = "teams";

	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_LOGO = "logo";

	private SQLiteOpenHelper mOpenHelper;
	private SQLiteDatabase mDatabase;

	public boolean removeTeam(long id) {
	 	return mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null) > 0;
	}
	 
	public boolean removeTeam(Team t) {
	 	return removeTeam(t.getID());
	}
	
	public void updateTeam(Team team) {
		ContentValues row = getContentValuesFromTeam(team);
		String selection = KEY_ID + " = " + team.getID();
		mDatabase.update(TABLE_NAME, row, selection, null);
	}
	
	public Team getTeam(long id) {
	 	String[] projection = new String[] { KEY_ID, KEY_NAME, KEY_LOGO };
	 	String selection = KEY_ID + " = " + id;
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_NAME
				+ " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return getTeamFromCursor(c);
	 	}
	 	return null;
	}
	public long getTeamID(String name) {
	 	String[] projection = new String[] { KEY_ID };
	 	String selection = KEY_NAME + " = \'" + name + "\'";
	 	Cursor c = mDatabase.query(TABLE_NAME, projection, selection, null, null, null, KEY_NAME
				+ " DESC");
	 	if (c != null && c.moveToFirst()) {
	       	return c.getInt(c.getColumnIndexOrThrow(KEY_ID));
	 	}
	 	return -1;
	}
	 
	private Team getTeamFromCursor(Cursor c) {
		Team t = new Team();
	 	t.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
	 	t.setName(c.getString(c.getColumnIndexOrThrow(KEY_NAME)));
	 	t.setLogo(c.getBlob(c.getColumnIndexOrThrow(KEY_LOGO)));
	 	return t;
	}
	
	public TeamDataAdapter(Context context) {
		mOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	public void open() {
		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public long addTeam(Team team) {
	 	ContentValues row = getContentValuesFromTeam(team);
	 	long rowId = mDatabase.insert(TABLE_NAME, null, row);
	 	team.setID(rowId);
	 	return rowId;
	}
	
	private ContentValues getContentValuesFromTeam(Team team) {
	 	ContentValues row = new ContentValues();
	 	row.put(KEY_NAME, team.getName());
	 	row.put(KEY_LOGO, team.getLogo());
	 	return row;
	}

	public Cursor getTeamsCursor() {
	 	String[] projection = new String[] { KEY_ID, KEY_NAME, KEY_LOGO };
	 	return mDatabase.query(TABLE_NAME, projection, null, null, null, null, KEY_NAME + " DESC");
	}

}
