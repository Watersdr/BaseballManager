package edu.rosehulman.baseballmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class SeasonStatisticsActivity extends Activity {

	private ActionBar.Tab Tab1, Tab2;
	
	/*
	 * Code for tabs based on Android Tutorials for Beginners
	 * http://www.learn-android
	 * -easily.com/2013/07/android-tabwidget-example.html
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_season_statistics);

		PitchingStatsFragment pitchingStats = new PitchingStatsFragment();
		BattingDefenseStatsFragment battingStats = new BattingDefenseStatsFragment();

		ActionBar actionBar = getActionBar();

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set Tab Icon and Titles
		Tab1 = actionBar.newTab().setText("Batting / Defense Statistics");
		Tab2 = actionBar.newTab().setText("Pitching Statistics");

		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(pitchingStats));
		Tab2.setTabListener(new TabListener(battingStats));

		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
	}
}
