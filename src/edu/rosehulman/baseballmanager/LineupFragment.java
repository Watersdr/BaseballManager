package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class LineupFragment extends Fragment {
	private OnUpdateRosterListener listener;
	private long teamID;
	private PlayerDataAdapter mPlayerDataAdapter;
	private StablePlayerAdapter mCursorAdapter;
	private DynamicListView mlistView;
    private long mSelectedPlayerID;
    private ArrayList<Player> players;
    
	private static final int REQUEST_PLAYER_ADDEDIT = 1;
	
	public LineupFragment(long teamID, ArrayList<Player> playerList) {
		super();
		this.teamID = teamID;
		this.players = playerList;
		mPlayerDataAdapter = new PlayerDataAdapter(this.getActivity());
		mPlayerDataAdapter.open();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (OnUpdateRosterListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnUpdateRosterListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_lineup, container, false);		
		mlistView = (DynamicListView) v.findViewById(R.id.lineup_listview);
        
        ArrayList<String> playerNames = new ArrayList<String>();
        for(int i = 0; i < players.size(); i++){
        	playerNames.add(players.get(i).getFName() + " " + players.get(i).getLName());
        }
        
		mCursorAdapter = new StablePlayerAdapter(getActivity(), R.layout.text_view, players, playerNames, mPlayerDataAdapter, StablePlayerAdapter.BATTING_ORDER);
		
		mlistView.setPlayerNameList(playerNames);
		mlistView.setPlayerList(players);
		mlistView.setAdapter(mCursorAdapter);
		mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
    	mlistView.setOnItemClickListener(mOnItemClickListener);
		return v;
	}
	
	public void updateLineup(ArrayList<Player> players) {
	    this.players = players;
	    ArrayList<String> playerNames = new ArrayList<String>();
        for(int i = 0; i < players.size(); i++){
        	playerNames.add(players.get(i).getFName() + " " + players.get(i).getLName());
        }
	    
		mCursorAdapter = new StablePlayerAdapter(getActivity(), R.layout.text_view, players, playerNames, mPlayerDataAdapter, StablePlayerAdapter.BATTING_ORDER);
		
		mlistView.setPlayerNameList(playerNames);
		mlistView.setPlayerList(players);
		mlistView.setAdapter(mCursorAdapter);
		mCursorAdapter.notifyDataSetChanged();
	}
    
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mSelectedPlayerID = id;
			getActivity().startActionMode(mActionModeCallback);
		}		
	};
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			Player p = mPlayerDataAdapter.getPlayer(mSelectedPlayerID);
			mode.setTitle(p.getFName() + " " + p.getLName());
			MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.lineup, menu);
	        return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			int id = item.getItemId();
			switch (id) {
			case R.id.remove_player:
				showDeleteDialog();
				return true;
			case R.id.edit_player:
				Intent i = new Intent(getActivity(), AddEditPlayerActivity.class);
				i.putExtra(PlayerDataAdapter.KEY_TEAM_ID, teamID);
				i.putExtra(PlayerDataAdapter.KEY_ID, mSelectedPlayerID);
				startActivityForResult(i, REQUEST_PLAYER_ADDEDIT);
				return true;
			case R.id.add_player:
				Intent i1 = new Intent(getActivity(), AddEditPlayerActivity.class);
				i1.putExtra(PlayerDataAdapter.KEY_TEAM_ID, teamID);
				// -1 To represent new player
				i1.putExtra(PlayerDataAdapter.KEY_ID, -1);
				startActivityForResult(i1, REQUEST_PLAYER_ADDEDIT);
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
				builder.setMessage(R.string.confirm_message);
				builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mPlayerDataAdapter.removePlayer(mSelectedPlayerID);
						listener.updateRoster();
					}
				});
				builder.setNegativeButton(android.R.string.cancel, null);
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "Delete Player");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){

        // See which child activity is calling us back. 
        switch (requestCode) {
            case REQUEST_PLAYER_ADDEDIT:
                if (resultCode == Activity.RESULT_OK){
                	listener.updateRoster();
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
