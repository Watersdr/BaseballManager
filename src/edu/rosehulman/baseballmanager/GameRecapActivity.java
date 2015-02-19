package edu.rosehulman.baseballmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class GameRecapActivity extends Activity {

	private long mGameID;
	private RecapBoxScoreArrayAdapter mBoxScoreAdapter;
	private PlayerStatsDataAdapter statsAdapter;
	private PlayerDataAdapter mPlayerDataAdapter;
	private SimpleCursorAdapter cursorAdapterHomeLineup, cursorAdapterAwayLineup, cursorAdapterHomePitching, cursorAdapterAwayPitching;
	
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
		statsAdapter = new PlayerStatsDataAdapter(this);
		mPlayerDataAdapter = new PlayerDataAdapter(this);
		gameAdapter.open();
		teamAdapter.open();
		statsAdapter.open();
		mPlayerDataAdapter.open();
		
		List<String> teamNames = new ArrayList<String>();
		Game game = gameAdapter.getGame(mGameID);
		String homeTeamName = teamAdapter.getTeam(game.getHomeID()).getName();
		String awayTeamName = teamAdapter.getTeam(game.getAwayID()).getName();
		teamNames.add("Title");
		teamNames.add(awayTeamName);
		teamNames.add(homeTeamName);
		
		((TextView) findViewById(R.id.recap_team1)).setText(homeTeamName);
		((TextView) findViewById(R.id.recap_team2)).setText(awayTeamName);
		
		//fix with actual runs
		TextView homeTV = (TextView) findViewById(R.id.recap_home_final_score);
		TextView awayTV = (TextView) findViewById(R.id.recap_away_final_score);
		
		
		mBoxScoreAdapter = new RecapBoxScoreArrayAdapter(this, R.layout.box_score_item, teamNames, mGameID, homeTV, awayTV);
		ListView boxScoreListView = (ListView) findViewById(R.id.box_score_listview);
		boxScoreListView.setAdapter(mBoxScoreAdapter);
		Utility.setListViewHeightBasedonChildren(boxScoreListView);	
		
		ListView team1LineUpListView = (ListView) findViewById(R.id.recap_team1_lineup_stats);
		String[] fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_ABS, PlayerStatsDataAdapter.KEY_H, PlayerStatsDataAdapter.KEY_K, PlayerStatsDataAdapter.KEY_BB, PlayerStatsDataAdapter.KEY_E};
		int[] toTextViews = new int[] {R.id.recap_batter_name, R.id.recap_at_bats, R.id.recap_hits, R.id.recap_strike_outs_batter, R.id.recap_walks_batter, R.id.recap_errors};
		Cursor c = statsAdapter.getHomePlayerStatsForGame(mGameID);
		team1LineUpListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openStatsDialog(id, true);
			}
		});
		cursorAdapterHomeLineup = new SimpleCursorAdapter(this, R.layout.lineup_stats_item, c, fromColumns, toTextViews, 0);
		team1LineUpListView.setAdapter(cursorAdapterHomeLineup);
		Utility.setListViewHeightBasedonChildren(team1LineUpListView);
		
		ListView team2LineUpListView = (ListView) findViewById(R.id.recap_team2_lineup_stats);
		fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_ABS, PlayerStatsDataAdapter.KEY_H, PlayerStatsDataAdapter.KEY_K, PlayerStatsDataAdapter.KEY_BB, PlayerStatsDataAdapter.KEY_E};
		toTextViews = new int[] {R.id.recap_batter_name, R.id.recap_at_bats, R.id.recap_hits, R.id.recap_strike_outs_batter, R.id.recap_walks_batter, R.id.recap_errors};
		c = statsAdapter.getAwayPlayerStatsForGame(mGameID);
		team2LineUpListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openStatsDialog(id, false);
			}
		});
		cursorAdapterAwayLineup = new SimpleCursorAdapter(this, R.layout.lineup_stats_item, c, fromColumns, toTextViews, 0);
		team2LineUpListView.setAdapter(cursorAdapterAwayLineup);
		Utility.setListViewHeightBasedonChildren(team2LineUpListView);

		ListView team1PitchingListView = (ListView) findViewById(R.id.recap_team1_pitching_stats);
		fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_IP, PlayerStatsDataAdapter.KEY_P_R, PlayerStatsDataAdapter.KEY_P_ER, PlayerStatsDataAdapter.KEY_P_K, PlayerStatsDataAdapter.KEY_P_BB};
		toTextViews = new int[] {R.id.recap_pitcher_name, R.id.recap_innings_pitched, R.id.recap_runs, R.id.recap_earned_runs, R.id.recap_strike_outs_pitcher, R.id.recap_walks_pitcher};
		c = statsAdapter.getHomePitcherStatsForGame(mGameID);
		team1PitchingListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openPitchingStatsDialog(id, true);
			}
		});
		cursorAdapterHomePitching = new SimpleCursorAdapter(this, R.layout.pitching_stats_item, c, fromColumns, toTextViews, 0);
		team1PitchingListView.setAdapter(cursorAdapterHomePitching);
		Utility.setListViewHeightBasedonChildren(team1PitchingListView);

		ListView team2PitchingListView = (ListView) findViewById(R.id.recap_team2_pitching_stats);
		fromColumns = new String[]  {PlayerDataAdapter.KEY_L_NAME, PlayerStatsDataAdapter.KEY_IP, PlayerStatsDataAdapter.KEY_P_R, PlayerStatsDataAdapter.KEY_P_ER, PlayerStatsDataAdapter.KEY_P_K, PlayerStatsDataAdapter.KEY_P_BB};
		toTextViews = new int[] {R.id.recap_pitcher_name, R.id.recap_innings_pitched, R.id.recap_runs, R.id.recap_earned_runs, R.id.recap_strike_outs_pitcher, R.id.recap_walks_pitcher};
		c = statsAdapter.getAwayPitcherStatsForGame(mGameID);
		team2PitchingListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openPitchingStatsDialog(id, false);
			}
		});
		cursorAdapterAwayPitching = new SimpleCursorAdapter(this, R.layout.pitching_stats_item, c, fromColumns, toTextViews, 0);
		team2PitchingListView.setAdapter(cursorAdapterAwayPitching);
		Utility.setListViewHeightBasedonChildren(team2PitchingListView);
		
		
	}
	
	private void openStatsDialog(final long id, final boolean homeTeam) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle b) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
				final PlayerStats ps = statsAdapter.getPlayerStats(id);
				
				LayoutInflater inflater = getActivity().getLayoutInflater();
			    View dialogView = inflater.inflate(R.layout.dialog_batting_stats, null);
			    builder.setView(dialogView);
			    final NumberPicker abPicker = (NumberPicker) dialogView.findViewById(R.id.abPicker);
			    final NumberPicker hitPicker = (NumberPicker) dialogView.findViewById(R.id.hitPicker);
			    final NumberPicker walksPicker = (NumberPicker) dialogView.findViewById(R.id.walksPicker);
			    final NumberPicker kPicker = (NumberPicker) dialogView.findViewById(R.id.kPicker);
			    final NumberPicker errorsPicker = (NumberPicker) dialogView.findViewById(R.id.errorsPicker);
			    
			    abPicker.setMaxValue(100);
			    abPicker.setWrapSelectorWheel(false);
			    abPicker.setValue(ps.getAbs());
			    hitPicker.setMaxValue(100);
			    hitPicker.setWrapSelectorWheel(false);
			    hitPicker.setValue(ps.getH());
			    walksPicker.setMaxValue(100);
			    walksPicker.setWrapSelectorWheel(false);
			    walksPicker.setValue(ps.getBb());
			    kPicker.setMaxValue(100);
			    kPicker.setWrapSelectorWheel(false);
			    kPicker.setValue(ps.getK());
			    errorsPicker.setMaxValue(100);
			    errorsPicker.setWrapSelectorWheel(false);
			    errorsPicker.setValue(ps.getE());
			    
			    String name = mPlayerDataAdapter.getPlayer(ps.getPlayerID()).getLName();
			    
			    ((TextView) dialogView.findViewById(R.id.batting_player)).setText(name);

			    Button button = (Button) dialogView.findViewById(R.id.save_inning);
			    button.setOnClickListener(new OnClickListener() {
			       	@Override
			       	public void onClick(View v) {
			       		ps.setAbs(abPicker.getValue());
			       		ps.setH(hitPicker.getValue());
			       		ps.setBb(walksPicker.getValue());
			       		ps.setK(kPicker.getValue());
			       		ps.setE(errorsPicker.getValue());
			       		statsAdapter.updatePlayerStats(ps);
						Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
			            dismiss();
			       	}
			 	});
				return builder.create();
			}
			
			@Override
			public void onDismiss(DialogInterface dialog){
				if(homeTeam) {
					Cursor c = statsAdapter.getHomePlayerStatsForGame(mGameID);
					cursorAdapterHomeLineup.changeCursor(c);
				} else {
					Cursor c = statsAdapter.getAwayPlayerStatsForGame(mGameID);
					cursorAdapterAwayLineup.changeCursor(c);
				}
			}
		};
		df.show(getFragmentManager(), "Batting/Defense Stats");
	}

	private void openPitchingStatsDialog(final long id, final boolean homeTeam) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle b) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
				final PlayerStats ps = statsAdapter.getPlayerStats(id);
				
				LayoutInflater inflater = getActivity().getLayoutInflater();
			    View dialogView = inflater.inflate(R.layout.dialog_pitching_stats, null);
			    builder.setView(dialogView);
			    final NumberPicker ipPicker = (NumberPicker) dialogView.findViewById(R.id.ipPicker);
			    final NumberPicker runPicker = (NumberPicker) dialogView.findViewById(R.id.runPicker);
			    final NumberPicker erPicker = (NumberPicker) dialogView.findViewById(R.id.erPicker);
			    final NumberPicker kPicker = (NumberPicker) dialogView.findViewById(R.id.kPicker);
			    final NumberPicker walksPicker = (NumberPicker) dialogView.findViewById(R.id.walksPicker);
			    
			    ipPicker.setMaxValue(100);
			    ipPicker.setWrapSelectorWheel(false);
			    ipPicker.setValue(ps.getIp());
			    runPicker.setMaxValue(100);
			    runPicker.setWrapSelectorWheel(false);
			    runPicker.setValue(ps.getP_r());
			    erPicker.setMaxValue(100);
			    erPicker.setWrapSelectorWheel(false);
			    erPicker.setValue(ps.getP_er());
			    kPicker.setMaxValue(100);
			    kPicker.setWrapSelectorWheel(false);
			    kPicker.setValue(ps.getP_k());
			    walksPicker.setMaxValue(100);
			    walksPicker.setWrapSelectorWheel(false);
			    walksPicker.setValue(ps.getP_bb());
			    
			    String name = mPlayerDataAdapter.getPlayer(ps.getPlayerID()).getLName();
			    
			    ((TextView) dialogView.findViewById(R.id.pitching_player)).setText(name);

			    Button button = (Button) dialogView.findViewById(R.id.save_inning_pitching);
			    button.setOnClickListener(new OnClickListener() {
			       	@Override
			       	public void onClick(View v) {
			       		ps.setIp(ipPicker.getValue());
			       		ps.setP_r(runPicker.getValue());
			       		ps.setP_er(erPicker.getValue());
			       		ps.setP_k(kPicker.getValue());
			       		ps.setP_bb(walksPicker.getValue());
			       		statsAdapter.updatePlayerStats(ps);
						Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
			            dismiss();
			       	}
			 	});
				return builder.create();
			}
			
			@Override
			public void onDismiss(DialogInterface dialog){
				if(homeTeam) {
					Cursor c = statsAdapter.getHomePitcherStatsForGame(mGameID);
					cursorAdapterHomePitching.changeCursor(c);
				} else {
					Cursor c = statsAdapter.getAwayPitcherStatsForGame(mGameID);
					cursorAdapterAwayPitching.changeCursor(c);
				}
			}
		};
		df.show(getFragmentManager(), "Pitching Stats");
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
