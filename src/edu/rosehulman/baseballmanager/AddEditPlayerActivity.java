package edu.rosehulman.baseballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddEditPlayerActivity extends Activity {

	private PlayerDataAdapter mPlayerDataAdapter;
	private long id, teamID;
	private Player mPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_player);
		
		mPlayerDataAdapter = new PlayerDataAdapter(this);
		mPlayerDataAdapter.open();
		
		Button saveButton = (Button) findViewById(R.id.save_player);
		final EditText fName = (EditText) findViewById(R.id.first_name);
		final EditText lName = (EditText) findViewById(R.id.last_name);
		final EditText number = (EditText) findViewById(R.id.number);
		
		Intent i = new Intent();
		id = i.getIntExtra(PlayerDataAdapter.KEY_ID, -1);
		teamID = i.getIntExtra(PlayerDataAdapter.KEY_TEAM_ID, -1);
		
		if (id > -1) {
			mPlayer = mPlayerDataAdapter.getPlayer(id);
			((CheckBox) findViewById(R.id.chk_p)).setSelected(mPlayer.getDc_P() > -1);
			((CheckBox) findViewById(R.id.chk_c)).setSelected(mPlayer.getDc_C() > -1);
			((CheckBox) findViewById(R.id.chk_1b)).setSelected(mPlayer.getDc_1B() > -1);
			((CheckBox) findViewById(R.id.chk_2b)).setSelected(mPlayer.getDc_2B() > -1);
			((CheckBox) findViewById(R.id.chk_3b)).setSelected(mPlayer.getDc_3B() > -1);
			((CheckBox) findViewById(R.id.chk_ss)).setSelected(mPlayer.getDc_SS() > -1);
			((CheckBox) findViewById(R.id.chk_lf)).setSelected(mPlayer.getDc_LF() > -1);
			((CheckBox) findViewById(R.id.chk_cf)).setSelected(mPlayer.getDc_CF() > -1);
			((CheckBox) findViewById(R.id.chk_rf)).setSelected(mPlayer.getDc_RF() > -1);
		}
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (id == -1) {
					Player p = new Player(fName.getText().toString(), lName.getText().toString(), Integer.parseInt(number.getText().toString()), teamID);
					p.setDc_P(((CheckBox) findViewById(R.id.chk_p)).isSelected() ? 0 : -1);
					p.setDc_C(((CheckBox) findViewById(R.id.chk_c)).isSelected() ? 0 : -1);
					p.setDc_1B(((CheckBox) findViewById(R.id.chk_1b)).isSelected() ? 0 : -1);
					p.setDc_2B(((CheckBox) findViewById(R.id.chk_2b)).isSelected() ? 0 : -1);
					p.setDc_3B(((CheckBox) findViewById(R.id.chk_3b)).isSelected() ? 0 : -1);
					p.setDc_SS(((CheckBox) findViewById(R.id.chk_ss)).isSelected() ? 0 : -1);
					p.setDc_LF(((CheckBox) findViewById(R.id.chk_lf)).isSelected() ? 0 : -1);
					p.setDc_CF(((CheckBox) findViewById(R.id.chk_cf)).isSelected() ? 0 : -1);
					p.setDc_RF(((CheckBox) findViewById(R.id.chk_rf)).isSelected() ? 0 : -1);
					mPlayerDataAdapter.addPlayer(p);
				} else {
					mPlayer.setDc_P(((CheckBox) findViewById(R.id.chk_p)).isSelected() ? (mPlayer.getDc_P() == -1 ? 0 : mPlayer.getDc_P()) : -1);
					mPlayer.setDc_C(((CheckBox) findViewById(R.id.chk_c)).isSelected() ? (mPlayer.getDc_C() == -1 ? 0 : mPlayer.getDc_C()) : -1);
					mPlayer.setDc_1B(((CheckBox) findViewById(R.id.chk_1b)).isSelected() ? (mPlayer.getDc_1B() == -1 ? 0 : mPlayer.getDc_1B()) : -1);
					mPlayer.setDc_2B(((CheckBox) findViewById(R.id.chk_2b)).isSelected() ? (mPlayer.getDc_2B() == -1 ? 0 : mPlayer.getDc_2B()) : -1);
					mPlayer.setDc_3B(((CheckBox) findViewById(R.id.chk_3b)).isSelected() ? (mPlayer.getDc_3B() == -1 ? 0 : mPlayer.getDc_3B()) : -1);
					mPlayer.setDc_SS(((CheckBox) findViewById(R.id.chk_ss)).isSelected() ? (mPlayer.getDc_SS() == -1 ? 0 : mPlayer.getDc_SS()) : -1);
					mPlayer.setDc_LF(((CheckBox) findViewById(R.id.chk_lf)).isSelected() ? (mPlayer.getDc_LF() == -1 ? 0 : mPlayer.getDc_LF()) : -1);
					mPlayer.setDc_CF(((CheckBox) findViewById(R.id.chk_cf)).isSelected() ? (mPlayer.getDc_CF() == -1 ? 0 : mPlayer.getDc_CF()) : -1);
					mPlayer.setDc_RF(((CheckBox) findViewById(R.id.chk_rf)).isSelected() ? (mPlayer.getDc_RF() == -1 ? 0 : mPlayer.getDc_RF()) : -1);
				}
				
				Intent returnIntent = new Intent();
			   	setResult(RESULT_OK, returnIntent);
			   	finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPlayerDataAdapter.close();
	}
	
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_edit_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	*/
}
