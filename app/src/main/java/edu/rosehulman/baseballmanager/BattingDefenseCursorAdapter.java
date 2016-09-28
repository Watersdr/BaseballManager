package edu.rosehulman.baseballmanager;

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
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BattingDefenseCursorAdapter extends SimpleCursorAdapter {
	private Activity activity;
	private PlayerStatsDataAdapter mPlayerStatsDataAdapter;
	private long playerStatsID;

	public BattingDefenseCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags ) {
		super(context, layout, c, from, to, flags);
		this.activity = (Activity) context;
		mPlayerStatsDataAdapter = new PlayerStatsDataAdapter(context);
		mPlayerStatsDataAdapter.open();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 	View view = super.getView(position, convertView, parent);
	 	Cursor c = getCursor();
		this.playerStatsID = c.getLong(c.getColumnIndexOrThrow(PlayerStatsDataAdapter.KEY_ID));
	 	
	 	view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openStatsDialog();				
			}
		});
	 	
		return view;
    }

	private void openStatsDialog() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle b) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
				final Cursor c = getCursor();
				
				LayoutInflater inflater = getActivity().getLayoutInflater();
			    View dialogView = inflater.inflate(R.layout.dialog_batting_stats, null);
			    builder.setView(dialogView);
			    final NumberPicker abPicker = (NumberPicker) dialogView.findViewById(R.id.abPicker);
			    final NumberPicker hitPicker = (NumberPicker) dialogView.findViewById(R.id.hitPicker);
			    final NumberPicker walksPicker = (NumberPicker) dialogView.findViewById(R.id.walksPicker);
			    final NumberPicker kPicker = (NumberPicker) dialogView.findViewById(R.id.kPicker);
			    final NumberPicker errorsPicker = (NumberPicker) dialogView.findViewById(R.id.errorsPicker);
			    
			    abPicker.setMaxValue(100);
			    abPicker.setWrapSelectorWheel(false);
			    abPicker.setValue(c.getInt(c.getColumnIndexOrThrow(PlayerStatsDataAdapter.KEY_ABS)));
			    hitPicker.setMaxValue(100);
			    hitPicker.setWrapSelectorWheel(false);
			    hitPicker.setValue(c.getInt(c.getColumnIndexOrThrow(PlayerStatsDataAdapter.KEY_H)));
			    walksPicker.setMaxValue(100);
			    walksPicker.setWrapSelectorWheel(false);
			    walksPicker.setValue(c.getInt(c.getColumnIndexOrThrow(PlayerStatsDataAdapter.KEY_BB)));
			    kPicker.setMaxValue(100);
			    kPicker.setWrapSelectorWheel(false);
			    kPicker.setValue(c.getInt(c.getColumnIndexOrThrow(PlayerStatsDataAdapter.KEY_K)));
			    errorsPicker.setMaxValue(100);
			    errorsPicker.setWrapSelectorWheel(false);
			    errorsPicker.setValue(c.getInt(c.getColumnIndexOrThrow(PlayerStatsDataAdapter.KEY_E)));
			    
			    ((TextView) dialogView.findViewById(R.id.batting_player)).setText(c.getString(c.getColumnIndexOrThrow(PlayerDataAdapter.KEY_L_NAME)));

			    Button button = (Button) dialogView.findViewById(R.id.save_inning);
			    button.setOnClickListener(new OnClickListener() {
			       	@Override
			       	public void onClick(View v) {
			       		PlayerStats ps = mPlayerStatsDataAdapter.getPlayerStats(playerStatsID);
			       		ps.setAbs(abPicker.getValue());
			       		ps.setH(hitPicker.getValue());
			       		ps.setBb(walksPicker.getValue());
			       		ps.setK(kPicker.getValue());
			       		ps.setE(errorsPicker.getValue());
			       		mPlayerStatsDataAdapter.updatePlayerStats(ps);
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
		df.show(this.activity.getFragmentManager(), "Batting/Defense Stats");
	}

}
