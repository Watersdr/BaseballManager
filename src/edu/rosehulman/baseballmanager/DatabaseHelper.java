package edu.rosehulman.baseballmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "baseballmanger.db";
	public static final String KEY_ID = "_id";
	public static final String BM = "BM";
	
	private static DatabaseHelper instance;
	private static String DROP_TEAM = "DROP TABLE IF EXISTS " + TeamDataAdapter.TABLE_NAME;
	private static String CREATE_TEAM;
	static {
	 	StringBuilder sb = new StringBuilder();
	 	sb.append("CREATE TABLE " + TeamDataAdapter.TABLE_NAME + " (");
	 	sb.append(TeamDataAdapter.KEY_ID + " integer primary key autoincrement, ");
	 	sb.append(TeamDataAdapter.KEY_NAME + " text, ");
	 	sb.append(TeamDataAdapter.KEY_LOGO + " blob");
	 	sb.append(")");
	 	CREATE_TEAM = sb.toString();
	}
	
	private static String DROP_PLAYER = "DROP TABLE IF EXISTS " + PlayerDataAdapter.TABLE_NAME;
	private static String CREATE_PLAYER;
	static {
	 	StringBuilder sb = new StringBuilder();
	 	sb.append("CREATE TABLE " + PlayerDataAdapter.TABLE_NAME + " (");
	 	sb.append(PlayerDataAdapter.KEY_ID + " integer primary key autoincrement, ");
	 	sb.append(PlayerDataAdapter.KEY_TEAM_ID + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_NAME + " text, ");
	 	sb.append(PlayerDataAdapter.KEY_NUMBER + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_P + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_C + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_1B + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_2B + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_3B + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_SS + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_LF + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_CF + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_DC_RF + " integer, ");
	 	sb.append(PlayerDataAdapter.KEY_BATTING_ORDER + " integer");
	 	sb.append(")");
	 	CREATE_PLAYER = sb.toString();
	}
	
	private static String DROP_PLAYER_STATS = "DROP TABLE IF EXISTS " + PlayerStatsDataAdapter.TABLE_NAME;
	private static String CREATE_PLAYER_STATS;
	static {
	 	StringBuilder sb = new StringBuilder();
	 	sb.append("CREATE TABLE " + PlayerStatsDataAdapter.TABLE_NAME + " (");
	 	sb.append(PlayerStatsDataAdapter.KEY_ID + " integer primary key autoincrement, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_PLAYER_ID + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_GAME_ID + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_BATTING_ORDER + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_ABS + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_H + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_K + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_BB + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_E + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_IP + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_P_K + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_P_BB + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_P_R + " integer, ");
	 	sb.append(PlayerStatsDataAdapter.KEY_P_ER + " integer");
	 	sb.append(")");
	 	CREATE_PLAYER_STATS = sb.toString();
	}
	
	private static String DROP_GAME = "DROP TABLE IF EXISTS " + GameDataAdapter.TABLE_NAME;
	private static String CREATE_GAME;
	static {
	 	StringBuilder sb = new StringBuilder();
	 	sb.append("CREATE TABLE " + GameDataAdapter.TABLE_NAME + " (");
	 	sb.append(GameDataAdapter.KEY_ID + " integer primary key autoincrement, ");
	 	sb.append(GameDataAdapter.KEY_GAME_DATE + " text, ");
	 	sb.append(GameDataAdapter.KEY_HOME_ID + " integer, ");
	 	sb.append(GameDataAdapter.KEY_AWAY_ID + " integer");
	 	sb.append(")");
	 	CREATE_GAME = sb.toString();
	}
	
	private static String DROP_INNING = "DROP TABLE IF EXISTS " + InningDataAdapter.TABLE_NAME;
	private static String CREATE_INNING;
	static {
	 	StringBuilder sb = new StringBuilder();
	 	sb.append("CREATE TABLE " + InningDataAdapter.TABLE_NAME + " (");
	 	sb.append(InningDataAdapter.KEY_ID + " integer primary key autoincrement, ");
	 	sb.append(InningDataAdapter.KEY_GAME_ID + " integer, ");
	 	sb.append(InningDataAdapter.KEY_INNING + " integer, ");
	 	sb.append(InningDataAdapter.KEY_HOME_RUNS + " integer, ");
	 	sb.append(InningDataAdapter.KEY_AWAY_RUNS + " integer, ");
	 	sb.append(InningDataAdapter.KEY_HOME_HITS + " integer, ");
	 	sb.append(InningDataAdapter.KEY_AWAY_HITS + " integer, ");
	 	sb.append(InningDataAdapter.KEY_HOME_ERRORS + " integer, ");
	 	sb.append(InningDataAdapter.KEY_AWAY_ERRORS + " integer");
	 	sb.append(")");
	 	CREATE_GAME = sb.toString();
	}

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TEAM);
		db.execSQL(CREATE_PLAYER);
		db.execSQL(CREATE_PLAYER_STATS);
		db.execSQL(CREATE_GAME);
		db.execSQL(CREATE_INNING);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(BM, "Upgrading from version " + oldVersion + " to " + newVersion + " will delete all tables.");
		db.execSQL(DROP_TEAM);
		db.execSQL(DROP_PLAYER);
		db.execSQL(DROP_PLAYER_STATS);
		db.execSQL(DROP_GAME);
		db.execSQL(DROP_INNING);
		db.execSQL(CREATE_TEAM);
		db.execSQL(CREATE_PLAYER);
		db.execSQL(CREATE_PLAYER_STATS);
		db.execSQL(CREATE_GAME);
		db.execSQL(CREATE_INNING);
	}
	
	public static DatabaseHelper getInstance(Context context) {
		if (DatabaseHelper.instance == null) {
			DatabaseHelper.instance = new DatabaseHelper(context);
			
		}
		
		return DatabaseHelper.instance;
	}
}
