package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.rosehulman.baseballmanager.Activities.GameRecapActivity;

public class PreviousGamesFragment extends ListFragment {
	private OnUpdateScheduleListener listener;
	private long teamID;
	private GameDataAdapter mGameDataAdapter;
	private TeamDataAdapter mTeamDataAdapter;
	private GameArrayAdapter mGameArrayAdapter;
	private ArrayList<Game> games;
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnUpdateScheduleListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnUpdateScheduleListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_previous_games, container,
				false);
		
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
		listener.updateSchedule();
	}

	public void updateGames(ArrayList<Game> games) {
		this.games = games;
		
		if (getActivity() != null) {
			mGameArrayAdapter = new GameArrayAdapter(getActivity(),
					R.layout.previous_game_item, games);
			mGameArrayAdapter.notifyDataSetChanged();
			setListAdapter(mGameArrayAdapter);
		}
	}
}
