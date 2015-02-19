package edu.rosehulman.baseballmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class PitcherCursorAdapter extends SimpleCursorAdapter {

	public PitcherCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 	// Recycles the convertView if it can.
	 	View view = super.getView(position, convertView, parent);
	 	
		return view;
    }
}
