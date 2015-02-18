package edu.rosehulman.baseballmanager;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PitchingStatsFragment extends Fragment {
	private Cursor statsCursor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_pitching_stats, container, false);
		
		ListView list = (ListView) v.findViewById(R.id.pitching_stats_list_view);
		
	 	String[] fromColumns = new String[] {PlayerDataAdapter.KEY_F_NAME, PlayerStatsDataAdapter.KEY_IP, PlayerStatsDataAdapter.KEY_P_R, PlayerStatsDataAdapter.KEY_P_ER, PlayerStatsDataAdapter.KEY_P_K, PlayerStatsDataAdapter.KEY_P_BB, PlayerStatsDataAdapter.KEY_P_ERA};
	 	int[] toTextViews = new int[] {R.id.pitcher_name, R.id.innings_pitched, R.id.runs, R.id.earned_runs, R.id.strike_outs_pitcher, R.id.walks_pitcher, R.id.era};
	 	SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.stat_view_pitching, this.statsCursor, fromColumns, toTextViews, 0);
		
		list.setAdapter(mCursorAdapter);
		return v;
	}
	
	public PitchingStatsFragment(Cursor c) {
		this.statsCursor = c;
	}
}
