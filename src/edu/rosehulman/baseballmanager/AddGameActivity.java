package edu.rosehulman.baseballmanager;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddGameActivity extends Activity {
	
	private TeamDataAdapter teamDataAdapter;
	private GameDataAdapter gameDataAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);
		
		teamDataAdapter = new TeamDataAdapter(this);
		gameDataAdapter = new GameDataAdapter(this);
		teamDataAdapter.open();
		gameDataAdapter.open();
		
		Button saveButton = (Button) findViewById(R.id.save_game_button);
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText homeTeam = (EditText) findViewById(R.id.home_team_edittext);
				EditText awayTeam = (EditText) findViewById(R.id.away_team_edittext);
				DatePicker datePicker = (DatePicker) findViewById(R.id.date_of_game_picker);
				TimePicker timePicker = (TimePicker) findViewById(R.id.time_of_game_picker);
				
				@SuppressWarnings("deprecation")
				Date date = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
				String homeTeamName = homeTeam.getText().toString();
				String awayTeamName = awayTeam.getText().toString();
				
				Game game = new Game(date, teamDataAdapter.getTeamID(homeTeamName), teamDataAdapter.getTeamID(awayTeamName));
				gameDataAdapter.addGame(game);
				finish();
			}
		});
	}

}
