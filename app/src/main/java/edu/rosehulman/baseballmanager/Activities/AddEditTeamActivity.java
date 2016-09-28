package edu.rosehulman.baseballmanager.Activities;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edu.rosehulman.baseballmanager.R;
import edu.rosehulman.baseballmanager.Team;
import edu.rosehulman.baseballmanager.TeamDataAdapter;

public class AddEditTeamActivity extends Activity {

	private ImageView mTeamPicture;
	private EditText mTeamName;
	private Bitmap mTeamBitmap;
	
	private static final int SELECT_PHOTO = 100;
	private TeamDataAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_team);

		adapter = new TeamDataAdapter(this);
		adapter.open();
		
		Button saveButton = (Button) findViewById(R.id.save_button);
		Button uploadButton = (Button) findViewById(R.id.upload_button);
		mTeamPicture = (ImageView) findViewById(R.id.team_picture);
		mTeamBitmap = ((BitmapDrawable) mTeamPicture.getDrawable()).getBitmap();
		mTeamName = (EditText) findViewById(R.id.new_team_name);
		
		Intent i = getIntent();
		final long id = i.getLongExtra(TeamDataAdapter.KEY_ID, -1);
		if (id > -1) {
			Team team = adapter.getTeam(id);
			mTeamName.setText(team.getName());
			mTeamBitmap = BitmapFactory.decodeByteArray(team.getLogo() , 0, team.getLogo() .length);
			mTeamPicture.setImageBitmap(mTeamBitmap);
		}
		
		mTeamPicture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoSelectorIntent = new Intent(Intent.ACTION_PICK);
				photoSelectorIntent.setType("image/*");
				startActivityForResult(photoSelectorIntent, SELECT_PHOTO);
			}
		});
		
		uploadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photoSelectorIntent = new Intent(Intent.ACTION_PICK);
				photoSelectorIntent.setType("image/*");
				startActivityForResult(photoSelectorIntent, SELECT_PHOTO);
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTeamPicture = (ImageView) findViewById(R.id.team_picture);
				mTeamName = (EditText) findViewById(R.id.new_team_name);
				String name = mTeamName.getText().toString();
				if (mTeamBitmap == null) {
					Toast.makeText(AddEditTeamActivity.this, "Please select a logo for your team", Toast.LENGTH_LONG).show();
					return;
				}
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				mTeamBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();
				Team newTeam = new Team(name, byteArray);
				if(id > -1) {
					Intent returnIntent = new Intent();
					setResult(RESULT_OK, returnIntent);
					newTeam.setID(id);
					adapter.updateTeam(newTeam);
				} else {
					adapter.addTeam(newTeam);
					Intent i = new Intent(AddEditTeamActivity.this, TeamPageActivity.class);
					i.putExtra(TeamDataAdapter.KEY_ID, newTeam.getID());
					AddEditTeamActivity.this.startActivity(i);
				}
				
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.close();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				Uri image = data.getData();
				InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(image);
					Bitmap selected = BitmapFactory.decodeStream(imageStream);
					mTeamPicture.setImageBitmap(selected);
					mTeamBitmap = selected;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
				
		
	}
}