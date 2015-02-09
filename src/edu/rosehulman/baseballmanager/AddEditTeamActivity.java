package edu.rosehulman.baseballmanager;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
				adapter.addTeam(newTeam);
				
				Intent i = new Intent(AddEditTeamActivity.this, TeamPageActivity.class);
				i.putExtra(TeamDataAdapter.KEY_ID, newTeam.getID());
				AddEditTeamActivity.this.startActivity(i);
				AddEditTeamActivity.this.finish();
			}
		});
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_team, menu);
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