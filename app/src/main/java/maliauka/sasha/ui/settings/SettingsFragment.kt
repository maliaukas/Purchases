package maliauka.sasha.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import maliauka.sasha.PurchaseApp
import maliauka.sasha.R
import maliauka.sasha.ui.viewmodel.PurchaseViewModel
import maliauka.sasha.ui.viewmodel.PurchaseViewModelFactory

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val viewModel: PurchaseViewModel by activityViewModels {
        PurchaseViewModelFactory(((activity?.application) as PurchaseApp).repository)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        updateListPrefSummary()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filterBtn = activity?.findViewById<ImageButton>(R.id.filter_btn)
        filterBtn?.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        // Set up a listener whenever a key changes
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        // Unregister the listener whenever a key changes
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun updateListPrefSummary() {
        val preference = findPreference<ListPreference>(getString(R.string.prefs_sort_column_key))
        val value = preference?.value ?: getString(R.string.prefs_sort_column_default)
        viewModel.setSortColumn(value)
        preference?.summary =
            String.format(getString(R.string.prefs_sort_column_summary), preference?.entry)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        Log.d("SHAS", "onSharedPreferenceChanged: $key")
        when (key) {
            getString(R.string.prefs_sort_column_key) -> {
                updateListPrefSummary()
            }
            getString(R.string.prefs_sort_order_key) -> {
                val preference =
                    findPreference<SwitchPreference>(getString(R.string.prefs_sort_order_key))
                viewModel.setSortOrder(preference?.isChecked!!)
            }
            getString(R.string.prefs_database_key) -> {
                val preference =
                    findPreference<SwitchPreference>(getString(R.string.prefs_database_key))
                viewModel.setUseRoom(preference?.isChecked!!)
            }
        }
    }
}