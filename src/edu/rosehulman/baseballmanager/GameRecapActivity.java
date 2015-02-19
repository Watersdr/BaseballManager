package edu.rosehulman.baseballmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class GameRecapActivity extends Activity {

	private long mGameID;
	private RecapBoxScoreArrayAdapter mBoxScoreAdapter;
//	private RecapLineupArrayAdapter mLineupAdapter;
//	private RecapPitchingArrayAdapter mPitchingAdapter;
	
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
		gameAdapter.open();
		teamAdapter.open();
		
		List<String> teamNames = new ArrayList<String>();
		
		Game game = gameAdapter.getGame(mGameID);
		teamNames.add("Title");
		teamNames.add(teamAdapter.getTeam(game.getHomeID()).getName());
		teamNames.add(teamAdapter.getTeam(game.getAwayID()).getName());
		
		
		mBoxScoreAdapter = new RecapBoxScoreArrayAdapter(this, R.layout.box_score_item, teamNames, mGameID);
		ListView boxScoreListView = (ListView) findViewById(R.id.box_score_listview);
		boxScoreListView.setAdapter(mBoxScoreAdapter);
		Utility.setListViewHeightBasedonChildren(boxScoreListView);	
		
//		mLineupAdapter = new RecapBoxScoreArrayAdapter(mGameID);
//		mBoxScoreAdapter = new RecapBoxScoreArrayAdapter(mGameID);
		
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
