package edu.rosehulman.baseballmanager;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BattingDefenseStatsFragment extends Fragment {
	private Cursor statsCursor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_batting_defense_stats, container, false);
		
		ListView list = (ListView) v.findViewById(R.id.batting_defense_stats_list_view);
		
	 	String[] fromColumns = new String[] {PlayerDataAdapter.KEY_F_NAME, PlayerStatsDataAdapter.KEY_ABS, PlayerStatsDataAdapter.KEY_H, PlayerStatsDataAdapter.KEY_K, PlayerStatsDataAdapter.KEY_BB, PlayerStatsDataAdapter.KEY_AVG, PlayerStatsDataAdapter.KEY_E};
	 	int[] toTextViews = new int[] {R.id.batter_name, R.id.at_bats, R.id.hits, R.id.strike_outs_batter, R.id.walks_batter, R.id.batting_average, R.id.errors};
	 	SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.stat_view_batting_defense, this.statsCursor, fromColumns, toTextViews, 0);
		
		list.setAdapter(mCursorAdapter);
		return v;
	}

	public BattingDefenseStatsFragment(Cursor c) {
		this.statsCursor = c;
	}
	
}