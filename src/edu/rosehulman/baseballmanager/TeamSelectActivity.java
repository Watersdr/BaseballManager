package edu.rosehulman.baseballmanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TeamSelectActivity extends Activity {

	private TeamDataAdapter mTeamDataAdapter;
	private SimpleCursorAdapter mCursorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_select);
		
		mTeamDataAdapter = new TeamDataAdapter(this);
		mTeamDataAdapter.open();
		

		ListView listView = (ListView)findViewById(R.id.team_list_view);
		
		Cursor cursor = mTeamDataAdapter.getTeamsCursor();
		String[] fromColumnsString = new String[] {TeamDataAdapter.KEY_NAME};
		int[] toTextView = new int[] {R.id.team_name_item};
		mCursorAdapter = new SimpleCursorAdapter(this, R.layout.team_item, cursor, fromColumnsString, toTextView, 0);
		listView.setAdapter(mCursorAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Start teams activity somehow
			}
		});
		
		Button createButton = (Button) findViewById(R.id.create_team_button);
		createButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(TeamSelectActivity.this, AddEditTeamActivity.class);
				startActivity(i);
			}
		});
		
		registerForContextMenu(listView);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.team_select, menu);
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
