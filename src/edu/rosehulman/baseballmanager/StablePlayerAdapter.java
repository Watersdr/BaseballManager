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

    final int INVALID_ID = -1;
	private PlayerDataAdapter mPlayerDataAdapter;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StablePlayerAdapter(Context context, int textViewResourceId, List<Player> players, List<String> playerNames, PlayerDataAdapter playerDataAdapter) {
        super(context, textViewResourceId, playerNames);
        for (int i = 0; i < players.size(); ++i) {
            mIdMap.put(playerNames.get(i), (int) players.get(i).getID());
        }
        mPlayerDataAdapter = playerDataAdapter;
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
    	for (int i = 0; i < players.size(); i++) {
    		players.get(i).setBattingOrder(i);
        	mPlayerDataAdapter.updatePlayer(players.get(i));
    	}
    	Toast.makeText(getContext(), "Line-up Saved!", Toast.LENGTH_SHORT).show();
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
