package edu.rosehulman.baseballmanager.Adapters;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.rosehulman.baseballmanager.DatabaseHelper;
import edu.rosehulman.baseballmanager.Player;

public class PlayerDataAdapter {
    public static final String TABLE_NAME = "players";

    public static final String KEY_ID = "_id";
    public static final String KEY_TEAM_ID = "team_id";
    public static final String KEY_F_NAME = "f_name";
    public static final String KEY_L_NAME = "l_name";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_DEPTH_CHART_C = "dc_c";
    public static final String KEY_DEPTH_CHART_P = "dc_p";
    public static final String KEY_DEPTH_CHART_1B = "dc_1b";
    public static final String KEY_DEPTH_CHART_2B = "dc_2b";
    public static final String KEY_DEPTH_CHART_3B = "dc_3b";
    public static final String KEY_DEPTH_CHART_SS = "dc_ss";
    public static final String KEY_DEPTH_CHART_LF = "dc_lf";
    public static final String KEY_DEPTH_CHART_CF = "dc_cf";
    public static final String KEY_DEPTH_CHART_RF = "dc_rf";
    public static final String KEY_DEPTH_CHART_DH = "dc_dh";
    public static final String KEY_BATTING_ORDER = "batting_order";

    private SQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDatabase;
    private Player mPlayer;

    private static final String[] PROJECTION = new String[]{KEY_ID, KEY_TEAM_ID, KEY_F_NAME,
            KEY_L_NAME, KEY_NUMBER, KEY_DEPTH_CHART_C, KEY_DEPTH_CHART_P, KEY_DEPTH_CHART_1B,
            KEY_DEPTH_CHART_2B, KEY_DEPTH_CHART_3B, KEY_DEPTH_CHART_SS, KEY_DEPTH_CHART_LF,
            KEY_DEPTH_CHART_CF, KEY_DEPTH_CHART_RF, KEY_DEPTH_CHART_DH, KEY_BATTING_ORDER};


