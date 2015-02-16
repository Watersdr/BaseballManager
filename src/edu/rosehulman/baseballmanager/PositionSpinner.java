package edu.rosehulman.baseballmanager;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class PositionSpinner extends Spinner{
	private ArrayList<Player> players;
	private SpinnerAdapter adapter;
	
	public PositionSpinner(Context context) {
		super(context);
		players = new ArrayList<Player>();
	}

	
	
}
