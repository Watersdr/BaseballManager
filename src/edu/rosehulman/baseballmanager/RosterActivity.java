package edu.rosehulman.baseballmanager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class RosterActivity extends Activity {

	private ActionBar.Tab Tab1, Tab2;
	private Fragment lineupFragment, fragmentTab2;
	private long teamID;
	private static final int REQUEST_PLAYER_ADDEDIT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster);

		teamID = getIntent().getLongExtra(TeamDataAdapter.KEY_ID, -1);
		lineupFragment = new LineupFragment(teamID);
		fragmentTab2 = new DepthChartFragment();
 
		ActionBar actionBar = getActionBar();
 
		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
		// Set Tab Icon and Titles
		Tab1 = actionBar.newTab().setText("Line-Up");
		Tab2 = actionBar.newTab().setText("Depth-Chart");
 
		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(lineupFragment));
		Tab2.setTabListener(new TabListener(fragmentTab2));
 
		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.roster, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_player) {
			Intent i = new Intent(this, AddEditPlayerActivity.class);
			i.putExtra(PlayerDataAdapter.KEY_TEAM_ID, teamID);
			// -1 To represent new player
			i.putExtra(PlayerDataAdapter.KEY_ID, -1);
			startActivityForResult(i, REQUEST_PLAYER_ADDEDIT);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // See which child activity is calling us back. 
        switch (requestCode) {
            case REQUEST_PLAYER_ADDEDIT:
                if (resultCode == Activity.RESULT_OK){
                	((LineupFragment) lineupFragment).updateLineup();
                    Log.d(SplashScreen.BM, "Result ok!");
                } 
                else {
                    Log.d(SplashScreen.BM, "Result not okay.  User hit back without a button");
                }
                break;
            default:
                Log.d(SplashScreen.BM, "Unknown result code");
                break;
        }
    }
}
