package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UpcomingGamesFragment extends ListFragment {

	private long teamID, mSelectedGameID;
	private GameDataAdapter mGameDataAdapter;
	private TeamDataAdapter mTeamDataAdapter;
	private GameArrayAdapter mGameArrayAdapter;
	private ArrayList<Game> games;
	private ActionMode mActionMode;

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
		View v = inflater.inflate(R.layout.activity_upcoming_games, container,
				false);

		// Cursor c = mGameDataAdapter.getUpcomingGamesCursor(teamID);
		Cursor c = mGameDataAdapter.getUpcomingGamesCursor(teamID);
		Log.d(SplashScreen.BM, "" + c.getCount());
		Log.d(SplashScreen.BM, "" + teamID);
		games = new ArrayList<Game>();
		while (c.moveToNext()) {
			Game g = mGameDataAdapter.getGame(c.getLong(0));
			Log.d(SplashScreen.BM, "adding game to adapter");
			games.add(g);
		}

		mGameArrayAdapter = new GameArrayAdapter(getActivity(),
				R.layout.upcoming_game_item, games);
		setListAdapter(mGameArrayAdapter);
		
		return v;
	}

	public void updateGames() {
		if (getActivity() != null) {
			Cursor c = mGameDataAdapter.getUpcomingGamesCursor(teamID);
			games = new ArrayList<Game>();
			while (c.moveToNext()) {
				Game g = mGameDataAdapter.getGame(c.getLong(0));
				games.add(g);
			}
			mGameArrayAdapter = new GameArrayAdapter(getActivity(),
					R.layout.upcoming_game_item, games);
			mGameArrayAdapter.notifyDataSetChanged();
			setListAdapter(mGameArrayAdapter);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mSelectedGameID = games.get(position).getID();
		mActionMode = getActivity().startActionMode(mActionModeCallback);
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle(R.string.game_selected);
			MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.game_recap, menu);
	        return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			int id = item.getItemId();
			switch (id) {
			case R.id.action_delete_game:
				showDeleteDialog();
				return true;
			default:
				return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) { 
		}
	};
	
	protected void showDeleteDialog() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle b) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.confirm_deletion);
				builder.setMessage(R.string.confirm_message_game);
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mGameDataAdapter.removeGame(mSelectedGameID);
						InningDataAdapter mInningDataAdapter = new InningDataAdapter(getActivity());
						PlayerStatsDataAdapter mPlayerStatsDataAdapter = new PlayerStatsDataAdapter(getActivity());
						mInningDataAdapter.open();
						mPlayerStatsDataAdapter.open();
						mInningDataAdapter.removeGame(mSelectedGameID);
						mPlayerStatsDataAdapter.removeGame(mSelectedGameID);
						
						updateGames();
						if (mActionMode != null) 
						{
							mActionMode.finish();
						}
					}
				});
				builder.setNegativeButton(android.R.string.cancel, null);
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "Delete Game");
	}
}
