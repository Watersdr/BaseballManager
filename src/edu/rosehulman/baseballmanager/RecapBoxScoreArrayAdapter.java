package edu.rosehulman.baseballmanager;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecapBoxScoreArrayAdapter extends ArrayAdapter<String> {
	
	private long mGameID;
	private InningDataAdapter mInningDataAdapter;
	
	public RecapBoxScoreArrayAdapter(Context context, int resource, List<String> teams, long gameID) {
		super(context, R.layout.box_score_item, R.id.team_name, teams);

		mInningDataAdapter = new InningDataAdapter(getContext());
		mInningDataAdapter.open();
		mGameID = gameID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View v = inflater.inflate(R.layout.box_score_item, null);
		if (position == 0) {
			return v;
		}
		Log.d(SplashScreen.BM, "postition 0: " + getItem(0));
		Log.d(SplashScreen.BM, "postition 1: " + getItem(1));
		Log.d(SplashScreen.BM, "postition 2: " + getItem(2));
		


		TextView name = (TextView) v.findViewById(R.id.team_name);
		TextView inn1 = (TextView) v.findViewById(R.id.inning_1);
		TextView inn2 = (TextView) v.findViewById(R.id.inning_2);
		TextView inn3 = (TextView) v.findViewById(R.id.inning_3);
		TextView inn4 = (TextView) v.findViewById(R.id.inning_4);
		TextView inn5 = (TextView) v.findViewById(R.id.inning_5);
		TextView inn6 = (TextView) v.findViewById(R.id.inning_6);
		TextView inn7 = (TextView) v.findViewById(R.id.inning_7);
		TextView inn8 = (TextView) v.findViewById(R.id.inning_8);
		TextView inn9 = (TextView) v.findViewById(R.id.inning_9);

		TextView runs = (TextView) v.findViewById(R.id.runs_total);
		TextView hits = (TextView) v.findViewById(R.id.hit_total);
		TextView errors = (TextView) v.findViewById(R.id.error_total);
		
		Cursor c = mInningDataAdapter.getInningsForGame(mGameID);
		
		int runTotal = 0;
		int hitTotal = 0;
		int errorTotal = 0;
		
		//This code makes me want to cry
		int currentInnNum = 1;
		if (position == 1) {
			name.setText(getItem(position));
			while (c.moveToNext()) {
				int inningRuns  = c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_HOME_RUNS));
				int inningHits = c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_HOME_HITS));
				int inningErrors = c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_HOME_ERRORS));
				errorTotal += inningErrors;
				hitTotal += inningHits;
				runTotal += inningRuns;
				switch (currentInnNum) {
				case 1:
					inn1.setText(""+inningRuns);
					break;
				case 2:
					inn2.setText(""+inningRuns);
					break;
				case 3:
					inn3.setText(""+inningRuns);
					break;
				case 4:
					inn4.setText(""+inningRuns);
					break;
				case 5:
					inn5.setText(""+inningRuns);
					break;
				case 6:
					inn6.setText(""+inningRuns);
					break;
				case 7:
					inn7.setText(""+inningRuns);
					break;
				case 8:
					inn8.setText(""+inningRuns);
					break;
				case 9:
					inn9.setText(""+inningRuns);
					break;
					
				}
				currentInnNum++;
			}
		}
		if (position == 2) {
			name.setText(getItem(position));
			while (c.moveToNext()) {
				int inningRuns  = c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_AWAY_RUNS));
				int inningHits = c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_AWAY_HITS));
				int inningErrors = c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_AWAY_ERRORS));
				errorTotal += inningErrors;
				hitTotal += inningHits;
				runTotal += inningRuns;
				switch (currentInnNum) {
				case 1:
					inn1.setText(""+inningRuns);
					break;
				case 2:
					inn2.setText(""+inningRuns);
					break;
				case 3:
					inn3.setText(""+inningRuns);
					break;
				case 4:
					inn4.setText(""+inningRuns);
					break;
				case 5:
					inn5.setText(""+inningRuns);
					break;
				case 6:
					inn6.setText(""+inningRuns);
					break;
				case 7:
					inn7.setText(""+inningRuns);
					break;
				case 8:
					inn8.setText(""+inningRuns);
					break;
				case 9:
					inn9.setText(""+inningRuns);
					break;
					
				}
				currentInnNum++;
			}
		}
		if (currentInnNum == 1) {
			inn1.setText(""+0);
			inn2.setText(""+0);
			inn3.setText(""+0);
			inn4.setText(""+0);
			inn5.setText(""+0);
			inn6.setText(""+0);
			inn7.setText(""+0);
			inn8.setText(""+0);
			inn9.setText(""+0);
		}
		runs.setText(""+runTotal);
		hits.setText(""+hitTotal);
		errors.setText(""+errorTotal);		
		return v;
	}

}
