package edu.rosehulman.baseballmanager.Activities;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import edu.rosehulman.baseballmanager.Game;
import edu.rosehulman.baseballmanager.GameDataAdapter;
import edu.rosehulman.baseballmanager.Inning;
import edu.rosehulman.baseballmanager.InningDataAdapter;
import edu.rosehulman.baseballmanager.Player;
import edu.rosehulman.baseballmanager.PlayerDataAdapter;
import edu.rosehulman.baseballmanager.PlayerStats;
import edu.rosehulman.baseballmanager.PlayerStatsDataAdapter;
import edu.rosehulman.baseballmanager.R;
import edu.rosehulman.baseballmanager.SplashScreen;
import edu.rosehulman.baseballmanager.TeamDataAdapter;

public class AddGameActivity extends Activity {

	private TeamDataAdapter teamDataAdapter;
	private GameDataAdapter gameDataAdapter;
	private InningDataAdapter mInningDataAdapter;
	private PlayerDataAdapter mPlayerDataAdapter;
	private PlayerStatsDataAdapter mPlayerStatsDataAdapter;
	private Button mDateButton;
	private Button mTimeButton;
	private int yr;
	private int mon;
	private int date;
	private int hour;
	private int min;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game);

		teamDataAdapter = new TeamDataAdapter(this);
		gameDataAdapter = new GameDataAdapter(this);
		mInningDataAdapter = new InningDataAdapter(this);
		mPlayerDataAdapter = new PlayerDataAdapter(this);
		mPlayerStatsDataAdapter = new PlayerStatsDataAdapter(this);
		teamDataAdapter.open();
		gameDataAdapter.open();
		mInningDataAdapter.open();
		mPlayerDataAdapter.open();
		mPlayerStatsDataAdapter.open();

		Button saveButton = (Button) findViewById(R.id.save_game_button);
		mDateButton = (Button) findViewById(R.id.date_button);
		mTimeButton = (Button) findViewById(R.id.time_button);
		Log.d(SplashScreen.BM, mDateButton.getText().toString());

		mDateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment f = new DatePickerFragment();
				f.show(getFragmentManager(), "time picker");
			}
		});

		mTimeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment f = new TimePickerFragment();
				f.show(getFragmentManager().beginTransaction(), "date picker");
			}
		});

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText homeTeam = (EditText) findViewById(R.id.home_team_edittext);
				EditText awayTeam = (EditText) findViewById(R.id.away_team_edittext);

				@SuppressWarnings("deprecation")
				Date setDate = new Date(yr-1900, mon-1, date, hour, min);
				String homeTeamName = homeTeam.getText().toString();
				String awayTeamName = awayTeam.getText().toString();

				long homeTeamID = teamDataAdapter.getTeamID(homeTeamName);
				long awayTeamID = teamDataAdapter.getTeamID(awayTeamName);
				Game game = new Game(setDate, homeTeamID, awayTeamID);
				long gameID = gameDataAdapter.addGame(game);
				for (int i = 1; i <= 9; i++) {
					Inning inning = new Inning(gameID, i);
					mInningDataAdapter.addInning(inning);
				}
				for (Player p : mPlayerDataAdapter.getTeamLineupById(homeTeamID)) {
					PlayerStats ps = new PlayerStats(p.getID(), gameID, p.getBattingOrder());
					mPlayerStatsDataAdapter.addPlayerStats(ps);
				}
				for (Player p : mPlayerDataAdapter.getTeamLineupById(awayTeamID)) {
					PlayerStats ps = new PlayerStats(p.getID(), gameID, p.getBattingOrder());
					mPlayerStatsDataAdapter.addPlayerStats(ps);
				}
				setResult(RESULT_OK);
				finish();
			}
		});
	}

	private class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			hour = hourOfDay;
			min = minute;
			mTimeButton.setText(String.format("%02d:%02d", hour%12, min));
		}
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			yr = year;
			mon = month;
			date = day;

			mDateButton.setText("" + yr + "-" + (mon+1) + "-" + date);
		}
	}

}
