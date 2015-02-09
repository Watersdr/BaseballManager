package edu.rosehulman.baseballmanager;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LineupFragment extends Fragment {
	private long teamID;
	private PlayerDataAdapter mPlayerDataAdapter;
	private SimpleCursorAdapter mCursorAdapter;
	
	public LineupFragment(long teamID) {
		super();
		this.teamID = teamID;
		mPlayerDataAdapter = new PlayerDataAdapter(this.getActivity());
		mPlayerDataAdapter.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_lineup, container, false);
		
		DynamicListView listView = (DynamicListView) v.findViewById(R.id.lineup_listview);
        
        Cursor c = mPlayerDataAdapter.getTeamPlayers(teamID);
		String[] fromColumnsString = new String[] {PlayerDataAdapter.KEY_F_NAME};
		int[] toTextView = new int[] {R.id.player_name_item};
		mCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.text_view, c, fromColumnsString, toTextView, 0);
		
		//listView.setCheeseList(c.);
		listView.setAdapter(mCursorAdapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		return v;
	}
	
	public void updateLineup() {
		Cursor c = mPlayerDataAdapter.getTeamPlayers(teamID);
		mCursorAdapter.changeCursor(c);
	}
	
	@Override
	public void onDestroy() {
		mPlayerDataAdapter.close();
		super.onDestroy();
	}
}
