package link.standen.michael.slideshow

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*

/**
 * A [SettingsActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 *
 * See [
 * Android Design: Settings](http://developer.android.com/design/patterns/settings.html) for design guidelines and the [Settings
 * API Guide](http://developer.android.com/guide/topics/ui/settings.html) for more information on developing a Settings UI.
 */

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_fragment, SlideshowPreferenceFragment())
            .commit()

    }

    /**
     * This fragment shows general preferences only.
     */
    class SlideshowPreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            addPreferencesFromResource(R.xml.preferences)

            val reverseOrderPref = findPreference<SwitchPreference>("reverse_order")
            val randomOrderPref = findPreference<SwitchPreference>("random_order")
            // Enabling reverse disables random
            reverseOrderPref?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    if (java.lang.Boolean.TRUE == newValue) {
                        randomOrderPref?.isChecked = false
                    }
                    true
                }
            // Enabling random disables reverse
            randomOrderPref?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    if (java.lang.Boolean.TRUE == newValue) {
                        reverseOrderPref?.isChecked = false
                    }
                    true
                }
            val rememberLocationPref = findPreference<SwitchPreference>("remember_location")
            val autoStartPref = findPreference<SwitchPreference>("auto_start")
            // Disabling remember location disables auto start
            rememberLocationPref?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    if (java.lang.Boolean.FALSE == newValue) {
                        autoStartPref?.isChecked = false
                    }
                    true
                }
            val glideSupportPref = findPreference<SwitchPreference>("glide_image_strategy")
            val preloadPref = findPreference<SwitchPreference>("preload_images")
            val gifPref = findPreference<SwitchPreference>("enable_gif_support")
            // Disabling glide support disables preloading and GIF support
            glideSupportPref?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    if (java.lang.Boolean.FALSE == newValue) {
                        preloadPref?.isChecked = false
                        gifPref?.isChecked = false
                    }
                    true
                }
            val imageDetailsPref = findPreference<SwitchPreference>("image_details")
            val imageDetailsDuringPref = findPreference<SwitchPreference>("image_details_during")
            // Disabling remember location disables auto start
            imageDetailsPref?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    if (java.lang.Boolean.FALSE == newValue) {
                        imageDetailsDuringPref?.isChecked = false
                    }
                    true
                }

            // Bind the summaries of List Preferences
            val onCompletePref = findPreference<ListPreference>("action_on_complete")
            onCompletePref?.onPreferenceChangeListener = listSummaryListener
            listSummaryListener.onPreferenceChange(onCompletePref, onCompletePref?.value)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                requireActivity().onBackPressed()
                return true
            }
            return super.onOptionsItemSelected(item)
        }

    }

    companion object {
        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private val listSummaryListener =
            Preference.OnPreferenceChangeListener { preference, value ->
                val stringValue = value.toString()
                if (preference is ListPreference) {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    val index = preference.findIndexOfValue(stringValue)

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                        if (index >= 0) preference.entries[index] else null
                    )
                } else {
                    // For all other preferences, set the summary to the value's
                    // simple string representation.
                    preference.summary = stringValue
                }
                true
            }

    }
}