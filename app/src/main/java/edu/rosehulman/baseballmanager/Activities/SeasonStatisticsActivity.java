package edu.rosehulman.baseballmanager.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import edu.rosehulman.baseballmanager.BattingDefenseStatsFragment;
import edu.rosehulman.baseballmanager.PitchingStatsFragment;
import edu.rosehulman.baseballmanager.PlayerDataAdapter;
import edu.rosehulman.baseballmanager.PlayerStatsDataAdapter;
import edu.rosehulman.baseballmanager.R;
import edu.rosehulman.baseballmanager.TabListener;
import edu.rosehulman.baseballmanager.TeamDataAdapter;

public class SeasonStatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_season_statistics);

		PlayerDataAdapter mPlayerDataAdapter = new PlayerDataAdapter(this);
		mPlayerDataAdapter.open();
		PlayerStatsDataAdapter mPlayerStatsDataAdapter = new PlayerStatsDataAdapter(this);
		mPlayerStatsDataAdapter.open();

		long teamID = getIntent().getLongExtra(TeamDataAdapter.KEY_ID, -1);
		Cursor battingCursor = mPlayerStatsDataAdapter.getBattingDefesnseSeasonStats(teamID);		
		Cursor pitchingCursor = mPlayerStatsDataAdapter.getPitchingSeasonStats(teamID);	
		PitchingStatsFragment pitchingStats = new PitchingStatsFragment(pitchingCursor);
		BattingDefenseStatsFragment battingStats = new BattingDefenseStatsFragment(battingCursor);

		ActionBar actionBar = getActionBar();

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set Tab Icon and Titles
		ActionBar.Tab Tab1 = actionBar.newTab().setText("Batting / Defense");
		ActionBar.Tab Tab2 = actionBar.newTab().setText("Pitching");

		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(battingStats));
		Tab2.setTabListener(new TabListener(pitchingStats));

		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
	}
}