    public boolean removePlayerById(long id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + " = " + id, null) > 0;
    }

    public boolean removePlayerById(Player p) {
        return removePlayerById(p.getID());
    }

    public void updatePlayer(Player player) {
        ContentValues row = getContentValuesFromPlayer(player);
        String selection = KEY_ID + " = " + player.getID();
        mDatabase.update(TABLE_NAME, row, selection, null);
    }

    public Player getPlayerById(long id) {
        String selectPlayer = KEY_ID + " = " + id;
        Cursor playerCursor = mDatabase.query(TABLE_NAME, PROJECTION, selectPlayer, null, null, null,
                KEY_BATTING_ORDER + " ASC");
        if (playerCursor != null && playerCursor.moveToFirst()) {
            return getPlayerFromCursor(playerCursor);
        }
        return null;
    }

    public ArrayList<Player> getTeamLineupById(long teamID) {
        String selectLineup = KEY_TEAM_ID + " = " + teamID + " AND (" + KEY_BATTING_ORDER
                + " < 9 OR " + KEY_DEPTH_CHART_P + " > -1)";
        Cursor lineupCursor = mDatabase.query(TABLE_NAME, PROJECTION, selectLineup, null, null,
                null, KEY_BATTING_ORDER + " ASC");
        ArrayList<Player> players = new ArrayList<Player>();
        while (lineupCursor.moveToNext()) {
            players.add(getPlayerFromCursor(lineupCursor));
        }
        return players;
    }

    public ArrayList<Player> getTeamPlayersById(long teamID) {
        String selectTeam = KEY_TEAM_ID + " = " + teamID;
        Cursor teamPlayersCursor = mDatabase.query(TABLE_NAME, PROJECTION, selectTeam, null, null,
                null, KEY_BATTING_ORDER + " ASC");
        ArrayList<Player> players = new ArrayList<Player>();
        while (teamPlayersCursor.moveToNext()) {
            players.add(getPlayerFromCursor(teamPlayersCursor));
        }
        return players;
    }

    private Player getPlayerFromCursor(Cursor c) {
        Player p = new Player();
        p.setID(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
        p.setTeamID(c.getInt(c.getColumnIndexOrThrow(KEY_TEAM_ID)));
        p.setFirstName(c.getString(c.getColumnIndexOrThrow(KEY_F_NAME)));
        p.setLastName(c.getString(c.getColumnIndexOrThrow(KEY_L_NAME)));
        p.setNumber(c.getInt(c.getColumnIndexOrThrow(KEY_NUMBER)));
        p.setDepthChartC(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_C)));
        p.setDepthChartP(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_P)));
        p.setDepthChart1B(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_1B)));
        p.setDepthChart2B(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_2B)));
        p.setDepthChart3B(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_3B)));
        p.setDepthChartSS(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_SS)));
        p.setDepthChartLF(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_LF)));
        p.setDepthChartCF(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_CF)));
        p.setDepthChartRF(c.getInt(c.getColumnIndexOrThrow(KEY_DEPTH_CHART_RF)));
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
        row.put(KEY_F_NAME, player.getFirstName());
        row.put(KEY_L_NAME, player.getLastName());
        row.put(KEY_NUMBER, player.getNumber());
        row.put(KEY_DEPTH_CHART_C, player.getDepthChartC());
        row.put(KEY_DEPTH_CHART_P, player.getDepthChartP());
        row.put(KEY_DEPTH_CHART_1B, player.getDepthChart1B());
        row.put(KEY_DEPTH_CHART_2B, player.getDepthChart2B());
        row.put(KEY_DEPTH_CHART_3B, player.getDepthChart3B());
        row.put(KEY_DEPTH_CHART_SS, player.getDepthChartSS());
        row.put(KEY_DEPTH_CHART_LF, player.getDepthChartLF());
        row.put(KEY_DEPTH_CHART_CF, player.getDepthChartCF());
        row.put(KEY_DEPTH_CHART_RF, player.getDepthChartRF());
        row.put(KEY_BATTING_ORDER, player.getBattingOrder());
        return row;
    }

    private void setPositions() {
        Cursor cursor = mDatabase.rawQuery(
                "SELECT MAX(" + KEY_DEPTH_CHART_P + "), MAX(" + KEY_DEPTH_CHART_C + "), MAX("
                        + KEY_DEPTH_CHART_1B + "), + MAX(" + KEY_DEPTH_CHART_2B + "), MAX("
                        + KEY_DEPTH_CHART_3B + "), MAX(" + KEY_DEPTH_CHART_SS + "), MAX("
                        + KEY_DEPTH_CHART_LF + "), MAX(" + KEY_DEPTH_CHART_CF + "), MAX("
                        + KEY_DEPTH_CHART_RF + "), MAX(" + KEY_BATTING_ORDER + ") FROM "
                        + TABLE_NAME + " WHERE " + KEY_TEAM_ID + "=?",
                new String[]{mPlayer.getTeamID() + ""}
        );
        if (cursor.moveToNext()) {
            setPositionPitcher(cursor);
            setPositionCatcher(cursor);
            setPosition1B(cursor);
            setPosition2B(cursor);
            setPosition3B(cursor);
            setPositionSS(cursor);
            setPositionLF(cursor);
            setPositionCF(cursor);
            setPositionRF(cursor);
            setPlayerBattingOrder(cursor);
        } else {
            mPlayer.setDepthChartP(mPlayer.getDepthChartP() == 0 ? 0 : mPlayer.getDepthChartP());
            mPlayer.setDepthChartC(mPlayer.getDepthChartC() == 0 ? 0 : mPlayer.getDepthChartC());
            mPlayer.setDepthChart1B(mPlayer.getDepthChart1B() == 0 ? 0 : mPlayer.getDepthChart1B());
            mPlayer.setDepthChart2B(mPlayer.getDepthChart2B() == 0 ? 0 : mPlayer.getDepthChart2B());
            mPlayer.setDepthChart3B(mPlayer.getDepthChart3B() == 0 ? 0 : mPlayer.getDepthChart3B());
            mPlayer.setDepthChartSS(mPlayer.getDepthChartSS() == 0 ? 0 : mPlayer.getDepthChartSS());
            mPlayer.setDepthChartLF(mPlayer.getDepthChartLF() == 0 ? 0 : mPlayer.getDepthChartLF());
            mPlayer.setDepthChartCF(mPlayer.getDepthChartCF() == 0 ? 0 : mPlayer.getDepthChartCF());
            mPlayer.setDepthChartRF(mPlayer.getDepthChartRF() == 0 ? 0 : mPlayer.getDepthChartRF());
            mPlayer.setBattingOrder(mPlayer.getBattingOrder() == -1 ? 0 : mPlayer.getBattingOrder());
        }

    }

    private void setPlayerBattingOrder(Cursor cursor) {
        if (mPlayer.getBattingOrder() == -1) {
            if (cursor.isNull(9)) {
                mPlayer.setBattingOrder(0);
            } else {
                mPlayer.setBattingOrder((cursor.getInt(9) + 1));
            }
        } else {
            mPlayer.setBattingOrder(mPlayer.getBattingOrder());
        }
    }

    private void setPositionRF(Cursor cursor) {
        if (mPlayer.getDepthChartRF() == 0) {
            if (cursor.isNull(8)) {
                mPlayer.setDepthChartRF(0);
            } else {
                mPlayer.setDepthChartRF((cursor.getInt(8) + 1));
            }
        } else {
            mPlayer.setDepthChartRF(mPlayer.getDepthChartRF());
        }
    }

    private void setPositionCF(Cursor cursor) {
        if (mPlayer.getDepthChartCF() == 0) {
            if (cursor.isNull(7)) {
                mPlayer.setDepthChartCF(0);
            } else {
                mPlayer.setDepthChartCF((cursor.getInt(7) + 1));
            }
        } else {
            mPlayer.setDepthChartCF(mPlayer.getDepthChartCF());
        }
    }

    private void setPositionLF(Cursor cursor) {
        if (mPlayer.getDepthChartLF() == 0) {
            if (cursor.isNull(6)) {
                mPlayer.setDepthChartLF(0);
            } else {
                mPlayer.setDepthChartLF((cursor.getInt(6) + 1));
            }
        } else {
            mPlayer.setDepthChartLF(mPlayer.getDepthChartLF());
        }
    }

    private void setPositionSS(Cursor cursor) {
        if (mPlayer.getDepthChartSS() == 0) {
            if (cursor.isNull(5)) {
                mPlayer.setDepthChartSS(0);
            } else {
                mPlayer.setDepthChartSS((cursor.getInt(5) + 1));
            }
        } else {
            mPlayer.setDepthChartSS(mPlayer.getDepthChartSS());
        }
    }

    private void setPosition3B(Cursor cursor) {
        if (mPlayer.getDepthChart3B() == 0) {
            if (cursor.isNull(4)) {
                mPlayer.setDepthChart3B(0);
            } else {
                mPlayer.setDepthChart3B((cursor.getInt(4) + 1));
            }
        } else {
            mPlayer.setDepthChart3B(mPlayer.getDepthChart3B());
        }
    }

    private void setPosition2B(Cursor cursor) {
        if (mPlayer.getDepthChart2B() == 0) {
            if (cursor.isNull(3)) {
                mPlayer.setDepthChart2B(0);
            } else {
                mPlayer.setDepthChart2B((cursor.getInt(3) + 1));
            }
        } else {
            mPlayer.setDepthChart2B(mPlayer.getDepthChart2B());
        }
    }

    private void setPosition1B(Cursor cursor) {
        if (mPlayer.getDepthChart1B() == 0) {
            if (cursor.isNull(2)) {
                mPlayer.setDepthChart1B(0);
            } else {
                mPlayer.setDepthChart1B((cursor.getInt(2) + 1));
            }
        } else {
            mPlayer.setDepthChart1B(mPlayer.getDepthChart1B());
        }
    }

    private void setPositionCatcher(Cursor cursor) {
        if (mPlayer.getDepthChartC() == 0) {
            if (cursor.isNull(1)) {
                mPlayer.setDepthChartC(0);
            } else {
                mPlayer.setDepthChartC((cursor.getInt(1) + 1));
            }
        } else {
            mPlayer.setDepthChartC(mPlayer.getDepthChartC());
        }
    }

    private void setPositionPitcher(Cursor c) {
        if (mPlayer.getDepthChartP() == 0)
            if (c.isNull(0)) {
                mPlayer.setDepthChartP(0);
            } else {
                mPlayer.setDepthChartP((c.getInt(0) + 1));
            }
        else {
            mPlayer.setDepthChartP(mPlayer.getDepthChartP());
        }
    }

    public Cursor getPlayersCursor() {
        return mDatabase.query(TABLE_NAME, PROJECTION, null, null, null, null, KEY_L_NAME + " DESC");
    }

}