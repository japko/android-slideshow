package link.standen.michael.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import link.standen.michael.slideshow.dialog.ControlsDialog;
import link.standen.michael.slideshow.model.FileItem;

/**
 * Slideshow base activity.
 */
public abstract class BaseActivity extends AppCompatActivity {

	String currentPath;
	List<FileItem> fileList = new ArrayList<>();

	static final String STATE_PATH = "pathState";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Check whether we're recreating a previously destroyed instance
		if (savedInstanceState != null) {
			// Restore value of members from saved state
			currentPath = savedInstanceState.getString(STATE_PATH);
		} else {
			// Probably initialize members with default values for a new instance
			currentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	/**
	 * Handle options menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			// Do the same thing as the back button.
			onBackPressed();
			return true;
		} else if (id == R.id.action_settings) {
			startSettingsActivity();
			return true;
		} else if (id == R.id.action_controls) {
			new ControlsDialog().show(getSupportFragmentManager(), null);
			return true;
		} else if (id == R.id.action_close) {
			this.moveTaskToBack(true);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Get the root location, considering the preferences.
	 */
	String getRootLocation(){
		if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("use_device_root", false)){
			return "";
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public void startSettingsActivity() {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.SlideshowPreferenceFragment.class.getName());
		intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
		startActivity(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(STATE_PATH, currentPath);
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(outState);
	}

}
