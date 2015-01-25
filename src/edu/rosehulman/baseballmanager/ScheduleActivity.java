package edu.rosehulman.baseballmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ScheduleActivity extends Activity {

	/*
	 * Code for tabs based on Android Tutorials for Beginners
	 * http://www.learn-android-easily.com/2013/07/android-tabwidget-example.html
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_schedule);

         // Create the TabHost that will contain the Tabs
         TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


         TabSpec tab1 = tabHost.newTabSpec("Upcoming Games");
         TabSpec tab2 = tabHost.newTabSpec("Previous Games");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
         tab1.setIndicator("Upcoming Games");
         tab1.setContent(new Intent(this, UpcomingGamesActivity.class));
         
         tab2.setIndicator("Previous Games");
         tab2.setContent(new Intent(this, PreviousGamesActivity.class));
         
         // Add the tabs  to the TabHost to display.
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
	}
}
