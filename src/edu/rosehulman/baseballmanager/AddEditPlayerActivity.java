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
		final EditText number = (EditText) findViewById(R.id.player_number);
		final CheckBox chkP = (CheckBox) findViewById(R.id.chk_p);
		final CheckBox chkC = (CheckBox) findViewById(R.id.chk_c);
		final CheckBox chk1B = (CheckBox) findViewById(R.id.chk_1b);
		final CheckBox chk2B = (CheckBox) findViewById(R.id.chk_2b);
		final CheckBox chk3B = (CheckBox) findViewById(R.id.chk_3b);
		final CheckBox chkSS = (CheckBox) findViewById(R.id.chk_ss);
		final CheckBox chkLF = (CheckBox) findViewById(R.id.chk_lf);
		final CheckBox chkCF = (CheckBox) findViewById(R.id.chk_cf);
		final CheckBox chkRF = (CheckBox) findViewById(R.id.chk_rf);
		
		Intent i = getIntent();
		id = i.getLongExtra(PlayerDataAdapter.KEY_ID, -1);
		teamID = i.getLongExtra(PlayerDataAdapter.KEY_TEAM_ID, -1);
		
		
		if (id > -1) {
			mPlayer = mPlayerDataAdapter.getPlayer(id);
			fName.setText(mPlayer.getFName());
			lName.setText(mPlayer.getLName());
			number.setText(""+mPlayer.getNumber());
			chkP.setChecked(mPlayer.getDc_P() > -1);
			chkC.setChecked(mPlayer.getDc_C() > -1);
			chk1B.setChecked(mPlayer.getDc_1B() > -1);
			chk2B.setChecked(mPlayer.getDc_2B() > -1);
			chk3B.setChecked(mPlayer.getDc_3B() > -1);
			chkSS.setChecked(mPlayer.getDc_SS() > -1);
			chkLF.setChecked(mPlayer.getDc_LF() > -1);
			chkCF.setChecked(mPlayer.getDc_CF() > -1);
			chkRF.setChecked(mPlayer.getDc_RF() > -1);
		}
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (id == -1) {
					Player p = new Player(fName.getText().toString(), lName.getText().toString(), Integer.parseInt(number.getText().toString()), teamID);
					p.setDc_P(chkP.isChecked() ? 0 : -1);
					p.setDc_C(chkC.isChecked() ? 0 : -1);
					p.setDc_1B(chk1B.isChecked() ? 0 : -1);
					p.setDc_2B(chk2B.isChecked() ? 0 : -1);
					p.setDc_3B(chk3B.isChecked() ? 0 : -1);
					p.setDc_SS(chkSS.isChecked() ? 0 : -1);
					p.setDc_LF(chkLF.isChecked() ? 0 : -1);
					p.setDc_CF(chkCF.isChecked() ? 0 : -1);
					p.setDc_RF(chkRF.isChecked() ? 0 : -1);
					p.setBattingOrder(-1);
					mPlayerDataAdapter.addPlayer(p);
				} else {
					mPlayer.setDc_P(chkP.isChecked() ? (mPlayer.getDc_P() == -1 ? 0 : mPlayer.getDc_P()) : -1);
					mPlayer.setDc_C(chkC.isChecked() ? (mPlayer.getDc_C() == -1 ? 0 : mPlayer.getDc_C()) : -1);
					mPlayer.setDc_1B(chk1B.isChecked() ? (mPlayer.getDc_1B() == -1 ? 0 : mPlayer.getDc_1B()) : -1);
					mPlayer.setDc_2B(chk2B.isChecked() ? (mPlayer.getDc_2B() == -1 ? 0 : mPlayer.getDc_2B()) : -1);
					mPlayer.setDc_3B(chk3B.isChecked() ? (mPlayer.getDc_3B() == -1 ? 0 : mPlayer.getDc_3B()) : -1);
					mPlayer.setDc_SS(chkSS.isChecked() ? (mPlayer.getDc_SS() == -1 ? 0 : mPlayer.getDc_SS()) : -1);
					mPlayer.setDc_LF(chkLF.isChecked() ? (mPlayer.getDc_LF() == -1 ? 0 : mPlayer.getDc_LF()) : -1);
					mPlayer.setDc_CF(chkCF.isChecked() ? (mPlayer.getDc_CF() == -1 ? 0 : mPlayer.getDc_CF()) : -1);
					mPlayer.setDc_RF(chkRF.isChecked() ? (mPlayer.getDc_RF() == -1 ? 0 : mPlayer.getDc_RF()) : -1);
					mPlayerDataAdapter.updatePlayer(mPlayer);
				}
				
				Intent returnIntent = new Intent();
			   	setResult(RESULT_OK, returnIntent);
			   	finish();
			}
		});
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		mPlayerDataAdapter.close();
//	}
//	
	
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
