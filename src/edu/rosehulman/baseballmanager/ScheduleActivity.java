package edu.rosehulman.baseballmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ScheduleActivity extends Activity {

	/*
	 * Code for tabs based on Android Tutorials for Beginners
	 * http://www.learn-android-easily.com/2013/07/android-tabwidget-example.html
	 */	
	private ActionBar.Tab Tab1, Tab2;
	private Fragment upcomingFragment, previousFragment;
	private long teamID;
	private static final int REQUEST_ENTER_GAME = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		teamID = getIntent().getLongExtra(TeamDataAdapter.KEY_ID, -1);
		upcomingFragment = new UpcomingGamesFragment(teamID);
		previousFragment = new PreviousGamesFragment(teamID);
 
		ActionBar actionBar = getActionBar();
 
		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
		// Set Tab Icon and Titles
		Tab1 = actionBar.newTab().setText("Upcoming Games");
		Tab2 = actionBar.newTab().setText("Previous Games");
 
		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(upcomingFragment));
		Tab2.setTabListener(new TabListener(previousFragment));
 
		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_game) {
			Intent i = new Intent(this, AddGameActivity.class);
			startActivityForResult(i, REQUEST_ENTER_GAME);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // See which child activity is calling us back. 
        switch (requestCode) {
            case REQUEST_ENTER_GAME:
                if (resultCode == Activity.RESULT_OK){
                	((UpcomingGamesFragment) upcomingFragment).updateGames();
                	((PreviousGamesFragment) previousFragment).updateGames();
                    Log.d(SplashScreen.BM, "Result ok! Adding game");
                } 
                else {
                    Log.d(SplashScreen.BM, "Result not okay.  Game not added.");
                }
                break;
            default:
                Log.d(SplashScreen.BM, "Unknown result code");
                break;
        }
    }
}
