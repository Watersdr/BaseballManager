package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

public class DepthChartFragment extends Fragment {
	private long teamID;
	private PlayerDataAdapter mPlayerDataAdapter;
	private StablePlayerAdapter mCursorAdapter;
    private ArrayList<Player> players;
	//private Spinner s_p, s_c, s_1b, s_2b, s_p;
    
	public DepthChartFragment(long teamID, ArrayList<Player> players) {
		super();
		this.teamID = teamID;
		this.players = players;
		mPlayerDataAdapter = new PlayerDataAdapter(this.getActivity());
		mPlayerDataAdapter.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = inflater.inflate(R.layout.activity_depth_chart, container, false);
		return v;
	}

	public void updateDepthChart() {
		// TODO Auto-generated method stub
		
	}
}
