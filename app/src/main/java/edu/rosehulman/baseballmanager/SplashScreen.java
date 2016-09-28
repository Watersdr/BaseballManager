package edu.rosehulman.baseballmanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import edu.rosehulman.baseballmanager.Activities.AddEditTeamActivity;
import edu.rosehulman.baseballmanager.Activities.TeamSelectActivity;

public class SplashScreen extends Activity {

	// Splash screen timer
    private static int SPLASH_TIME_OUT = 500;
    
    public static final String BM = "BM";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {
                // Load teams in database
            	TeamDataAdapter teams = new TeamDataAdapter(SplashScreen.this);
            	teams.open();
            	Cursor cursor = teams.getTeamsCursor();
            	
            	
            	
            	// Prompt user to create a team if there aren't any. Otherwise, goto team select
                Intent i;
                if(cursor.getCount() == 0 /* count == 0 */) {
                	i = new Intent(SplashScreen.this, AddEditTeamActivity.class);
                } else {
                	i = new Intent(SplashScreen.this, TeamSelectActivity.class);
                }
                startActivity(i);
 
                // Close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
