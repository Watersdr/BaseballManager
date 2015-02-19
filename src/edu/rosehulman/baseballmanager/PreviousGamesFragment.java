package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PreviousGamesFragment extends ListFragment {
	private long teamID;
	private GameDataAdapter mGameDataAdapter;
	private TeamDataAdapter mTeamDataAdapter;
	private GameArrayAdapter mGameArrayAdapter;
	private static final int REQUEST_CODE_RECAP = 1;

	public PreviousGamesFragment(long teamID) {
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
		View v = inflater.inflate(R.layout.activity_previous_games, container,
				false);

		Cursor c = mGameDataAdapter.getPreviousGamesCursor(teamID);
		ArrayList<Game> games = new ArrayList<Game>();
		while (c.moveToNext()) {
			Game g = mGameDataAdapter.getGame(c.getLong(0));
			games.add(g);
		}

		mGameArrayAdapter = new GameArrayAdapter(getActivity(),
				R.layout.previous_game_item, games);
		setListAdapter(mGameArrayAdapter);

		return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(getActivity(), GameRecapActivity.class);
		Game game = (Game) getListAdapter().getItem(position);
		i.putExtra(GameDataAdapter.KEY_ID, game.getID());
		startActivityForResult(i, REQUEST_CODE_RECAP);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		updateGames();
	}

	public void updateGames() {
		if (getActivity() != null) {
			Cursor c = mGameDataAdapter.getPreviousGamesCursor(teamID);
			ArrayList<Game> games = new ArrayList<Game>();
			while (c.moveToNext()) {
				Game g = mGameDataAdapter.getGame(c.getLong(0));
				games.add(g);
			}

			mGameArrayAdapter = new GameArrayAdapter(getActivity(),
					R.layout.previous_game_item, games);
			setListAdapter(mGameArrayAdapter);
		}
	}
}
