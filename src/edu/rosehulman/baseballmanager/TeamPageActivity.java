package edu.rosehulman.baseballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TeamPageActivity extends Activity {
	private TeamDataAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_page);
		adapter = new TeamDataAdapter(this);
		adapter.open();
		
		Team t = adapter.getTeam(getIntent().getLongExtra(TeamDataAdapter.KEY_ID, -1));
		((TextView) findViewById(R.id.team_name_item)).setText(getString(R.string.manage_team, t.getName()));
		
		Button rosterButton = (Button) findViewById(R.id.roster_button);
		rosterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeRosterIntent();
			}
		});
		
		Button scheduleButton = (Button) findViewById(R.id.schedule_button);
		scheduleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeScheduleIntent();
			}
		});
		Button statsButton = (Button) findViewById(R.id.season_statistics_button);
		statsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeStatsIntent();
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.close();
	}

	protected void makeStatsIntent() {
		Intent i = new Intent(this, SeasonStatisticsActivity.class);
		startActivity(i);		
	}

	protected void makeScheduleIntent() {
		Intent i = new Intent(this, ScheduleActivity.class);
		startActivity(i);
	}

	protected void makeRosterIntent() {
		Intent i = new Intent(this, RosterActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team_page, menu);
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
