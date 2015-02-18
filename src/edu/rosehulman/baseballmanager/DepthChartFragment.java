package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DepthChartFragment extends Fragment {
	private OnUpdateRosterListener listener;
	private long teamID;
	private PlayerDataAdapter mPlayerDataAdapter;
	private StablePlayerAdapter mCursorAdapter;
    private ArrayList<Player> players;
	private ArrayList<PositionSpinner> spinners;
    
	public DepthChartFragment(long teamID, ArrayList<Player> players) {
		super();
		this.teamID = teamID;
		this.players = players;
		this.spinners = new ArrayList<PositionSpinner>();
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
		View v = inflater.inflate(R.layout.fragment_depth_chart, container, false);
		PositionSpinner spinner;
		this.spinners = new ArrayList<PositionSpinner>();
		spinners.add(new PositionSpinner(getActivity(), null));
		
		spinner = (PositionSpinner) v.findViewById(R.id.pitcher_spinner);
		spinner.init(StablePlayerAdapter.DC_P, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.catcher_spinner);
		spinner.init(StablePlayerAdapter.DC_C, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.first_base_spinner);
		spinner.init(StablePlayerAdapter.DC_1B, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.second_base_spinner);
		spinner.init(StablePlayerAdapter.DC_2B, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.third_base_spinner);
		spinner.init(StablePlayerAdapter.DC_3B, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.short_stop_spinner);
		spinner.init(StablePlayerAdapter.DC_SS, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.left_field_spinner);
		spinner.init(StablePlayerAdapter.DC_LF, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.center_field_spinner);
		spinner.init(StablePlayerAdapter.DC_CF, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		spinner = (PositionSpinner) v.findViewById(R.id.right_field_spinner);
		spinner.init(StablePlayerAdapter.DC_RF, getActivity(), mPlayerDataAdapter);
		spinners.add(spinner);
		
		updateDepthChart(players);
		return v;
	}

	public void updateDepthChart(ArrayList<Player> players) {
		this.players = players;
		
		if(spinners.size() > 0) {
			for (PositionSpinner spinner : spinners) {
				spinner.clearSpinner();
			}
			
			for (Player p : players) {
				if (p.getDc_P() > -1) {
					spinners.get(StablePlayerAdapter.DC_P).addPlayer(p, p.getDc_P());
				}
				if (p.getDc_C() > -1) {
					spinners.get(StablePlayerAdapter.DC_C).addPlayer(p, p.getDc_C());
				}
				if (p.getDc_1B() > -1) {
					spinners.get(StablePlayerAdapter.DC_1B).addPlayer(p, p.getDc_1B());
				}
				if (p.getDc_2B() > -1) {
					spinners.get(StablePlayerAdapter.DC_2B).addPlayer(p, p.getDc_2B());
				}
				if (p.getDc_3B() > -1) {
					spinners.get(StablePlayerAdapter.DC_3B).addPlayer(p, p.getDc_3B());
				}
				if (p.getDc_SS() > -1) {
					spinners.get(StablePlayerAdapter.DC_SS).addPlayer(p, p.getDc_SS());
				}
				if (p.getDc_LF() > -1) {
					spinners.get(StablePlayerAdapter.DC_LF).addPlayer(p, p.getDc_LF());
				}
				if (p.getDc_CF() > -1) {
					spinners.get(StablePlayerAdapter.DC_CF).addPlayer(p, p.getDc_CF());
				}
				if (p.getDc_RF() > -1) {
					spinners.get(StablePlayerAdapter.DC_RF).addPlayer(p, p.getDc_RF());
				}
			}
		}
	}
}
