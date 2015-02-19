package edu.rosehulman.baseballmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class GameRecapActivity extends Activity {

	private long mGameID;
	private RecapBoxScoreArrayAdapter mBoxScoreAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_recap);
		
		Intent i = getIntent();
		mGameID = i.getLongExtra(GameDataAdapter.KEY_ID, -1);
		if (mGameID == -1) {
			Log.d(SplashScreen.BM, "Game ID was -1");
			finish();
		}
		
		GameDataAdapter gameAdapter = new GameDataAdapter(this);
		TeamDataAdapter teamAdapter = new TeamDataAdapter(this);
		PlayerStatsDataAdapter statsAdapter = new PlayerStatsDataAdapter(this);
		gameAdapter.open();
		teamAdapter.open();
		statsAdapter.open();
		
		List<String> teamNames = new ArrayList<String>();
		Game game = gameAdapter.getGame(mGameID);
		String homeTeamName = teamAdapter.getTeam(game.getHomeID()).getName();
		String awayTeamName = teamAdapter.getTeam(game.getAwayID()).getName();
		teamNames.add("Title");
		teamNames.add(homeTeamName);
		teamNames.add(awayTeamName);
		
		((TextView) findViewById(R.id.recap_team1)).setText(homeTeamName);
		((TextView) findViewById(R.id.recap_team2)).setText(awayTeamName);
		
		//fix with actual runs
		((TextView) findViewById(R.id.recap_home_final_score)).setText(homeTeamName + ": 0");
		((TextView) findViewById(R.id.recap_away_final_score)).setText(awayTeamName + ": 0"); 
		
		
		mBoxScoreAdapter = new RecapBoxScoreArrayAdapter(this, R.layout.box_score_item, teamNames, mGameID);
		ListView boxScoreListView = (ListView) findViewById(R.id.box_score_listview);
		boxScoreListView.setAdapter(mBoxScoreAdapter);
		Utility.setListViewHeightBasedonChildren(boxScoreListView);	
		
		ListView team1LineUpListView = (ListView) findViewById(R.id.recap_team1_lineup_stats);
		String[] fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_ABS, PlayerStatsDataAdapter.KEY_H, PlayerStatsDataAdapter.KEY_K, PlayerStatsDataAdapter.KEY_BB, PlayerStatsDataAdapter.KEY_E};
		int[] toTextViews = new int[] {R.id.recap_batter_name, R.id.recap_at_bats, R.id.recap_hits, R.id.recap_strike_outs_batter, R.id.recap_errors};
		Cursor c = statsAdapter.getHomePlayerStatsForGame(mGameID);
		SimpleCursorAdapter cursorAdapterHomeLineup = new SimpleCursorAdapter(this, R.layout.lineup_stats_item, c, fromColumns, toTextViews, 0);
		team1LineUpListView.setAdapter(cursorAdapterHomeLineup);
		Utility.setListViewHeightBasedonChildren(team1LineUpListView);
		
		ListView team2LineUpListView = (ListView) findViewById(R.id.recap_team2_lineup_stats);
		fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_ABS, PlayerStatsDataAdapter.KEY_H, PlayerStatsDataAdapter.KEY_K, PlayerStatsDataAdapter.KEY_BB, PlayerStatsDataAdapter.KEY_E};
		toTextViews = new int[] {R.id.recap_batter_name, R.id.recap_at_bats, R.id.recap_hits, R.id.recap_strike_outs_batter,R.id.recap_walks_batter, R.id.recap_errors};
		c = statsAdapter.getAwayPlayerStatsForGame(mGameID);
		SimpleCursorAdapter cursorAdapterAwayLineup = new SimpleCursorAdapter(this, R.layout.lineup_stats_item, c, fromColumns, toTextViews, 0);
		team2LineUpListView.setAdapter(cursorAdapterAwayLineup);
		Utility.setListViewHeightBasedonChildren(team2LineUpListView);

		ListView team1PitchingListView = (ListView) findViewById(R.id.recap_team1_pitching_stats);
		fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_IP, PlayerStatsDataAdapter.KEY_P_R, PlayerStatsDataAdapter.KEY_P_ER, PlayerStatsDataAdapter.KEY_P_K, PlayerStatsDataAdapter.KEY_P_BB};
		toTextViews = new int[] {R.id.recap_pitcher_name, R.id.recap_innings_pitched, R.id.recap_runs, R.id.recap_earned_runs, R.id.recap_strike_outs_pitcher, R.id.recap_walks_pitcher};
		c = statsAdapter.getHomePitcherStatsForGame(mGameID);
		SimpleCursorAdapter cursorAdapterHomePitching = new SimpleCursorAdapter(this, R.layout.pitching_stats_item, c, fromColumns, toTextViews, 0);
		team1PitchingListView.setAdapter(cursorAdapterHomePitching);
		Utility.setListViewHeightBasedonChildren(team1PitchingListView);

		ListView team2PitchingListView = (ListView) findViewById(R.id.recap_team2_pitching_stats);
		fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_IP, PlayerStatsDataAdapter.KEY_P_R, PlayerStatsDataAdapter.KEY_P_ER, PlayerStatsDataAdapter.KEY_P_K, PlayerStatsDataAdapter.KEY_P_BB};
		toTextViews = new int[] {R.id.recap_pitcher_name, R.id.recap_innings_pitched, R.id.recap_runs, R.id.recap_earned_runs, R.id.recap_strike_outs_pitcher, R.id.recap_walks_pitcher};
		c = statsAdapter.getAwayPitcherStatsForGame(mGameID);
		SimpleCursorAdapter cursorAdapterAwayPitching = new SimpleCursorAdapter(this, R.layout.pitching_stats_item, c, fromColumns, toTextViews, 0);
		team1PitchingListView.setAdapter(cursorAdapterAwayPitching);
		Utility.setListViewHeightBasedonChildren(team2PitchingListView);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_recap, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
