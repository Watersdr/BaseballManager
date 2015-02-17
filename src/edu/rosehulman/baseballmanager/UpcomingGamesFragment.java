package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UpcomingGamesFragment extends ListFragment {

	private long teamID;
	private GameDataAdapter mGameDataAdapter;
	private TeamDataAdapter mTeamDataAdapter;
	private GameArrayAdapter mGameArrayAdapter;
	
	public UpcomingGamesFragment(long teamID) {
		super();
		this.teamID = teamID;
		mGameDataAdapter = new GameDataAdapter(this.getActivity());
		mTeamDataAdapter = new TeamDataAdapter(this.getActivity());
		mGameDataAdapter.open();
		mTeamDataAdapter.open();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_upcoming_games, container, false);
		
//		Cursor c = mGameDataAdapter.getUpcomingGamesCursor(teamID);
		Cursor c = mGameDataAdapter.getUpcomingGamesCursor(teamID);
		Log.d(SplashScreen.BM, "" + c.getCount());
		Log.d(SplashScreen.BM, "" + teamID);
		ArrayList<Game> games = new ArrayList<Game>();
		while (c.moveToNext()) {
			Game g = mGameDataAdapter.getGame(c.getLong(0));
			Log.d(SplashScreen.BM, "adding game to adapter");
			games.add(g);
		}
		
		mGameArrayAdapter = new GameArrayAdapter(getActivity(), R.layout.upcoming_game_item, games);
		setListAdapter(mGameArrayAdapter);
		return v;
	}
	
	public void updateGames() {
		Cursor c = mGameDataAdapter.getUpcomingGamesCursor(teamID);
		ArrayList<Game> games = new ArrayList<Game>();
		while (c.moveToNext()) {
			Game g = mGameDataAdapter.getGame(c.getLong(0));
			games.add(g);
		}
		mGameArrayAdapter = new GameArrayAdapter(getActivity(), R.layout.upcoming_game_item, games);
		mGameArrayAdapter.notifyDataSetChanged();
		setListAdapter(mGameArrayAdapter);
	}	
}
