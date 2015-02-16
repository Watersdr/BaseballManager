/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.rosehulman.baseballmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class StablePlayerAdapter extends ArrayAdapter<String> {

    public static final int BATTING_ORDER = 0;
    public static final int DC_P = 1;
    public static final int DC_C = 2;
    public static final int DC_1B = 3;
    public static final int DC_2B = 4;
    public static final int DC_3B = 5;
    public static final int DC_SS = 6;
    public static final int DC_LF = 7;
    public static final int DC_CF = 8;
    public static final int DC_RF = 9;
    
	final int INVALID_ID = -1;
	private PlayerDataAdapter mPlayerDataAdapter;
	private int position;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StablePlayerAdapter(Context context, int textViewResourceId, List<Player> players, List<String> playerNames, PlayerDataAdapter playerDataAdapter, int position) {
        super(context, textViewResourceId, playerNames);
        for (int i = 0; i < players.size(); ++i) {
            mIdMap.put(playerNames.get(i), (int) players.get(i).getID());
        }
        mPlayerDataAdapter = playerDataAdapter;
        this.position = position;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }
    
    public void saveState(ArrayList<Player> players) {
    	switch (this.position) {
    	case BATTING_ORDER:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setBattingOrder(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_P:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_P(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_C:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_C(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_1B:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_1B(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_2B:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_2B(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_3B:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_3B(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_SS:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_SS(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_LF:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_LF(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_CF:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_CF(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	case DC_RF:
    		for (int i = 0; i < players.size(); i++) {
        		players.get(i).setDc_RF(i);
            	mPlayerDataAdapter.updatePlayer(players.get(i));
        	}
    		break;
    	}
    		
    	
    	Toast.makeText(getContext(), "Order Saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 	// Recycles the convertView if it can.
	 	View view = super.getView(position, convertView, parent);
	 	
	 	TextView nameTextView = (TextView) view.findViewById(R.id.player_name_item);
	 	nameTextView.setText((position > 8 ? "" : ((position+1) + ". ")) + getItem(position));
	 	
		return view;
    }
}
