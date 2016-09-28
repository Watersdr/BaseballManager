package edu.rosehulman.baseballmanager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import edu.rosehulman.baseballmanager.Player;
import edu.rosehulman.baseballmanager.PlayerDataAdapter;
import edu.rosehulman.baseballmanager.R;

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
			mPlayer = mPlayerDataAdapter.getPlayerById(id);
			fName.setText(mPlayer.getFirstName());
			lName.setText(mPlayer.getLastName());
			number.setText(""+mPlayer.getNumber());
			chkP.setChecked(mPlayer.getDepthChartP() > -1);
			chkC.setChecked(mPlayer.getDepthChartC() > -1);
			chk1B.setChecked(mPlayer.getDepthChart1B() > -1);
			chk2B.setChecked(mPlayer.getDepthChart2B() > -1);
			chk3B.setChecked(mPlayer.getDepthChart3B() > -1);
			chkSS.setChecked(mPlayer.getDepthChartSS() > -1);
			chkLF.setChecked(mPlayer.getDepthChartLF() > -1);
			chkCF.setChecked(mPlayer.getDepthChartCF() > -1);
			chkRF.setChecked(mPlayer.getDepthChartRF() > -1);
		}
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (id == -1) {
					Player p = new Player(fName.getText().toString(), lName.getText().toString(), Integer.parseInt(number.getText().toString()), teamID);
					p.setDepthChartP(chkP.isChecked() ? 0 : -1);
					p.setDepthChartC(chkC.isChecked() ? 0 : -1);
					p.setDepthChart1B(chk1B.isChecked() ? 0 : -1);
					p.setDepthChart2B(chk2B.isChecked() ? 0 : -1);
					p.setDepthChart3B(chk3B.isChecked() ? 0 : -1);
					p.setDepthChartSS(chkSS.isChecked() ? 0 : -1);
					p.setDepthChartLF(chkLF.isChecked() ? 0 : -1);
					p.setDepthChartCF(chkCF.isChecked() ? 0 : -1);
					p.setDepthChartRF(chkRF.isChecked() ? 0 : -1);
					p.setBattingOrder(-1);
					mPlayerDataAdapter.addPlayer(p);
				} else {
					mPlayer.setDepthChartP(chkP.isChecked() ? (mPlayer.getDepthChartP() == -1 ? 0 : mPlayer.getDepthChartP()) : -1);
					mPlayer.setDepthChartC(chkC.isChecked() ? (mPlayer.getDepthChartC() == -1 ? 0 : mPlayer.getDepthChartC()) : -1);
					mPlayer.setDepthChart1B(chk1B.isChecked() ? (mPlayer.getDepthChart1B() == -1 ? 0 : mPlayer.getDepthChart1B()) : -1);
					mPlayer.setDepthChart2B(chk2B.isChecked() ? (mPlayer.getDepthChart2B() == -1 ? 0 : mPlayer.getDepthChart2B()) : -1);
					mPlayer.setDepthChart3B(chk3B.isChecked() ? (mPlayer.getDepthChart3B() == -1 ? 0 : mPlayer.getDepthChart3B()) : -1);
					mPlayer.setDepthChartSS(chkSS.isChecked() ? (mPlayer.getDepthChartSS() == -1 ? 0 : mPlayer.getDepthChartSS()) : -1);
					mPlayer.setDepthChartLF(chkLF.isChecked() ? (mPlayer.getDepthChartLF() == -1 ? 0 : mPlayer.getDepthChartLF()) : -1);
					mPlayer.setDepthChartCF(chkCF.isChecked() ? (mPlayer.getDepthChartCF() == -1 ? 0 : mPlayer.getDepthChartCF()) : -1);
					mPlayer.setDepthChartRF(chkRF.isChecked() ? (mPlayer.getDepthChartRF() == -1 ? 0 : mPlayer.getDepthChartRF()) : -1);
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
