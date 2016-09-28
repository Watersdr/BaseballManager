package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
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
				if (p.getDepthChartP() > -1) {
					spinners.get(StablePlayerAdapter.DC_P).addPlayer(p, p.getDepthChartP());
				}
				if (p.getDepthChartC() > -1) {
					spinners.get(StablePlayerAdapter.DC_C).addPlayer(p, p.getDepthChartC());
				}
				if (p.getDepthChart1B() > -1) {
					spinners.get(StablePlayerAdapter.DC_1B).addPlayer(p, p.getDepthChart1B());
				}
				if (p.getDepthChart2B() > -1) {
					spinners.get(StablePlayerAdapter.DC_2B).addPlayer(p, p.getDepthChart2B());
				}
				if (p.getDepthChart3B() > -1) {
					spinners.get(StablePlayerAdapter.DC_3B).addPlayer(p, p.getDepthChart3B());
				}
				if (p.getDepthChartSS() > -1) {
					spinners.get(StablePlayerAdapter.DC_SS).addPlayer(p, p.getDepthChartSS());
				}
				if (p.getDepthChartLF() > -1) {
					spinners.get(StablePlayerAdapter.DC_LF).addPlayer(p, p.getDepthChartLF());
				}
				if (p.getDepthChartCF() > -1) {
					spinners.get(StablePlayerAdapter.DC_CF).addPlayer(p, p.getDepthChartCF());
				}
				if (p.getDepthChartRF() > -1) {
					spinners.get(StablePlayerAdapter.DC_RF).addPlayer(p, p.getDepthChartRF());
				}
			}
		}
	}
}
