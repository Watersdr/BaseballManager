package edu.rosehulman.baseballmanager;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;

public class PositionSpinner extends Spinner {
	private ArrayList<Player> players;
	private ArrayList<String> playerNames;
	private int position;
	private Activity mActivity;
	private PlayerDataAdapter mPlayerDataAdapter;
	private StablePlayerAdapter mPlayerAdapter;
	private HashMap<Long, Integer> dcOrder;
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showDCDialog();
		}
	};
	
	public PositionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public void init(int position, Activity activity, PlayerDataAdapter adapter) {
		players = new ArrayList<Player>();
		this.position = position;
		this.dcOrder = new HashMap<Long, Integer>();
		this.mActivity = activity;
		this.mPlayerDataAdapter = adapter;
		this.setOnClickListener(mOnClickListener);
	}
	
	public void clearSpinner() {
		this.dcOrder = new HashMap<Long, Integer>();
		players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player p, int order) {
		for (int i = 0; i < players.size(); i++) {
			if (dcOrder.get(players.get(i)) > order) {
				players.add(i, p);
				dcOrder.put(p.getID(), order);
				break;
			}
		}
		
		playerNames = new ArrayList<String>();
        for(int i = 0; i < players.size(); i++){
        	playerNames.add(players.get(i).getFName() + " " + players.get(i).getLName());
        }
        
        mPlayerAdapter = new StablePlayerAdapter(mActivity, R.layout.text_view, players, playerNames, mPlayerDataAdapter, position);
        this.setAdapter(mPlayerAdapter);
	}
	
	private void showDCDialog() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle b) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			    LayoutInflater inflater = getActivity().getLayoutInflater();
			    View dialogView = inflater.inflate(R.layout.activity_lineup, null);
			    
				DynamicListView mlistView = (DynamicListView) dialogView.findViewById(R.id.lineup_listview);				
				mlistView.setPlayerNameList(playerNames);
				mlistView.setPlayerList(players);
				mlistView.setAdapter(mPlayerAdapter);
				mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				
			    builder.setView(dialogView);
			    return builder.create();
			}
		};
		df.show(mActivity.getFragmentManager(), "DC Dialog");
	}
	
}
