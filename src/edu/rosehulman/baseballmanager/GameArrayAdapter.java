package edu.rosehulman.baseballmanager;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameArrayAdapter extends ArrayAdapter<Game> {

	private int layoutResource;
	private InningDataAdapter mInningDataAdapter;

	public GameArrayAdapter(Context context, int resource, List<Game> games) {
		super(context, R.layout.upcoming_game_item, R.id.team1_textview, games);

		mInningDataAdapter = new InningDataAdapter(getContext());
		mInningDataAdapter.open();
		layoutResource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());

		Game game = getItem(position);
		
		if (layoutResource == R.layout.upcoming_game_item) {
			View v = inflater.inflate(R.layout.upcoming_game_item, null);

			TextView date = (TextView) v.findViewById(R.id.date_of_game);
			TextView time = (TextView) v.findViewById(R.id.gametime_textview);
			TextView awayTeamView = (TextView) v
					.findViewById(R.id.team1_textview);
			TextView homeTeamView = (TextView) v
					.findViewById(R.id.team2_textview);

			String[] fullDate = game.getGameDate().split(" ");

//			date.setText(fullDate[0] + " " + fullDate[1] + " " + fullDate[2]);
			date.setText(fullDate[0]);
//			String[] fullTimeString = fullDate[3].split(":");
//			String timeString = fullTimeString[0] + ":" + fullTimeString[1] + fullDate[4];
//			time.setText(timeString);
			time.setText(fullDate[1]);

			TeamDataAdapter adapter = new TeamDataAdapter(getContext());
			adapter.open();
			Team homeTeam = adapter.getTeam(game.getHomeID());
			Team awayTeam = adapter.getTeam(game.getAwayID());

			homeTeamView.setText(homeTeam.getName());
			awayTeamView.setText(awayTeam.getName());
			return v;
		}
		else {
			View v = inflater.inflate(R.layout.previous_game_item, null);

			Cursor c = mInningDataAdapter.getInningsForGame(game.getID());
			int homeRuns = 0;
			int awayRuns = 0;
			while (c.moveToNext()) {
				homeRuns += c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_HOME_RUNS));
				awayRuns += c.getInt(c.getColumnIndexOrThrow(InningDataAdapter.KEY_AWAY_RUNS));
			}

			TextView date = (TextView) v.findViewById(R.id.date_of_game);
			TextView awayTeamView = (TextView) v
					.findViewById(R.id.team1_textview);
			TextView homeTeamView = (TextView) v
					.findViewById(R.id.team2_textview);
			TextView awayScore = (TextView) v.findViewById(R.id.team1_score);
			TextView homeScore = (TextView) v.findViewById(R.id.team2_score);
			TextView recapOrEnter = (TextView) v.findViewById(R.id.recap_or_enter_stats);
			
			
			//Replace with results queried from database
			homeScore.setText("" + homeRuns);
			awayScore.setText("" + awayRuns);
			recapOrEnter.setText("Recap");

			String[] fullDate = game.getGameDate().split(" ");

//			date.setText(fullDate[0] + " " + fullDate[1] + " " + fullDate[2]);
			date.setText(fullDate[0]);
			
			TeamDataAdapter adapter = new TeamDataAdapter(getContext());
			adapter.open();
			Team homeTeam = adapter.getTeam(game.getHomeID());
			Team awayTeam = adapter.getTeam(game.getAwayID());

			homeTeamView.setText(homeTeam.getName());
			awayTeamView.setText(awayTeam.getName());
			return v;
		}

	}
}
