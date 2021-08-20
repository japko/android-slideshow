package link.standen.michael.slideshow;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.cketti.library.changelog.ChangeLog;
import link.standen.michael.slideshow.dialog.ControlsDialog;
import link.standen.michael.slideshow.model.FileItem;

/**
 * Slideshow base activity.
 */
public abstract class BaseActivity extends AppCompatActivity {

	String currentPath;
	List<FileItem> fileList = new ArrayList<>();
	private Dialog changeLog;

	static final String STATE_PATH = "pathState";

	private static final String CHANGE_LOG_CSS = "body { padding: 0.8em; } " +
			"h1 { margin-left: 0px; font-size: 1.2em; } " +
			"ul { padding-left: 1.2em; } " +
			"li { margin-left: 0px; }";
	private static final String DEFAULT_LANGUAGE = new Locale("en").getLanguage();

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
			Intent intent = new Intent(this, SettingsActivity.class);
			intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.SlideshowPreferenceFragment.class.getName());
			intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
			startActivity(intent);
			return true;
		} else if (id == R.id.action_controls) {
			new ControlsDialog().show(getSupportFragmentManager(), null);
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(STATE_PATH, currentPath);
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(outState);
	}

}
