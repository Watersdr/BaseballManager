package edu.rosehulman.baseballmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class RecapBoxScoreArrayAdapter extends ArrayAdapter<String> {
	
	private long mGameID;
	private InningDataAdapter mInningDataAdapter;
	private ArrayList<TextView> inningViews;
	private TextView homeScore, awayScore;
	
	public RecapBoxScoreArrayAdapter(Context context, int resource, List<String> teams, long gameID, TextView homeScore, TextView awayScore) {
		super(context, R.layout.box_score_item, R.id.team_name, teams);
		this.homeScore = homeScore;
		this.awayScore = awayScore;
		
		mInningDataAdapter = new InningDataAdapter(getContext());
		mInningDataAdapter.open();
		mGameID = gameID;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		
		if (position == 0) {
			return v;
		}

		TextView name = (TextView) v.findViewById(R.id.team_name);
		inningViews = new ArrayList<TextView>();
		inningViews.add((TextView) v.findViewById(R.id.inning_1));
		inningViews.add((TextView) v.findViewById(R.id.inning_2));
		inningViews.add((TextView) v.findViewById(R.id.inning_3));
		inningViews.add((TextView) v.findViewById(R.id.inning_4));
		inningViews.add((TextView) v.findViewById(R.id.inning_5));
		inningViews.add((TextView) v.findViewById(R.id.inning_6));
		inningViews.add((TextView) v.findViewById(R.id.inning_7));
		inningViews.add((TextView) v.findViewById(R.id.inning_8));
		inningViews.add((TextView) v.findViewById(R.id.inning_9));
		
		for (int i = 1; i <= 9; i++) {
			final int x = i;
			inningViews.get(i-1).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showInningRunsDialog(x, position == 1, getItem(position));
				}
			});
		}
		
		Cursor c = mInningDataAdapter.getInningsForGame(mGameID);
		
		int runTotal = 0;
		int hitTotal = 0;
		int errorTotal = 0;
		name.setText(getItem(position));
		
		int currentInnNum = 1;
		while (c.moveToNext()) {
			int inningRuns  = c.getInt(c.getColumnIndexOrThrow(position == 1 ? InningDataAdapter.KEY_AWAY_RUNS : InningDataAdapter.KEY_HOME_RUNS));
			int inningHits = c.getInt(c.getColumnIndexOrThrow(position == 1 ? InningDataAdapter.KEY_AWAY_HITS : InningDataAdapter.KEY_HOME_HITS));
			int inningErrors = c.getInt(c.getColumnIndexOrThrow(position == 1 ? InningDataAdapter.KEY_AWAY_ERRORS : InningDataAdapter.KEY_HOME_ERRORS));
			errorTotal += inningErrors;
			hitTotal += inningHits;
			runTotal += inningRuns;
			
			inningViews.get(currentInnNum - 1).setText(""+inningRuns);
			currentInnNum++;
		}
		
		if (position == 1) {
			awayScore.setText(getItem(position) + ": " + runTotal);
		} else {
			homeScore.setText(getItem(position) + ": " + runTotal);
		}
		((TextView) v.findViewById(R.id.runs_total)).setText(""+runTotal);
		((TextView) v.findViewById(R.id.hit_total)).setText(""+hitTotal);
		((TextView) v.findViewById(R.id.error_total)).setText(""+errorTotal);
		return v;
	}
	
	protected void showInningRunsDialog(final int inning, final boolean awayTeam, final String teamName) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle b) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
				LayoutInflater inflater = getActivity().getLayoutInflater();
			    View dialogView = inflater.inflate(R.layout.dialog_inning, null);
			    builder.setView(dialogView);
			    final NumberPicker runsPicker = (NumberPicker) dialogView.findViewById(R.id.runsPicker);
			    final NumberPicker hitsPicker = (NumberPicker) dialogView.findViewById(R.id.hitsPicker);
			    final NumberPicker errorsPicker = (NumberPicker) dialogView.findViewById(R.id.errorsPicker);
			    final Inning i = mInningDataAdapter.getInning(mGameID, inning);
			    
			    runsPicker.setMaxValue(100);
			    runsPicker.setWrapSelectorWheel(false);
			    runsPicker.setValue(awayTeam ? i.getAwayRuns() : i.getHomeRuns());
			    hitsPicker.setMaxValue(100);
			    hitsPicker.setWrapSelectorWheel(false);
			    hitsPicker.setValue(awayTeam ? i.getAwayHits() : i.getHomeHits());
			    errorsPicker.setMaxValue(100);
			    errorsPicker.setWrapSelectorWheel(false);
			    errorsPicker.setValue(awayTeam ? i.getAwayErrors() : i.getHomeErrors());
			    
			    ((TextView) dialogView.findViewById(R.id.inning_team)).setText(teamName);
			    ((TextView) dialogView.findViewById(R.id.inning_num)).setText(getString(R.string.inning, inning));
			    Button button = (Button) dialogView.findViewById(R.id.save_inning);
			    button.setOnClickListener(new OnClickListener() {
			       	@Override
			       	public void onClick(View v) {
						if(awayTeam) {
							i.setAwayRuns(runsPicker.getValue());
							i.setAwayHits(hitsPicker.getValue());
							i.setAwayErrors(errorsPicker.getValue());
						} else {
							i.setHomeRuns(runsPicker.getValue());
							i.setHomeHits(hitsPicker.getValue());
							i.setHomeErrors(errorsPicker.getValue());
						}
						mInningDataAdapter.updateScore(i);
						Toast.makeText(getActivity(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
			            dismiss();
			       	}
			 	});
				return builder.create();
			}
			
			@Override
			public void onDismiss(DialogInterface dialog){
				notifyDataSetChanged();
			}
		};
		df.show(((Activity) getContext()).getFragmentManager(), "Inning Runs");
	}

}
